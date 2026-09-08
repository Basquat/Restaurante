package com.ads.restaurante.controller;

import com.ads.restaurante.dto.prato.PratoRequest;
import com.ads.restaurante.dto.prato.PratoResponse;
import com.ads.restaurante.service.PratoService;
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

@RestController
@RequestMapping("/pratos")
public class PratoController {

    private final PratoService service;

    public PratoController(PratoService service) {
        this.service = service;
    }

    /** GET é público (é o cardápio). POST/PUT/DELETE exigem token de manager. */
    @GetMapping
    public List<PratoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public PratoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PratoResponse criar(@Valid @RequestBody PratoRequest req) {
        return service.criar(req);
    }

    @PutMapping("/{id}")
    public PratoResponse atualizar(@PathVariable Long id, @Valid @RequestBody PratoRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
