package com.fms.app.ppm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.ppm.models.PlanPart;
import com.fms.app.ppm.repository.PlanPartRepository;

@Service
public class PlanPartServices {
	
	@Autowired
	PlanPartRepository planPartRepo;
	
	public void saveOrUpdate(PlanPart planPart) {
		this.planPartRepo.save(planPart);
	}
	
	public PlanPart getById(int planPartId) {
		return this.planPartRepo.findById(planPartId).orElse(null);
	}
	
	public List<PlanPart> getAllByPlanStepId(int planStepId) {
		return this.planPartRepo.findByPlanStepId(planStepId);
	}
	
	public void deleteById(int planPartId) {
		this.planPartRepo.deleteById(planPartId);
	}
	
	public boolean checkIsPartExist(int planStepId, Integer partId) {

		return this.planPartRepo.existsByPlanStepIdAndPartId(planStepId, partId);
	}

}
