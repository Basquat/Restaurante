package com.ads.restaurante.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Entrada de cadastro/edição de cliente. O id é gerado pelo banco, então não entra aqui. */
public record ClienteRequest(
        @NotBlank String username,
        @NotBlank @Size(min = 8, message = "senha deve ter ao menos 8 caracteres") String password,
        @Email @NotBlank String email,
        String telefone,
        String redeSocial
) {
}
