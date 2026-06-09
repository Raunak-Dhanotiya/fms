package com.fms.app.inventory.model.dto;

import java.math.BigDecimal;


public class BudgetTermReportInputs {
	private Integer budgetTermId;
	private Integer blId;
	private Integer flId;
	private Integer itemId;
	private BigDecimal budget;
	private BigDecimal expense;
	
	public Integer getBudgetTermId() {
		return budgetTermId;
	}
	public void setBudgetTermId(Integer budgetTermId) {
		this.budgetTermId = budgetTermId;
	}
	public Integer getBlId() {
		return blId;
	}
	public void setBlId(Integer blId) {
		this.blId = blId;
	}
	public Integer getFlId() {
		return flId;
	}
	public void setFlId(Integer flId) {
		this.flId = flId;
	}
	public BigDecimal getBudget() {
		return budget;
	}
	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}
	public BigDecimal getExpense() {
		return expense;
	}
	public void setExpense(BigDecimal expense) {
		this.expense = expense;
	}
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public BudgetTermReportInputs() {
		super();
	}
	public BudgetTermReportInputs(Integer budgetTermId, Integer blId, Integer flId,Integer itemId, BigDecimal budget, BigDecimal expense) {
		super();
		this.budgetTermId = budgetTermId;
		this.blId = blId;
		this.flId = flId;
		this.itemId = itemId;
		this.budget = budget;
		this.expense = expense;
	}
	
}
