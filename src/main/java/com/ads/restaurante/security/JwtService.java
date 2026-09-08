package com.ads.restaurante.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/** Gera e valida tokens JWT assinados com HS256. Stateless: nada é guardado no servidor. */
@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    private final SecretKey key;
    private final long expirationMinutes;

    public JwtService(@Value("${jwt.secret:}") String secret,
                      @Value("${jwt.expiration-minutes:60}") long expirationMinutes) {
        this.expirationMinutes = expirationMinutes;
        if (secret == null || secret.isBlank()) {
            // Sem segredo configurado (ex.: primeiro clone): gera um efêmero só para não
            // travar a inicialização. Os tokens deixam de valer a cada restart.
            this.key = Jwts.SIG.HS256.key().build();
            log.warn("jwt.secret não configurado — usando segredo efêmero. Defina JWT_SECRET em produção.");
        } else {
            byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
            if (bytes.length < 32) {
                throw new IllegalStateException("jwt.secret deve ter ao menos 32 bytes para HS256");
            }
            this.key = Keys.hmacShaKeyFor(bytes);
        }
    }

    public GeneratedToken generate(String username) {
        Instant agora = Instant.now();
        Instant expira = agora.plus(expirationMinutes, ChronoUnit.MINUTES);
        String token = Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(agora))
                .expiration(Date.from(expira))
                .signWith(key)
                .compact();
        return new GeneratedToken(token, expira);
    }

    public String extractUsername(String token) {
        return parse(token).getSubject();
    }

    public boolean isValid(String token) {
        try {
            parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parse(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public record GeneratedToken(String token, Instant expiraEm) {
    }
}
