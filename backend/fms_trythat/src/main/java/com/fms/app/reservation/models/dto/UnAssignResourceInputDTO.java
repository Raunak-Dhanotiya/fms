package com.fms.app.reservation.models.dto;

public class UnAssignResourceInputDTO {
	private Integer blId;
	private Integer flId;
	private Integer rmId;
	private Integer resourcesId;
	private Integer rmResourcesId;
	public UnAssignResourceInputDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UnAssignResourceInputDTO(Integer blId, Integer flId, Integer rmId, Integer resourcesId,Integer rmResourcesId) {
		super();
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
		this.resourcesId = resourcesId;
		this.rmResourcesId = rmResourcesId;
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
	public Integer getRmId() {
		return rmId;
	}
	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}
	public Integer getResourcesId() {
		return resourcesId;
	}
	public void setResourcesId(Integer resourcesId) {
		this.resourcesId = resourcesId;
	}
	public Integer getRmResourcesId() {
		return rmResourcesId;
	}
	public void setRmResourcesId(Integer rmResourcesId) {
		this.rmResourcesId = rmResourcesId;
	}
	
}
