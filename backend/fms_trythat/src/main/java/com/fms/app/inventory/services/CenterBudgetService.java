package com.fms.app.inventory.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.inventory.model.CenterBudget;
import com.fms.app.inventory.repository.ICenterBudgetRepository;

@Service
public class CenterBudgetService {

	@Autowired
	ICenterBudgetRepository centerBudgetRepository;
	
	public void saveOrUpdate(CenterBudget centerBudget) {
		centerBudgetRepository.save(centerBudget);
	}

	public CenterBudget getCenterBudgetById(int centerBudgetId) {
		return centerBudgetRepository.findById(centerBudgetId).orElse(new CenterBudget());
	}

	public void deleteCenterBudgetById(int centerBudgetId) {
		centerBudgetRepository.deleteById(centerBudgetId);
	}

	public List<CenterBudget> getAllCenterBudget() {
		return centerBudgetRepository.findAll();
	}

}
