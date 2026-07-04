package com.ads.restaurante.Service;
import com.ads.restaurante.Repository.*;
import com.ads.restaurante.Model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class pratoService {
    private final pratoRepository repository;

    @Autowired
    public pratoService(pratoRepository repository) {
        this.repository = repository;
    }


    @Transactional
    public pratoModel cadastrarPrato(pratoModel prato){
        if (repository.existsById(prato.getPratoID())){
            throw new RuntimeException("prato já existente");
        }
        return repository.save(prato);
    }
}
