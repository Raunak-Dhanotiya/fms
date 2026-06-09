package com.fms.app.reservation.models.dto;

public class ResourcesDTO {
	private Integer resourcesId;
	private String description;
	private String title;
	private String type;
	private Integer quanity;
	private double costPerUnit;
	private String isReusable;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getQuanity() {
		return quanity;
	}
	public void setQuanity(Integer quanity) {
		this.quanity = quanity;
	}
	public double getCostPerUnit() {
		return costPerUnit;
	}
	public void setCostPerUnit(double costPerUnit) {
		this.costPerUnit = costPerUnit;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIsReusable() {
		return isReusable;
	}
	public void setIsReusable(String isReusable) {
		this.isReusable = isReusable;
	}
	public Integer getResourcesId() {
		return resourcesId;
	}
	public void setResourcesId(Integer resourcesId) {
		this.resourcesId = resourcesId;
	}
}
