package com.ads.restaurante.DTOS.Cliente;

import com.ads.restaurante.Model.clienteModel;

public record ClienteResponseDTO(
        Long clienteID,
        String clienteUsername,
        String emailCliente

) {

    public ClienteResponseDTO(clienteModel cliente) {
        this(
                cliente.getClienteID(),
                cliente.getClienteUsername(),
                cliente.getEmailCliente()
        );
    }
}
