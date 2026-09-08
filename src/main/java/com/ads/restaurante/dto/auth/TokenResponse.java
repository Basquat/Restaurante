package com.ads.restaurante.dto.auth;

import java.time.Instant;

public record TokenResponse(
        String token,
        String tokenType,
        Instant expiraEm
) {
    public static TokenResponse bearer(String token, Instant expiraEm) {
        return new TokenResponse(token, "Bearer", expiraEm);
    }
}
