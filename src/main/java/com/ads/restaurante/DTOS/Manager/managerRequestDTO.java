package com.ads.restaurante.DTOS.Manager;

public record managerRequestDTO(
//REQUEST DTO
        Long managerID,
        String managerUsername,
        String managerPassword,
        String managerEmail

) {}
