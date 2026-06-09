package com.fms.app.response.input;

public class ScreenAssignInput {
	private Integer userRoleId;
	
	/**
	 * @return the userRoleId
	 */
	public Integer getUserRoleId() {
		return userRoleId;
	}

	/**
	 * @param userRoleId the userRoleId to set
	 */
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public ScreenAssignInput() {
		super();
	}

	/**
	 * @param userRoleId
	 */
	public ScreenAssignInput(Integer userRoleId) {
		super();
		this.userRoleId = userRoleId;
	}

}
