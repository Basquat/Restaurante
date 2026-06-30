package com.ads.restaurante.Repository;

import com.ads.restaurante.Model.pedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface pedidoRepository extends JpaRepository<pedidoModel, Long> {

    // Busca todos os pedidos de um cliente específico pelo clienteID
    List<pedidoModel> findByCliente_ClienteID(Long clienteID);

    // Busca pedidos por status (ex: PENDENTE, EM_PREPARO)
    List<pedidoModel> findByPedidoStatus(String pedidoStatus);
}