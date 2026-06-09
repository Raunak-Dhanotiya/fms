package com.fms.app.ppm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.ppm.models.PlanTool;
import java.util.List;

public interface PlanToolRepository extends JpaRepository<PlanTool,Integer> {

	List<PlanTool> findByPlanStepId(int planStepId);

	boolean existsByPlanStepIdAndToolId(int planStepId, Integer toolId);

}
