package com.ads.restaurante.dto.manager;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ManagerRequest(
        @NotBlank String username,
        @NotBlank @Size(min = 8, message = "senha deve ter ao menos 8 caracteres") String password,
        @NotBlank @Pattern(regexp = "\\d{11}", message = "cpf deve ter 11 dígitos") String cpf,
        @Email @NotBlank String email
) {
}
