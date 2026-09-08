package com.ads.restaurante.dto.pedido;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/** O cliente só escolhe o prato e a quantidade. Total, status e data são do servidor. */
public record PedidoRequest(
        @NotNull Long clienteId,
        @NotNull Long pratoId,
        @NotNull @Positive Integer quantidade
) {
}
