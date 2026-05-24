package com.ads.restaurante.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ads.restaurante.Model.clienteModel;

public interface clienteRepository extends JpaRepository<clienteModel, Long> {
}
