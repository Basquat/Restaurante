package com.ads.restaurante.service;

import com.ads.restaurante.dto.pedido.PedidoRequest;
import com.ads.restaurante.dto.pedido.PedidoResponse;
import com.ads.restaurante.exception.NotFoundException;
import com.ads.restaurante.model.Cliente;
import com.ads.restaurante.model.Pedido;
import com.ads.restaurante.model.PedidoStatus;
import com.ads.restaurante.model.Prato;
import com.ads.restaurante.repository.ClienteRepository;
import com.ads.restaurante.repository.PedidoRepository;
import com.ads.restaurante.repository.PratoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final PratoRepository pratoRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         ClienteRepository clienteRepository,
                         PratoRepository pratoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.pratoRepository = pratoRepository;
    }

    @Transactional
    public PedidoResponse criar(PedidoRequest req) {
        Cliente cliente = clienteRepository.findById(req.clienteId())
                .orElseThrow(() -> new NotFoundException("Cliente", req.clienteId()));
        Prato prato = pratoRepository.findById(req.pratoId())
                .orElseThrow(() -> new NotFoundException("Prato", req.pratoId()));

        if (Boolean.FALSE.equals(prato.getDisponivel())) {
            throw new IllegalArgumentException("Prato '" + prato.getNome() + "' não está disponível");
        }

        BigDecimal total = prato.getValor().multiply(BigDecimal.valueOf(req.quantidade()));

        Pedido pedido = new Pedido(cliente, prato, req.quantidade(), total,
                PedidoStatus.PENDENTE, LocalDateTime.now());
        return PedidoResponse.of(pedidoRepository.save(pedido));
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> listar() {
        return pedidoRepository.findAll().stream().map(PedidoResponse::of).toList();
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> porCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId).stream().map(PedidoResponse::of).toList();
    }

    @Transactional(readOnly = true)
    public PedidoResponse buscar(Long id) {
        return pedidoRepository.findById(id)
                .map(PedidoResponse::of)
                .orElseThrow(() -> new NotFoundException("Pedido", id));
    }

    @Transactional
    public PedidoResponse atualizarStatus(Long id, PedidoStatus novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido", id));
        pedido.setStatus(novoStatus);
        return PedidoResponse.of(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoResponse cancelar(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido", id));
        if (pedido.getStatus() == PedidoStatus.ENTREGUE || pedido.getStatus() == PedidoStatus.CANCELADO) {
            throw new IllegalArgumentException(
                    "Pedido não pode ser cancelado no status " + pedido.getStatus());
        }
        pedido.setStatus(PedidoStatus.CANCELADO);
        return PedidoResponse.of(pedidoRepository.save(pedido));
    }

    @Transactional
    public void remover(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new NotFoundException("Pedido", id);
        }
        pedidoRepository.deleteById(id);
    }
}
