package com.fms.app.reservation.models.dto;

public class ReserveResourcesDTO {
	private Integer reserveRsId;
	private Integer reserveId;
	private Integer blId;
	private Integer flId;
	private Integer rmId;
	private Integer resourcesId;
	private Integer qty;
	private String comments;
	private double costPerUnit;
	private double totalCost;
	private String status;
	private String type;

	public ReserveResourcesDTO() {
		super();
	}

	/**
	 * @return the reserveId
	 */
	public Integer getReserveId() {
		return reserveId;
	}

	/**
	 * @param reserveId the reserveId to set
	 */
	public void setReserveId(Integer reserveId) {
		this.reserveId = reserveId;
	}

	/**
	 * @return the blId
	 */
	public Integer getBlId() {
		return blId;
	}

	/**
	 * @param blId the blId to set
	 */
	public void setBlId(Integer blId) {
		this.blId = blId;
	}

	/**
	 * @return the flId
	 */
	public Integer getFlId() {
		return flId;
	}

	/**
	 * @param flId the flId to set
	 */
	public void setFlId(Integer flId) {
		this.flId = flId;
	}

	/**
	 * @return the rmId
	 */
	public Integer getRmId() {
		return rmId;
	}

	/**
	 * @param rmId the rmId to set
	 */
	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}

	/**
	 * @return the resourcesId
	 */
	public Integer getResourcesId() {
		return resourcesId;
	}

	/**
	 * @param resourcesId the resourcesId to set
	 */
	public void setResourcesId(Integer resourcesId) {
		this.resourcesId = resourcesId;
	}

	/**
	 * @return the qty
	 */
	public Integer getQty() {
		return qty;
	}

	/**
	 * @param qty the qty to set
	 */
	public void setQty(Integer qty) {
		this.qty = qty;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the costPerUnit
	 */
	public double getCostPerUnit() {
		return costPerUnit;
	}

	/**
	 * @param costPerUnit the costPerUnit to set
	 */
	public void setCostPerUnit(double costPerUnit) {
		this.costPerUnit = costPerUnit;
	}

	/**
	 * @return the totalCost
	 */
	public double getTotalCost() {
		return totalCost;
	}

	/**
	 * @param totalCost the totalCost to set
	 */
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public Integer getReserveRsId() {
		return reserveRsId;
	}

	public void setReserveRsId(Integer reserveRsId) {
		this.reserveRsId = reserveRsId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
