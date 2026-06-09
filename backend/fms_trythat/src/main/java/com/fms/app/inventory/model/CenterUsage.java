package com.fms.app.inventory.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "center_usage")
@Table(name = "center_usage")
public class CenterUsage {

	@Id
	@Column(name="center_usage_id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer centerUsageId;

    @Column(name = "quantity" )
	private Integer quantity;
    
    @Column(name = "rate" )
	private double rate=0.0;

    @Column(name = "cost" )
	private double cost=0.0;

    @Column(name = "over_usage_reason" )
	private String overUsageReason;

    @Column(name = "entered_date", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date enteredDate;

    @Column(name = "entered_by" )
	private Integer enteredBy;

	@Column(name = "budget_term_id" )
	private Integer budgetTermId;

	@Column(name = "bl_id" )
	private Integer blId;

	@Column(name = "fl_id" )
	private Integer flId;

	@Column(name = "item_id" )
	private Integer itemId;

	public Integer getCenterUsageId() {
		return centerUsageId;
	}

	public void setCenterUsageId(Integer centerUsageId) {
		this.centerUsageId = centerUsageId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getOverUsageReason() {
		return overUsageReason;
	}

	public void setOverUsageReason(String overUsageReason) {
		this.overUsageReason = overUsageReason;
	}

	public Date getEnteredDate() {
		return enteredDate;
	}

	public void setEnteredDate(Date enteredDate) {
		this.enteredDate = enteredDate;
	}

	public Integer getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(Integer enteredBy) {
		this.enteredBy = enteredBy;
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

	public CenterUsage(Integer centerUsageId, Integer quantity, double rate, double cost,
			String overUsageReason, Date enteredDate, Integer enteredBy, Integer budgetTermId, Integer blId,
			Integer flId, Integer itemId) {
		super();
		this.centerUsageId = centerUsageId;
		this.quantity = quantity;
		this.rate = rate;
		this.cost = cost;
		this.overUsageReason = overUsageReason;
		this.enteredDate = enteredDate;
		this.enteredBy = enteredBy;
		this.budgetTermId = budgetTermId;
		this.blId = blId;
		this.flId = flId;
		this.itemId = itemId;
	}

	public CenterUsage() {
		super();
	}

}

