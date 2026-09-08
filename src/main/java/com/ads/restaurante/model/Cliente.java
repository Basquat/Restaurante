package com.ads.restaurante.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    /** Armazenada com hash BCrypt (ver ClienteService). Nunca exposta em respostas. */
    private String password;

    private String email;

    private String telefone;

    private String redeSocial;

    /** Construtor sem argumentos exigido pelo JPA para instanciar a entidade na leitura. */
    protected Cliente() {
    }

    public Cliente(String username, String password, String email, String telefone, String redeSocial) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.telefone = telefone;
        this.redeSocial = redeSocial;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getRedeSocial() {
        return redeSocial;
    }

    public void setRedeSocial(String redeSocial) {
        this.redeSocial = redeSocial;
    }
}
