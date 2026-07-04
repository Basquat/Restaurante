package com.ads.restaurante.Service;


import jakarta.transaction.Transactional;
import jakarta.websocket.ClientEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ads.restaurante.Repository.*;
import com.ads.restaurante.Model.*;

@Service
public class clienteService {

    private final clienteRepository repository;

    @Autowired
    public clienteService(clienteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public clienteModel cadastrarCliente(clienteModel cliente){
        if (repository.existsById(cliente.getClienteID())){
            throw new RuntimeException("Cliente já existente");
        }
        return repository.save(cliente);
    }
}
