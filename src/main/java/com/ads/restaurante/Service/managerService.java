package com.ads.restaurante.Service;

import com.ads.restaurante.Repository.*;
import com.ads.restaurante.Model.managerModel;
import com.ads.restaurante.DTOS.Manager.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class managerService {
    private final managerRepository repository;

    @Autowired
    public managerService(managerRepository repository) {
        this.repository = repository;
    }

    @Transactional
 public managerModel cadastrarManager(managerModel manager){
        if (repository.existsById(manager.getManagerID())){
            throw new RuntimeException("Manager Já existente");
        }
        return repository.save(manager);
    }
}
