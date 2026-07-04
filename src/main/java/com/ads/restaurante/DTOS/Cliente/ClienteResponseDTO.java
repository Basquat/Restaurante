package com.ads.restaurante.DTOS.Cliente;

import com.ads.restaurante.Model.clienteModel;

public record ClienteResponseDTO(
        Long clienteID,
        String clienteUsername,
        String emailCliente,
        String telefoneCliente,
        String redeSocialCliente
) {
    // Construtor auxiliar muito útil: ele transforma uma entidade clienteModel diretamente em DTO de saída
    public ClienteResponseDTO(clienteModel cliente) {
        this(
                cliente.getClienteID(),
                cliente.getClienteUsername(),
                cliente.getEmailCliente(),
                cliente.getTelefoneCliente(),
                cliente.getRedeSocialCliente()
        );
    }
}
