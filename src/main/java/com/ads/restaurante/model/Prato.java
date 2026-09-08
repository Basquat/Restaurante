package com.ads.restaurante.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Prato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    /** Dinheiro em BigDecimal, nunca double: double acumula erro de arredondamento. */
    private BigDecimal valor;

    /** Substitui o antigo `pratoSaindo`, cujo nome não deixava claro o que true/false significava. */
    private Boolean disponivel;

    protected Prato() {
    }

    public Prato(String nome, BigDecimal valor, Boolean disponivel) {
        this.nome = nome;
        this.valor = valor;
        this.disponivel = disponivel;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }
}
