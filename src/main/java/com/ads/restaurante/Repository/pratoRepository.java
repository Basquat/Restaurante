package com.ads.restaurante.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ads.restaurante.Model.pratoModel;
import org.springframework.stereotype.Repository;

@Repository
public interface pratoRepository extends JpaRepository<pratoModel, Long>{
}
