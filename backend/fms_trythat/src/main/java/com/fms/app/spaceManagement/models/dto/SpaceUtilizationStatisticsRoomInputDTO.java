package com.fms.app.spaceManagement.models.dto;

public class SpaceUtilizationStatisticsRoomInputDTO {
	private Boolean division;
	private Boolean department;
	private Boolean building;
	private Boolean floor;
	private Integer blId;
	private Integer flId;
	private Integer divId;
	private Integer depId;
	public Boolean getDivision() {
		return division;
	}
	public void setDivision(Boolean division) {
		this.division = division;
	}
	public Boolean getDepartment() {
		return department;
	}
	public void setDepartment(Boolean department) {
		this.department = department;
	}
	public Boolean getBuilding() {
		return building;
	}
	public void setBuilding(Boolean building) {
		this.building = building;
	}
	public Boolean getFloor() {
		return floor;
	}
	public void setFloor(Boolean floor) {
		this.floor = floor;
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
	public Integer getDivId() {
		return divId;
	}
	public void setDivId(Integer divId) {
		this.divId = divId;
	}
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	
}
