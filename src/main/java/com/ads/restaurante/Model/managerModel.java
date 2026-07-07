package com.ads.restaurante.Model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;


@Entity
public class managerModel {

    //ID E VARIAVEIS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerID;

    private String managerUsername;
    private int managerCPF;
    private String managerPassword;
    private String managerEmail;

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

    public String getManagerEmail() {
        return managerEmail;
    }
    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    //Constructor


    public managerModel(Long managerID, String managerUsername, int managerCPF, String managerPassword) {
        this.managerID = managerID;
        this.managerUsername = managerUsername;
        this.managerCPF = managerCPF;
        this.managerPassword = managerPassword;
    }
}
