package com.fms.app.inventory.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.inventory.model.CenterUsage;
import com.fms.app.inventory.repository.ICenterUsageRepository;

@Service
public class CenterUsageService {

	@Autowired
	ICenterUsageRepository centerUsageRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public void saveOrUpdate(CenterUsage centerUsage) {
		centerUsageRepository.save(centerUsage);
	}

	public List<CenterUsage> getAllCenterBudget() {
		return centerUsageRepository.findAll();
	}

	public CenterUsage getCenterUsageById(int centerUsageId) {
		return centerUsageRepository.findById(centerUsageId).orElse(new CenterUsage());
	}

	public void deleteCenterUsageById(int centerUsageId) {
		centerUsageRepository.deleteById(centerUsageId);
	}

	public Map<String, BigDecimal> checkIfTotalCostExceed(Map<String, Double> requestBody) {
		int budgetTermId = requestBody.get("budgetTermId").intValue();
		int blId =  requestBody.get("blId").intValue();
		int flId =  requestBody.get("flId").intValue();
		int itemId =  requestBody.get("itemId").intValue();
		int centerUsageId = requestBody.get("centerUsageId").intValue();

		String budgetQuery = "SELECT budget FROM center_budget WHERE budget_term_id = ? AND bl_id = ? AND fl_id = ? AND item_id = ?";
		Query budgetNativeQuery = entityManager.createNativeQuery(budgetQuery);
		budgetNativeQuery.setParameter(1, budgetTermId);
		budgetNativeQuery.setParameter(2, blId);
		budgetNativeQuery.setParameter(3, flId);
		budgetNativeQuery.setParameter(4, itemId);
		BigDecimal budget = (BigDecimal) budgetNativeQuery.getResultStream().findFirst().orElse(null);

		Map<String, BigDecimal> data = new HashMap<>();

		BigDecimal totalCost = BigDecimal.ZERO ;
		if(budget!=null) {
			String costQuery = "SELECT SUM(cost) FROM center_usage WHERE budget_term_id = ? AND bl_id = ? AND fl_id = ? AND item_id = ?";
			if (centerUsageId != 0) {
				costQuery += " AND center_usage_id = ?";
			}

			Query costNativeQuery = entityManager.createNativeQuery(costQuery);
			costNativeQuery.setParameter(1, budgetTermId);
			costNativeQuery.setParameter(2, blId);
			costNativeQuery.setParameter(3, flId);
			costNativeQuery.setParameter(4, itemId);
			if (centerUsageId != 0) {
			costNativeQuery.setParameter(5, centerUsageId);
			}

			totalCost = (costNativeQuery.getSingleResult() != null) ? (BigDecimal) costNativeQuery.getSingleResult() : BigDecimal.ZERO;
		}
		data.put("budget", budget);
		data.put("cost", totalCost);

		return data;
	}

}
