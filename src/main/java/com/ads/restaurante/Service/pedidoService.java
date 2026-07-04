package com.ads.restaurante.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ads.restaurante.Repository.*;
import com.ads.restaurante.Model.*;

@Service
public class pedidoService {
    private final pedidoRepository repository;

    @Autowired
    public pedidoService(pedidoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public pedidoModel cadastrarPedido(pedidoModel pedido){
        if (repository.existsById(pedido.getPedidoID())){
            throw new RuntimeException("pedido já existente");
        }
        return repository.save(pedido);
    }
}
