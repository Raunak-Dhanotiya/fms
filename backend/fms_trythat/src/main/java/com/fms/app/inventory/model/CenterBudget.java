package com.fms.app.inventory.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "center_budget")
@Table(name = "center_budget")
public class CenterBudget {

	@Id
	@Column(name="center_budget_id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer centerBudgetId;

    @Column(name = "budget_term_id" )
	private Integer budgetTermId;

    @Column(name = "bl_id" )
	private Integer blId;
    
    @Column(name = "fl_id" )
	private Integer flId;
    
    @Column(name = "item_id" )
	private Integer itemId;

    @Column(name = "budget" )
	private double budget=0.0;
    
    @Column(name = "entered_by" )
	private Integer enteredBy;
    
    @Column(name = "entered_date", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date enteredDate;

	public Integer getCenterBudgetId() {
		return centerBudgetId;
	}

	public void setCenterBudgetId(Integer centerBudgetId) {
		this.centerBudgetId = centerBudgetId;
	}

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

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public Integer getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(Integer enteredBy) {
		this.enteredBy = enteredBy;
	}

	public Date getEnteredDate() {
		return enteredDate;
	}

	public void setEnteredDate(Date enteredDate) {
		this.enteredDate = enteredDate;
	}

	public CenterBudget(Integer centerBudgetId, Integer budgetTermId, Integer blId, Integer flId, Integer itemId,
			double budget, Integer enteredBy, Date enteredDate) {
		super();
		this.centerBudgetId = centerBudgetId;
		this.budgetTermId = budgetTermId;
		this.blId = blId;
		this.flId = flId;
		this.itemId = itemId;
		this.budget = budget;
		this.enteredBy = enteredBy;
		this.enteredDate = enteredDate;
	}

	public CenterBudget() {
		super();
	}


}
