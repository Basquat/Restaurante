package com.ads.restaurante.dto.manager;

import com.ads.restaurante.model.Manager;

/** Saída de manager — sem password. (O bug antigo colocava a senha no lugar do username.) */
public record ManagerResponse(
        Long id,
        String username,
        String cpf,
        String email
) {
    public static ManagerResponse of(Manager m) {
        return new ManagerResponse(m.getId(), m.getUsername(), m.getCpf(), m.getEmail());
    }
}
