package com.fms.app.ppm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.ppm.models.PlanTool;
import com.fms.app.ppm.repository.PlanToolRepository;

@Service
public class PlanToolServices {
	
	@Autowired
	PlanToolRepository planToolRepo;
	
	public void saveOrUpdate(PlanTool planTool) {
		this.planToolRepo.save(planTool);
	}
	
	public PlanTool getById(int planToolId) {
		return this.planToolRepo.findById(planToolId).orElse(null);
	}
	
	public List<PlanTool> getAllByPlanStepId(int planStepId) {
		return this.planToolRepo.findByPlanStepId(planStepId);
	}
	
	public void deletePlanTool(int planToolId) {
		this.planToolRepo.deleteById(planToolId);
	}
	
	public boolean checkIsToolExist(int planStepId, Integer toolId) {

		return this.planToolRepo.existsByPlanStepIdAndToolId(planStepId, toolId);
	}
}
