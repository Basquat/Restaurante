package com.ads.restaurante.controller;

import com.ads.restaurante.dto.auth.LoginRequest;
import com.ads.restaurante.dto.auth.TokenResponse;
import com.ads.restaurante.security.JwtService;
import com.ads.restaurante.security.ManagerDetailsService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ManagerDetailsService managerDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(ManagerDetailsService managerDetailsService,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.managerDetailsService = managerDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /** Autentica um manager e devolve um JWT válido por jwt.expiration-minutes. */
    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest req) {
        UserDetails user;
        try {
            user = managerDetailsService.loadUserByUsername(req.username());
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }
        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }
        JwtService.GeneratedToken token = jwtService.generate(user.getUsername());
        return TokenResponse.bearer(token.token(), token.expiraEm());
    }
}
