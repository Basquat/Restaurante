package com.ads.restaurante.DTOS.Prato;

import com.ads.restaurante.Model.pratoModel;

public record pratoResponseDTO(
        Long pratoID,
        String pratoNome,
        Double pratoValor,
        Boolean pratoSaindo
) {
    public pratoResponseDTO(pratoModel prato){
        this(
                prato.getPratoID(),
                prato.getPratoNome(),
                prato.getPratoValor(),
                prato.getPratoSaindo()
        );
    }
}
