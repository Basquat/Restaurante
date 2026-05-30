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

    private String emailCliente;

    private String telefoneCliente;

    private String redeSocialCliente;

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

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }

    public String getRedeSocialCliente() {
        return redeSocialCliente;
    }

    public void setRedeSocialCliente(String redeSocialCliente) {
        this.redeSocialCliente = redeSocialCliente;
    }


    //Construtor


    public clienteModel(Long clienteID, String clienteUsername, String clientePassword, String emailCliente, String telefoneCliente, String redeSocialCliente) {
        this.clienteID = clienteID;
        this.clienteUsername = clienteUsername;
        this.clientePassword = clientePassword;
        this.emailCliente = emailCliente;
        this.telefoneCliente = telefoneCliente;
        this.redeSocialCliente = redeSocialCliente;
    }
}
