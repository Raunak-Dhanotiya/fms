package com.fms.app.ppm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.ppm.models.PlanTrade;
import com.fms.app.ppm.repository.PlanTradeRepository;

@Service
public class PlanTradeServices {
	
	@Autowired
	PlanTradeRepository planTradeRepo;
	
	public void saveOrUpdate(PlanTrade planTrade) {
		this.planTradeRepo.save(planTrade);
	}
	
	public List<PlanTrade> getAllByStepId(int stepId) {
		return this.planTradeRepo.findByPlanStepId(stepId);
	}
	
	public PlanTrade getById(int planTradeId) {
		return this.planTradeRepo.findById(planTradeId).orElse(null);
	}
	
	public void deleteById(int planTradeId) {
		this.planTradeRepo.deleteById(planTradeId);
	}

	public boolean checkIsTradeExist(int planStepId, Integer tradeId) {
		
		return  this.planTradeRepo.existsByPlanStepIdAndTradeId(planStepId, tradeId);
	}

}
