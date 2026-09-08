package com.ads.restaurante.dto.prato;

import com.ads.restaurante.model.Prato;

import java.math.BigDecimal;

public record PratoResponse(
        Long id,
        String nome,
        BigDecimal valor,
        Boolean disponivel
) {
    public static PratoResponse of(Prato p) {
        return new PratoResponse(p.getId(), p.getNome(), p.getValor(), p.getDisponivel());
    }
}
