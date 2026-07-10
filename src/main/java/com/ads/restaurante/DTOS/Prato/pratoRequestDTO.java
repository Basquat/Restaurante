package com.ads.restaurante.DTOS.Prato;

public record pratoRequestDTO(
        String pratoNome,
        Double pratoValor,
        Boolean pratoSaindo
) {}
