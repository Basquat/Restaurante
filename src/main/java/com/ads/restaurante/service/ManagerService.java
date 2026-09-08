package com.ads.restaurante.service;

import com.ads.restaurante.dto.manager.ManagerRequest;
import com.ads.restaurante.dto.manager.ManagerResponse;
import com.ads.restaurante.exception.ConflictException;
import com.ads.restaurante.exception.NotFoundException;
import com.ads.restaurante.model.Manager;
import com.ads.restaurante.repository.ManagerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ManagerService {

    private final ManagerRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ManagerService(ManagerRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ManagerResponse criar(ManagerRequest req) {
        if (repository.existsByUsername(req.username())) {
            throw new ConflictException("username '" + req.username() + "' já está em uso");
        }
        Manager manager = new Manager(
                req.username(),
                passwordEncoder.encode(req.password()),
                req.cpf(),
                req.email()
        );
        return ManagerResponse.of(repository.save(manager));
    }

    @Transactional
    public ManagerResponse atualizar(Long id, ManagerRequest req) {
        Manager manager = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Manager", id));
        manager.setUsername(req.username());
        manager.setPassword(passwordEncoder.encode(req.password()));
        manager.setCpf(req.cpf());
        manager.setEmail(req.email());
        return ManagerResponse.of(repository.save(manager));
    }

    @Transactional(readOnly = true)
    public ManagerResponse buscar(Long id) {
        return repository.findById(id)
                .map(ManagerResponse::of)
                .orElseThrow(() -> new NotFoundException("Manager", id));
    }

    @Transactional(readOnly = true)
    public List<ManagerResponse> listar() {
        return repository.findAll().stream().map(ManagerResponse::of).toList();
    }

    @Transactional
    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Manager", id);
        }
        repository.deleteById(id);
    }
}
