package com.fms.app.ppm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.ppm.models.PlanStep;
import com.fms.app.ppm.repository.PlanStepRepository;

@Service
public class PlanStepServices {

	@Autowired
	PlanStepRepository planStepRepo;

	public void saveOrUpdate(PlanStep planStep) {
		this.planStepRepo.save(planStep);
	}

	public PlanStep getPlanStepById(int planStepId) {
		return this.planStepRepo.findById(planStepId).orElse(null);
	}

	public List<PlanStep> getAllPlanSteps() {
		return this.planStepRepo.findAll();
	}

	public void deletePlanStep(int planStepId) {
		this.planStepRepo.deleteById(planStepId);
	}

	public boolean checkStepCodeExist(int planId, String stepCode) {
		
		return this.planStepRepo.existsByPlanIdAndStepCode(planId,stepCode);
	}
	
	public List<PlanStep> getAllByPlanId(int planId) {
		return this.planStepRepo.findAllByPlanId(planId);
	}

}
