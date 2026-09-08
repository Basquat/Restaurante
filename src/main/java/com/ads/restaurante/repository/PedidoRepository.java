package com.ads.restaurante.repository;

import com.ads.restaurante.model.Pedido;
import com.ads.restaurante.model.PedidoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByStatus(PedidoStatus status);
}
