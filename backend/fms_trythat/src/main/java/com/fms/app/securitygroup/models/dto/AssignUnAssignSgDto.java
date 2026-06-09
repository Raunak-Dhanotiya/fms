package com.fms.app.securitygroup.models.dto;

public class AssignUnAssignSgDto {
	private Integer userId;
	private Integer userRoleId;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

}
