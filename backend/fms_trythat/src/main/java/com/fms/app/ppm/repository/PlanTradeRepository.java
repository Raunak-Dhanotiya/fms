package com.fms.app.ppm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.ppm.models.PlanTrade;

public interface PlanTradeRepository extends JpaRepository<PlanTrade,Integer>{

	List<PlanTrade> findByPlanStepId(int stepId);

	boolean existsByPlanStepIdAndTradeId(int planStepId, Integer tradeId);

}
