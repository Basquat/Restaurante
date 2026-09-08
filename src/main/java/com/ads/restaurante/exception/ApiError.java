package com.ads.restaurante.exception;

import java.time.Instant;
import java.util.Map;

/** Corpo padrão de erro da API. `campos` só é preenchido em erro de validação. */
public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String message,
        Map<String, String> campos
) {
    public static ApiError of(int status, String error, String message) {
        return new ApiError(Instant.now(), status, error, message, null);
    }

    public static ApiError validation(int status, String error, String message, Map<String, String> campos) {
        return new ApiError(Instant.now(), status, error, message, campos);
    }
}
