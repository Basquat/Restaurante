package com.ads.restaurante.dto.pedido;

import com.ads.restaurante.model.Pedido;
import com.ads.restaurante.model.PedidoStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResponse(
        Long id,
        Long clienteId,
        String clienteUsername,
        Long pratoId,
        String pratoNome,
        Integer quantidade,
        BigDecimal valorTotal,
        PedidoStatus status,
        LocalDateTime dataPedido
) {
    public static PedidoResponse of(Pedido p) {
        return new PedidoResponse(
                p.getId(),
                p.getCliente().getId(),
                p.getCliente().getUsername(),
                p.getPrato().getId(),
                p.getPrato().getNome(),
                p.getQuantidade(),
                p.getValorTotal(),
                p.getStatus(),
                p.getDataPedido()
        );
    }
}
