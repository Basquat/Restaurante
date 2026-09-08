package com.ads.restaurante.controller;

import com.ads.restaurante.dto.manager.ManagerRequest;
import com.ads.restaurante.dto.manager.ManagerResponse;
import com.ads.restaurante.service.ManagerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Todas as rotas exigem token de manager (ver SecurityConfig). */
@RestController
@RequestMapping("/managers")
public class ManagerController {

    private final ManagerService service;

    public ManagerController(ManagerService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ManagerResponse criar(@Valid @RequestBody ManagerRequest req) {
        return service.criar(req);
    }

    @GetMapping
    public List<ManagerResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ManagerResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public ManagerResponse atualizar(@PathVariable Long id, @Valid @RequestBody ManagerRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
