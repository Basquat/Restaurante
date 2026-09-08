package com.ads.restaurante.model;

/** Ciclo de vida de um pedido. Enum em vez de String solta: o compilador barra valor inválido. */
public enum PedidoStatus {
    PENDENTE,
    EM_PREPARO,
    ENTREGUE,
    CANCELADO
}
