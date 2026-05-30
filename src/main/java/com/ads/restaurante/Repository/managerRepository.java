package com.ads.restaurante.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ads.restaurante.Model.managerModel;
import org.springframework.stereotype.Repository;

@Repository
public interface  managerRepository extends JpaRepository<managerModel, Long>{
}
