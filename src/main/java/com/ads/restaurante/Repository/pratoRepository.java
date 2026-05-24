package com.ads.restaurante.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ads.restaurante.Model.pratoModel;

public interface pratoRepository extends JpaRepository<pratoModel, Long>{
}
