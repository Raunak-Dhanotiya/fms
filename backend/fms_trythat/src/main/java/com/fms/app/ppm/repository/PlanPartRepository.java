package com.fms.app.ppm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.ppm.models.PlanPart;

public interface PlanPartRepository extends JpaRepository<PlanPart,Integer> {

	List<PlanPart> findByPlanStepId(int planStepId);

	boolean existsByPlanStepIdAndPartId(int planStepId, Integer partId);

}
