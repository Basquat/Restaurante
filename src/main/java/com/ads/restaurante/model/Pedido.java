package com.ads.restaurante.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "prato_id", nullable = false)
    private Prato prato;

    private Integer quantidade;

    /** Calculado no servidor (valor do prato × quantidade), nunca recebido do cliente. */
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    private PedidoStatus status;

    private LocalDateTime dataPedido;

    protected Pedido() {
    }

    public Pedido(Cliente cliente, Prato prato, Integer quantidade, BigDecimal valorTotal,
                  PedidoStatus status, LocalDateTime dataPedido) {
        this.cliente = cliente;
        this.prato = prato;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.status = status;
        this.dataPedido = dataPedido;
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Prato getPrato() {
        return prato;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public PedidoStatus getStatus() {
        return status;
    }

    public void setStatus(PedidoStatus status) {
        this.status = status;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }
}
