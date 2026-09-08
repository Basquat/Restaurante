package com.ads.restaurante.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/** Funcionário administrativo do restaurante. É a identidade usada no login com JWT. */
@Entity
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    /** Hash BCrypt (ver ManagerService). Nunca exposta em respostas. */
    private String password;

    /** CPF fica como String: 11 dígitos não cabem em int (estoura ~2,1 bilhões). */
    private String cpf;

    private String email;

    protected Manager() {
    }

    public Manager(String username, String password, String cpf, String email) {
        this.username = username;
        this.password = password;
        this.cpf = cpf;
        this.email = email;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
