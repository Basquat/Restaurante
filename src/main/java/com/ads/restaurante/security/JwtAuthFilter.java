package com.ads.restaurante.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/** Lê o header Authorization: Bearer &lt;token&gt;, valida o JWT e popula o SecurityContext. */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String PREFIXO = "Bearer ";

    private final JwtService jwtService;
    private final ManagerDetailsService managerDetailsService;

    public JwtAuthFilter(JwtService jwtService, ManagerDetailsService managerDetailsService) {
        this.jwtService = jwtService;
        this.managerDetailsService = managerDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith(PREFIXO)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring(PREFIXO.length());
        if (jwtService.isValid(token)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails user = managerDetailsService.loadUserByUsername(jwtService.extractUsername(token));
                var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (UsernameNotFoundException ignored) {
                // token válido mas o manager não existe mais — segue sem autenticar
            }
        }

        chain.doFilter(request, response);
    }
}
