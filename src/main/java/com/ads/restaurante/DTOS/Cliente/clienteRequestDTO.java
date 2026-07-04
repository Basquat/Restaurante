package com.ads.restaurante.DTOS.Cliente;

// O ID não entra aqui, pois o banco de dados gera ele sozinho
public record clienteRequestDTO( //REQUEST DTO
String clienteUsername,
String clientePassword,
                                 String emailCliente,
                                 String telefoneCliente,
                                 String redeSocialCliente
) {}

