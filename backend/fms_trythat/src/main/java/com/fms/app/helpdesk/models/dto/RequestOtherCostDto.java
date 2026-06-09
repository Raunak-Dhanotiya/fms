package com.fms.app.helpdesk.models.dto;

public class RequestOtherCostDto {

	private Integer requestOtherCostId;
	private Integer costTypeId;
	private Integer enteredBy;
	private double estimatedCost;
	private double actualCost;
	private String dateEntered;
	private String timeEntered;
	private Integer requestId; 
	private String description;
	private String costTypeCostType;
	private String userUserName;
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
	public String getDateEntered() {
		return dateEntered;
	}
	/**
	 * @param dateEntered the dateEntered to set
	 */
	public void setDateEntered(String dateEntered) {
		this.dateEntered = dateEntered;
	}
	/**
	 * @return the timeEntered
	 */
	public String getTimeEntered() {
		return timeEntered;
	}
	/**
	 * @param timeEntered the timeEntered to set
	 */
	public void setTimeEntered(String timeEntered) {
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
	public Integer getCostTypeId() {
		return costTypeId;
	}
	public void setCostTypeId(Integer costTypeId) {
		this.costTypeId = costTypeId;
	}
	
	public String getCostTypeCostType() {
		return costTypeCostType;
	}
	public void setCostTypeCostType(String costTypeCostType) {
		this.costTypeCostType = costTypeCostType;
	}
	public String getUserUserName() {
		return userUserName;
	}
	public void setUserUserName(String userUserName) {
		this.userUserName = userUserName;
	}
	
}
