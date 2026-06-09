package com.fms.app.helpdesk.models;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.app.userModels.User;

@Entity(name = "request_other_cost")
@Table(name = "request_other_cost")
public class RequestOtherCost {

	@Id
	@Column(name = "request_other_cost_id",nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer requestOtherCostId;

	@Column(name = "cost_type_id")
	private Integer costTypeId;

	@Column(name = "entered_by")
	private Integer enteredBy;

	@Column(name = "estimated_cost")
	private double estimatedCost;

	@Column(name = "actual_cost")
	private double actualCost;

	@Column(name = "date_entered")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateEntered;
	
	@Column(name = "time_entered")
	@JsonFormat(pattern="HH:mm:ss")
	private Time timeEntered;
	
	@Column(name = "request_id")
	private Integer requestId;
	
	@Column(name = "description")
	private String description;


	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cost_type_id", insertable = false, updatable = false, nullable = false)
	private CostType costType;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entered_by",referencedColumnName = "user_id", insertable = false, updatable = false)
	private User user;

	/**
	 * @return the requestOtherCostId
	 */
	public Integer getRequestOtherCostId() {
		return requestOtherCostId;
	}

	/**
	 * @param requestOtherCostId the requestOtherCostId to set
	 */
	public void setRequestOtherCostId(Integer requestOtherCostId) {
		this.requestOtherCostId = requestOtherCostId;
	}

	/**
	 * @return the costTypeId
	 */
	public Integer getCostTypeId() {
		return costTypeId;
	}

	/**
	 * @param costTypeId the costTypeId to set
	 */
	public void setCostTypeId(Integer costTypeId) {
		this.costTypeId = costTypeId;
	}

	public CostType getCostType() {
		return costType;
	}

	public void setCostType(CostType costType) {
		this.costType = costType;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the enteredBy
	 */
	public Integer getEnteredBy() {
		return enteredBy;
	}

	/**
	 * @param enteredBy the enteredBy to set
	 */
	public void setEnteredBy(Integer enteredBy) {
		this.enteredBy = enteredBy;
	}

	/**
	 * @return the amount
	 */
	public double getEstimatedCost() {
		return estimatedCost;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setEstimatedCost(double estimatedCost) {
		this.estimatedCost = estimatedCost;
	}

	/**
	 * @return the dateEntered
	 */
	public Date getDateEntered() {
		return dateEntered;
	}

	/**
	 * @param dateEntered the dateEntered to set
	 */
	public void setDateEntered(Date dateEntered) {
		this.dateEntered = dateEntered;
	}

	/**
	 * @return the timeEntered
	 */
	public Time getTimeEntered() {
		return timeEntered;
	}

	/**
	 * @param timeEntered the timeEntered to set
	 */
	public void setTimeEntered(Time timeEntered) {
		this.timeEntered = timeEntered;
	}

	public Integer getRequestId() {
		return requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getActualCost() {
		return actualCost;
	}

	public void setActualCost(double actualCost) {
		this.actualCost = actualCost;
	}
	

	public RequestOtherCost(Integer requestOtherCostId, Integer costTypeId, Integer enteredBy, double estimatedCost, double actualCost,
			Date dateEntered, Time timeEntered,Integer requestId,String description ) {
		super();
		this.requestOtherCostId = requestOtherCostId;
		this.costTypeId = costTypeId;
		this.enteredBy = enteredBy;
		this.estimatedCost = estimatedCost;
		this.dateEntered = dateEntered;
		this.timeEntered = timeEntered;
		this.requestId = requestId;
		this.description = description;
		this.actualCost = actualCost;
	}

	public RequestOtherCost() {
		super();
		// TODO Auto-generated constructor stub
	}

}
