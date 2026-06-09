package com.fms.app.ppm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.ppm.models.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer>, JpaSpecificationExecutor<Plan>{

	boolean existsByPlanName(String planName);

}
