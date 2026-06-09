package com.fms.app.inventory.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.inventory.model.CenterUsage;
import com.fms.app.inventory.model.dto.BudgetTermReportInputs;
import com.fms.app.inventory.repository.ICenterBudgetRepository;
import com.fms.app.reservation.services.ReservationNativeQueryServices;

@Service
public class InventoryReportService {

	@Autowired
	ICenterBudgetRepository centerBudgetRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	ReservationNativeQueryServices reservationNativeQueryServices;

	public List<BudgetTermReportInputs> getByBudgetTermId(Integer budgetTermId) {
		List<BudgetTermReportInputs> centerBudgets = new ArrayList<>();
		String blRestriction = "";
		String flRestriction = "";
		Query nativeQuery;
		String budgetTermQuery = "SELECT cb.budget_term_id,cb.bl_id AS BlId, cb.fl_id AS FlId, SUM(budget) AS Budget "
				+ "FROM center_budget cb WHERE cb.budget_term_id= :budgetTermId GROUP BY cb.bl_id,cb.fl_id, cb.budget_term_id";
		nativeQuery = entityManager.createNativeQuery(budgetTermQuery).setParameter("budgetTermId", budgetTermId);
		@SuppressWarnings("unchecked")
		List<Object[]> budgets = nativeQuery.getResultList();

		List<BudgetTermReportInputs> budgetList = new ArrayList<>();

		if (!budgets.isEmpty() && budgets.get(0) != null) {
			for (Object[] data : budgets) {
				budgetList.add(new BudgetTermReportInputs((Integer) data[0], (Integer) data[1], (Integer) data[2], null,
						(BigDecimal) data[3], null));
			}
		}

		StringBuilder primaryQuery = new StringBuilder("select ");
		if (!budgetList.isEmpty()) {
			String secondaryQuery = "";
			for (BudgetTermReportInputs budget : budgetList) {
				secondaryQuery = "SELECT SUM(cost) FROM center_usage WHERE budget_term_id = " + budgetTermId + " ";
				blRestriction = this.reservationNativeQueryServices.createIdRestriction("bl_id", budget.getBlId());
				flRestriction = this.reservationNativeQueryServices.createIdRestriction("fl_id", budget.getFlId());
				secondaryQuery += blRestriction + flRestriction;
				String sqlQuery = " AND item_id IN ( SELECT item_id FROM center_budget WHERE budget_term_id = "
						+ budgetTermId + " " + blRestriction + flRestriction + " )";
				secondaryQuery += sqlQuery;
				secondaryQuery = "(" + secondaryQuery + ") AS " + "budget_" + budget.getBlId() + "_" + budget.getFlId();
				primaryQuery.append(secondaryQuery).append(",");
				secondaryQuery = "";
			}
			primaryQuery.replace(primaryQuery.length() - 1, primaryQuery.length(), "");
			nativeQuery = entityManager.createNativeQuery(primaryQuery.toString());

			@SuppressWarnings("unchecked")
			List<Object[]> dataRecords = nativeQuery.getResultList();
			if (!dataRecords.isEmpty()) {
				Object expenseObject = dataRecords.get(0);
				final boolean isArray = expenseObject != null ? expenseObject.getClass().isArray() : false;
				Object[] expenseArray = null;
				if (isArray) {
					expenseArray = dataRecords.get(0);
				}
				for (int i = 0; i < budgetList.size(); i++) {
					BudgetTermReportInputs budgetResponse = new BudgetTermReportInputs();
					BudgetTermReportInputs budget = budgetList.get(i);
					budgetResponse.setBudgetTermId(budget.getBudgetTermId());
					budgetResponse.setBlId(budget.getBlId());
					budgetResponse.setFlId(budget.getFlId());
					budgetResponse.setBudget(budget.getBudget());
					if (isArray) {
						budgetResponse.setExpense((BigDecimal) expenseArray[i]);
					} else {
						budgetResponse.setExpense((BigDecimal) expenseObject);
					}
					centerBudgets.add(budgetResponse);
				}
			}
		}

		return centerBudgets;
	}

