package com.ads.restaurante.DTOS.pedido;

public record pedidoRequestDTO(

        Integer quantidade,
        Double valorTotal,
        String pedidoStatus

) {}
