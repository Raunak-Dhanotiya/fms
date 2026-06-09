package com.fms.app.inventory.model.dto;


public class BudgetTermOutputDto {
	
	private Integer budgetTermId;

	private String name;

	private String dateFrom;

	private String dateTo;

	public Integer getBudgetTermId() {
		return budgetTermId;
	}

	public void setBudgetTermId(Integer budgetTermId) {
		this.budgetTermId = budgetTermId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public BudgetTermOutputDto(Integer budgetTermId, String name, String dateFrom, String dateTo) {
		super();
		this.budgetTermId = budgetTermId;
		this.name = name;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public BudgetTermOutputDto() {
		super();
	}
	
	
}
