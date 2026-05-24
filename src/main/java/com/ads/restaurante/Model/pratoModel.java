package com.ads.restaurante.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class pratoModel {

    //VARIAVEIS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pratoID;

    private String pratoNome;

    private Double pratoValor;

    private Boolean pratoSaindo;

    //GETTERS E SETTERS

    public Long getPratoID() {
        return pratoID;
    }

    public void setPratoID(Long pratoID) {
        this.pratoID = pratoID;
    }

    public String getPratoNome() {
        return pratoNome;
    }

    public void setPratoNome(String pratoNome) {
        this.pratoNome = pratoNome;
    }

    public Double getPratoValor() {
        return pratoValor;
    }

    public void setPratoValor(Double pratoValor) {
        this.pratoValor = pratoValor;
    }

    public Boolean getPratoSaindo() {
        return pratoSaindo;
    }

    public void setPratoSaindo(Boolean pratoSaindo) {
        this.pratoSaindo = pratoSaindo;
    }

    //CONSTRUTOR


    public pratoModel(Long pratoID, Boolean pratoSaindo, Double pratoValor, String pratoNome) {
        this.pratoID = pratoID;
        this.pratoSaindo = pratoSaindo;
        this.pratoValor = pratoValor;
        this.pratoNome = pratoNome;
    }
}
