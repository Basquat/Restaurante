package com.ads.restaurante.dto.cliente;

import com.ads.restaurante.model.Cliente;

/** Saída de cliente — sem o campo password. */
public record ClienteResponse(
        Long id,
        String username,
        String email,
        String telefone,
        String redeSocial
) {
    public static ClienteResponse of(Cliente c) {
        return new ClienteResponse(c.getId(), c.getUsername(), c.getEmail(), c.getTelefone(), c.getRedeSocial());
    }
}
