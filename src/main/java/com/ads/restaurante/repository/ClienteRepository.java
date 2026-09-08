package com.ads.restaurante.repository;

import com.ads.restaurante.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByUsername(String username);
}
