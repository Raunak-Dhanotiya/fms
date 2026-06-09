package com.fms.app.reservation.models.dto;

public class RmResourcesDTO {
	private Integer blId;
	private Integer flId;
	private Integer rmId;
	private Integer resourcesId;
	private Integer quanity;
	private double costPerUnit;
	private String comments;
	private String resourceDescription;
	private String resourceTitle;
	private Integer rmResourcesId;
	public RmResourcesDTO() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @return the quanity
	 */
	public Integer getQuanity() {
		return quanity;
	}
	/**
	 * @param quanity the quanity to set
	 */
	public void setQuanity(Integer quanity) {
		this.quanity = quanity;
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
	 * @return the resourceDescription
	 */
	public String getResourceDescription() {
		return resourceDescription;
	}
	/**
	 * @param resourceDescription the resourceDescription to set
	 */
	public void setResourceDescription(String resourceDescription) {
		this.resourceDescription = resourceDescription;
	}
	/**
	 * @return the resourceTitle
	 */
	public String getResourceTitle() {
		return resourceTitle;
	}
	/**
	 * @param resourceTitle the resourceTitle to set
	 */
	public void setResourceTitle(String resourceTitle) {
		this.resourceTitle = resourceTitle;
	}
	public Integer getRmResourcesId() {
		return rmResourcesId;
	}
	public void setRmResourcesId(Integer rmResourcesId) {
		this.rmResourcesId = rmResourcesId;
	}
		
}
