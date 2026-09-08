package com.ads.restaurante.controller;

import com.ads.restaurante.dto.pedido.PedidoRequest;
import com.ads.restaurante.dto.pedido.PedidoResponse;
import com.ads.restaurante.model.PedidoStatus;
import com.ads.restaurante.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    /** Cliente cria o pedido escolhendo prato + quantidade. Rota pública. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse criar(@Valid @RequestBody PedidoRequest req) {
        return service.criar(req);
    }

    /** Lista todos os pedidos — uso do manager. */
    @GetMapping
    public List<PedidoResponse> listar() {
        return service.listar();
    }

    /** Pedidos de um cliente — rota pública para o cliente acompanhar. */
    @GetMapping("/cliente/{clienteId}")
    public List<PedidoResponse> porCliente(@PathVariable Long clienteId) {
        return service.porCliente(clienteId);
    }

    @GetMapping("/{id}")
    public PedidoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    /** Manager avança o status do pedido. Body: { "status": "EM_PREPARO" } */
    @PatchMapping("/{id}/status")
    public PedidoResponse atualizarStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String valor = body.get("status");
        if (valor == null) {
            throw new IllegalArgumentException("campo 'status' é obrigatório");
        }
        PedidoStatus status;
        try {
            status = PedidoStatus.valueOf(valor.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("status inválido: " + valor);
        }
        return service.atualizarStatus(id, status);
    }

    /** Atalho público para o cliente cancelar o próprio pedido. */
    @PatchMapping("/{id}/cancelar")
    public PedidoResponse cancelar(@PathVariable Long id) {
        return service.cancelar(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
