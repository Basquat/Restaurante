package com.ads.restaurante.DTOS.Manager;

import com.ads.restaurante.Model.managerModel;

public record managerResponseDTO(
        Long managerID,
        String managerUsername,
        String managerEmail) {

    public managerResponseDTO(managerModel manager){
        this(
                manager.getManagerID(),
                manager.getManagerPassword(),
                manager.getManagerEmail()
        );
    }
}

