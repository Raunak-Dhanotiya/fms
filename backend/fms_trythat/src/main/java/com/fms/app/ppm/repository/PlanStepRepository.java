package com.fms.app.ppm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.ppm.models.PlanStep;

public interface PlanStepRepository extends JpaRepository<PlanStep,Integer>{

	boolean existsByPlanIdAndStepCode(int planId, String stepCode);

	List<PlanStep> findAllByPlanId(int planId);

}
