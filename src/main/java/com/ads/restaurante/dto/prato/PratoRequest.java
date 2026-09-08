package com.ads.restaurante.dto.prato;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PratoRequest(
        @NotBlank String nome,
        @NotNull @Positive BigDecimal valor,
        @NotNull Boolean disponivel
) {
}