	public List<BudgetTermReportInputs> getCenterBudgetByBlIdFlId(BudgetTermReportInputs selectedBlFlIda) {
		List<BudgetTermReportInputs> centerBudgets = new ArrayList<>();
		String budgetTermRestriction;
		String blRestriction;
		String flRestriction;
		String itemRestriction;
		Query nativeQuery;
		String budgetTermQuery = "SELECT cb.budget_term_id,cb.bl_id, cb.fl_id, cb.item_id, SUM(budget) AS Budget "
				+ "FROM center_budget cb WHERE cb.budget_term_id=:budgetTermId AND cb.bl_id=:blId AND cb.fl_id=:flId "
				+ " GROUP BY cb.item_id, cb.budget_term_id,cb.bl_id,cb.fl_id";
		nativeQuery = entityManager.createNativeQuery(budgetTermQuery)
				.setParameter("budgetTermId", selectedBlFlIda.getBudgetTermId())
				.setParameter("blId", selectedBlFlIda.getBlId()).setParameter("flId", selectedBlFlIda.getFlId());

		@SuppressWarnings("unchecked")
		List<Object[]> budgets = nativeQuery.getResultList();

		List<BudgetTermReportInputs> budgetList = new ArrayList<>();
		if (!budgets.isEmpty() && budgets.get(0) != null) {
			budgetList = budgets
					.stream().map(data -> new BudgetTermReportInputs((Integer) data[0], (Integer) data[1],
							(Integer) data[2], (Integer) data[3], (BigDecimal) data[4], null))
					.collect(Collectors.toList());
		}

		StringBuilder primaryQuery = new StringBuilder("SELECT ");
		if (!budgetList.isEmpty()) {
			String secondaryQuery = "";
			for (BudgetTermReportInputs budget : budgetList) {
				secondaryQuery = "SELECT SUM(cost) AS expense FROM center_usage WHERE (1=1) ";
				budgetTermRestriction = this.reservationNativeQueryServices.createIdRestriction("budget_term_id",
						budget.getBudgetTermId());
				blRestriction = this.reservationNativeQueryServices.createIdRestriction("bl_id", budget.getBlId());
				flRestriction = this.reservationNativeQueryServices.createIdRestriction("fl_id", budget.getFlId());
				itemRestriction = this.reservationNativeQueryServices.createIdRestriction("item_id",
						budget.getItemId());
				secondaryQuery += budgetTermRestriction + blRestriction + flRestriction + itemRestriction;
				secondaryQuery = "(" + secondaryQuery + ") AS " + "budget_" + budget.getBlId() + "_" + budget.getFlId()
						+ budget.getItemId();
				primaryQuery.append(secondaryQuery).append(",");
				secondaryQuery = "";
			}
			primaryQuery.replace(primaryQuery.length() - 1, primaryQuery.length(), "");
			nativeQuery = entityManager.createNativeQuery(primaryQuery.toString());

			@SuppressWarnings("unchecked")
			List<Object[]> dataRecords = nativeQuery.getResultList();
			if (!dataRecords.isEmpty()) {
				Object expenseObject = dataRecords.get(0);
				final boolean isArray = expenseObject != null ? expenseObject.getClass().isArray() : false;
				for (int i = 0; i < budgetList.size(); i++) {
					BudgetTermReportInputs budgetResponse = new BudgetTermReportInputs();
					BudgetTermReportInputs budget = budgetList.get(i);
					budgetResponse.setBudgetTermId(budget.getBudgetTermId());
					budgetResponse.setBlId(budget.getBlId());
					budgetResponse.setFlId(budget.getFlId());
					budgetResponse.setItemId(budget.getItemId());
					budgetResponse.setBudget(budget.getBudget());
					if (isArray) {
			            Object[] expenseArray = (Object[]) expenseObject;
						budgetResponse.setExpense((BigDecimal) expenseArray[i]);
					} else {
						budgetResponse.setExpense((BigDecimal) expenseObject);
					}
					centerBudgets.add(budgetResponse);
				}
			}
		}

		return centerBudgets;
	}

	@SuppressWarnings("unchecked")
	public List<CenterUsage> getCenterUsageExpense(BudgetTermReportInputs budgetTInput) {
		String budgetTermRestriction = this.reservationNativeQueryServices.createIdRestriction("budget_term_id",
				budgetTInput.getBudgetTermId());
		String blRestriction = this.reservationNativeQueryServices.createIdRestriction("bl_id", budgetTInput.getBlId());
		String flRestriction = this.reservationNativeQueryServices.createIdRestriction("fl_id", budgetTInput.getFlId());

		String query = "SELECT center_usage_id,budget_term_id,bl_id,fl_id,item_id,quantity,rate,"
				+ "cost,over_usage_reason,entered_by,entered_date FROM center_usage WHERE (1=1) ";
		query += budgetTermRestriction + blRestriction + flRestriction;
		String sqlQuesry = " AND item_id IN ( SELECT item_id FROM center_budget WHERE (1=1) " + budgetTermRestriction
				+ blRestriction + flRestriction + ")";
		query += sqlQuesry;
		List<CenterUsage> centerUsages = new ArrayList<CenterUsage>();
		centerUsages = entityManager.createNativeQuery(query, CenterUsage.class).getResultList();

		return centerUsages;
	}

	@SuppressWarnings("unchecked")
	public List<CenterUsage> getCenterUsageItemExpense(BudgetTermReportInputs budgetTInput) {
		List<CenterUsage> centerUsages = new ArrayList<CenterUsage>();
		String query = "SELECT center_usage_id,budget_term_id,bl_id,fl_id,item_id,quantity,rate,"
				+ "cost,over_usage_reason,entered_by,entered_date FROM center_usage WHERE (1=1) ";
		String budgetTermRestriction = this.reservationNativeQueryServices.createIdRestriction("budget_term_id",
				budgetTInput.getBudgetTermId());
		String blRestriction = this.reservationNativeQueryServices.createIdRestriction("bl_id", budgetTInput.getBlId());
		String flRestriction = this.reservationNativeQueryServices.createIdRestriction("fl_id", budgetTInput.getFlId());
		String itemRestriction = this.reservationNativeQueryServices.createIdRestriction("item_id",
				budgetTInput.getItemId());
		query += budgetTermRestriction + blRestriction + flRestriction + itemRestriction;
		centerUsages = entityManager.createNativeQuery(query, CenterUsage.class).getResultList();
		return centerUsages;
	}

}
