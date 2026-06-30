package com.ads.restaurante.Controller;

import com.ads.restaurante.Model.clienteModel;
import com.ads.restaurante.Model.pedidoModel;
import com.ads.restaurante.Model.pratoModel;
import com.ads.restaurante.Repository.clienteRepository;
import com.ads.restaurante.Repository.pedidoRepository;
import com.ads.restaurante.Repository.pratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedido")
@CrossOrigin(origins = "*")
public class pedidoController {

    @Autowired
    private pedidoRepository pedidoRepo;

    @Autowired
    private clienteRepository clienteRepo;

    @Autowired
    private pratoRepository pratoRepo;

    // ── POST /pedido/addPedido ────────────────────────────
    // Body: { "clienteID": 1, "pratoID": 2, "quantidade": 1 }
    @PostMapping("/addPedido")
    public ResponseEntity<?> addPedido(@RequestBody Map<String, Object> body) {

        Long clienteID = Long.valueOf(body.get("clienteID").toString());
        Long pratoID   = Long.valueOf(body.get("pratoID").toString());
        int  qtd       = Integer.parseInt(body.get("quantidade").toString());

        clienteModel cliente = clienteRepo.findById(clienteID)
                .orElse(null);
        if (cliente == null)
            return ResponseEntity.badRequest().body("Cliente não encontrado");

        pratoModel prato = pratoRepo.findById(pratoID)
                .orElse(null);
        if (prato == null)
            return ResponseEntity.badRequest().body("Prato não encontrado");

        if (prato.getPratoSaindo() == null || !prato.getPratoSaindo())
            return ResponseEntity.badRequest().body("Prato não está disponível");

        double total = prato.getPratoValor() * qtd;

        pedidoModel pedido = new pedidoModel(
                cliente, prato, qtd, total, "PENDENTE", LocalDateTime.now()
        );

        return ResponseEntity.ok(pedidoRepo.save(pedido));
    }

    // ── GET /pedido ───────────────────────────────────────
    // Retorna todos os pedidos (usado pelo manager)
    @GetMapping
    public List<pedidoModel> getAll() {
        return pedidoRepo.findAll();
    }

    // ── GET /pedido/cliente/{clienteID} ───────────────────
    // Retorna os pedidos de um cliente específico
    @GetMapping("/cliente/{clienteID}")
    public List<pedidoModel> getByCliente(@PathVariable Long clienteID) {
        return pedidoRepo.findByCliente_ClienteID(clienteID);
    }

    // ── GET /pedido/{pedidoID} ────────────────────────────
    @GetMapping("/{pedidoID}")
    public ResponseEntity<?> getById(@PathVariable Long pedidoID) {
        return pedidoRepo.findById(pedidoID)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── PATCH /pedido/{pedidoID}/status ──────────────────
    // Body: { "status": "EM_PREPARO" }
    // Usado pelo manager para atualizar o status do pedido
    @PatchMapping("/{pedidoID}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long pedidoID,
                                          @RequestBody Map<String, String> body) {
        return pedidoRepo.findById(pedidoID).map(p -> {
            p.setPedidoStatus(body.get("status"));
            return ResponseEntity.ok(pedidoRepo.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ── PATCH /pedido/{pedidoID}/cancelar ────────────────
    // Atalho para o cliente cancelar o próprio pedido
    @PatchMapping("/{pedidoID}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Long pedidoID) {
        return pedidoRepo.findById(pedidoID).map(p -> {
            if ("ENTREGUE".equals(p.getPedidoStatus()) ||
                    "CANCELADO".equals(p.getPedidoStatus())) {
                return ResponseEntity.badRequest()
                        .body("Pedido não pode ser cancelado no status atual");
            }
            p.setPedidoStatus("CANCELADO");
            return ResponseEntity.ok(pedidoRepo.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ── DELETE /pedido/{pedidoID} ─────────────────────────
    @DeleteMapping("/{pedidoID}")
    public ResponseEntity<Void> delete(@PathVariable Long pedidoID) {
        if (!pedidoRepo.existsById(pedidoID))
            return ResponseEntity.notFound().build();
        pedidoRepo.deleteById(pedidoID);
        return ResponseEntity.noContent().build();
    }
}