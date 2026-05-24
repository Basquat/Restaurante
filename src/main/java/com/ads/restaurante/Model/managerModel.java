package com.ads.restaurante.Model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

// Espaço  para foreign key dos pratos

@Entity
public class managerModel {

    //ID E VARIAVEIS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerID;

    private String managerUsername;

    private int managerCPF;

    private String managerPassword;



    //GETTERS E SETTERS

    public Long getManagerID() {
        return managerID;
    }

    public void setManagerID(Long managerID) {
        this.managerID = managerID;
    }

    public String getManagerUsername() {
        return managerUsername;
    }

    public void setManagerUsername(String managerUsername) {
        this.managerUsername = managerUsername;
    }

    public int getManagerCPF() {
        return managerCPF;
    }

    public void setManagerCPF(int managerCPF) {
        this.managerCPF = managerCPF;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    //Constructor


    public managerModel(Long managerID, String managerUsername, int managerCPF, String managerPassword) {
        this.managerID = managerID;
        this.managerUsername = managerUsername;
        this.managerCPF = managerCPF;
        this.managerPassword = managerPassword;
    }
}
