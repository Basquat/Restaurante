package com.ads.restaurante.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ads.restaurante.Model.managerModel;

public interface  managerRepository extends JpaRepository<managerModel, Long>{
}
