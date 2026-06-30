package com.ads.restaurante.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class pedidoModel {

    // ── ID ────────────────────────────────────────────────
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pedidoID;

    // ── RELACIONAMENTOS ───────────────────────────────────

    // FK → cliente_model (clienteID)
    @ManyToOne
    @JoinColumn(name = "clienteID", nullable = false)
    private clienteModel cliente;

    // FK → prato_model (pratoID)
    @ManyToOne
    @JoinColumn(name = "pratoID", nullable = false)
    private pratoModel prato;

    // ── CAMPOS PRÓPRIOS ───────────────────────────────────

    private Integer quantidade;

    private Double valorTotal;      // pratoValor × quantidade

    private String pedidoStatus;    // PENDENTE | EM_PREPARO | ENTREGUE | CANCELADO

    private LocalDateTime dataPedido;

    // ── GETTERS E SETTERS ─────────────────────────────────

    public Long getPedidoID() {
        return pedidoID;
    }

    public void setPedidoID(Long pedidoID) {
        this.pedidoID = pedidoID;
    }

    public clienteModel getCliente() {
        return cliente;
    }

    public void setCliente(clienteModel cliente) {
        this.cliente = cliente;
    }

    public pratoModel getPrato() {
        return prato;
    }

    public void setPrato(pratoModel prato) {
        this.prato = prato;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getPedidoStatus() {
        return pedidoStatus;
    }

    public void setPedidoStatus(String pedidoStatus) {
        this.pedidoStatus = pedidoStatus;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    // ── CONSTRUTOR PADRÃO (obrigatório para JPA) ──────────

    public pedidoModel() {}

    // ── CONSTRUTOR COMPLETO ───────────────────────────────

    public pedidoModel(clienteModel cliente, pratoModel prato,
                       Integer quantidade, Double valorTotal,
                       String pedidoStatus, LocalDateTime dataPedido) {
        this.cliente      = cliente;
        this.prato        = prato;
        this.quantidade   = quantidade;
        this.valorTotal   = valorTotal;
        this.pedidoStatus = pedidoStatus;
        this.dataPedido   = dataPedido;
    }
}