package com.ads.restaurante.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class clienteModel {

    //VARIAVEIS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteID;

    private String clienteUsername;

    private String clientePassword;

    //GETTERS E SETTERS

    public Long getClienteID() {
        return clienteID;
    }

    public void setClienteID(Long clienteID) {
        this.clienteID = clienteID;
    }

    public String getClienteUsername() {
        return clienteUsername;
    }

    public void setClienteUsername(String clienteUsername) {
        this.clienteUsername = clienteUsername;
    }

    public String getClientePassword() {
        return clientePassword;
    }

    public void setClientePassword(String clientePassword) {
        this.clientePassword = clientePassword;
    }

    //Construtor

    public clienteModel(Long clienteID, String clienteUsername, String clientePassword) {
        this.clienteID = clienteID;
        this.clienteUsername = clienteUsername;
        this.clientePassword = clientePassword;
    }
}
