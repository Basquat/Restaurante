package com.ads.restaurante.service;

import com.ads.restaurante.dto.cliente.ClienteRequest;
import com.ads.restaurante.dto.cliente.ClienteResponse;
import com.ads.restaurante.exception.ConflictException;
import com.ads.restaurante.exception.NotFoundException;
import com.ads.restaurante.model.Cliente;
import com.ads.restaurante.repository.ClienteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ClienteService(ClienteRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ClienteResponse criar(ClienteRequest req) {
        if (repository.existsByUsername(req.username())) {
            throw new ConflictException("username '" + req.username() + "' já está em uso");
        }
        Cliente cliente = new Cliente(
                req.username(),
                passwordEncoder.encode(req.password()),
                req.email(),
                req.telefone(),
                req.redeSocial()
        );
        return ClienteResponse.of(repository.save(cliente));
    }

    @Transactional
    public ClienteResponse atualizar(Long id, ClienteRequest req) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente", id));
        cliente.setUsername(req.username());
        cliente.setPassword(passwordEncoder.encode(req.password()));
        cliente.setEmail(req.email());
        cliente.setTelefone(req.telefone());
        cliente.setRedeSocial(req.redeSocial());
        return ClienteResponse.of(repository.save(cliente));
    }

    @Transactional(readOnly = true)
    public ClienteResponse buscar(Long id) {
        return repository.findById(id)
                .map(ClienteResponse::of)
                .orElseThrow(() -> new NotFoundException("Cliente", id));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponse> listar() {
        return repository.findAll().stream().map(ClienteResponse::of).toList();
    }

    @Transactional
    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Cliente", id);
        }
        repository.deleteById(id);
    }
}
