package com.ads.restaurante.service;

import com.ads.restaurante.dto.prato.PratoRequest;
import com.ads.restaurante.dto.prato.PratoResponse;
import com.ads.restaurante.exception.NotFoundException;
import com.ads.restaurante.model.Prato;
import com.ads.restaurante.repository.PratoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PratoService {

    private final PratoRepository repository;

    public PratoService(PratoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PratoResponse criar(PratoRequest req) {
        Prato prato = new Prato(req.nome(), req.valor(), req.disponivel());
        return PratoResponse.of(repository.save(prato));
    }

    @Transactional
    public PratoResponse atualizar(Long id, PratoRequest req) {
        Prato prato = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prato", id));
        prato.setNome(req.nome());
        prato.setValor(req.valor());
        prato.setDisponivel(req.disponivel());
        return PratoResponse.of(repository.save(prato));
    }

    @Transactional(readOnly = true)
    public PratoResponse buscar(Long id) {
        return repository.findById(id)
                .map(PratoResponse::of)
                .orElseThrow(() -> new NotFoundException("Prato", id));
    }

    @Transactional(readOnly = true)
    public List<PratoResponse> listar() {
        return repository.findAll().stream().map(PratoResponse::of).toList();
    }

    @Transactional
    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Prato", id);
        }
        repository.deleteById(id);
    }
}
