package com.ads.restaurante.DTOS.pedido;
import com.ads.restaurante.Model.pedidoModel;

public record pedidoResponseDTO(
        Long pedidoID,
        Integer quantidade,
        Double valorTotal,
        String pedidoStatus
) {
    public pedidoResponseDTO(pedidoModel pedido){
        this(
                pedido.getPedidoID(),
                pedido.getQuantidade(),
                pedido.getValorTotal(),
                pedido.getPedidoStatus()
        );
    }
}
