package com.fms.app.reservation.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.reservation.models.ReserveResources;

public interface ReserveResourcesRepository extends JpaRepository<ReserveResources, Integer>{

}
