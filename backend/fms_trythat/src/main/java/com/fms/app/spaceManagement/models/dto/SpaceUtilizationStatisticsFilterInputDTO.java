package com.fms.app.spaceManagement.models.dto;

public class SpaceUtilizationStatisticsFilterInputDTO {
	private Boolean division;
	private Boolean department;
	private Boolean building;
	private Boolean floor;

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

}
