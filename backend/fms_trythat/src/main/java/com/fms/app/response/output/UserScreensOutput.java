package com.fms.app.response.output;

public class UserScreensOutput {

	private Integer userScreensNum;
	private Integer userRoleId;
	private Integer screenNum;
	private Integer processId;
	private String screenName;
	private Integer subProcessId;
	private String processCode;
	private String subProcessCode;
	/**
	 * @return the userScreensNum
	 */
	public Integer getUserScreensNum() {
		return userScreensNum;
	}
	/**
	 * @param userScreensNum the userScreensNum to set
	 */
	public void setUserScreensNum(Integer userScreensNum) {
		this.userScreensNum = userScreensNum;
	}

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
	/**
	 * @return the screenNum
	 */
	public Integer getScreenNum() {
		return screenNum;
	}
	/**
	 * @param screenNum the screenNum to set
	 */
	public void setScreenNum(Integer screenNum) {
		this.screenNum = screenNum;
	}
	/**
	 * @return the processId
	 */
	public Integer getProcessId() {
		return processId;
	}
	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}
	/**
	 * @param screenName the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	/**
	 * @return the subProcessId
	 */
	public Integer getSubProcessId() {
		return subProcessId;
	}
	/**
	 * @param subProcessId the subProcessId to set
	 */
	public void setSubProcessId(Integer subProcessId) {
		this.subProcessId = subProcessId;
	}
	/**
	 * 
	 */
	public UserScreensOutput() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param userScreensNum
	 * @param userRoleId
	 * @param screenNum
	 * @param processId
	 * @param screenName
	 * @param subProcessId
	 */
	public UserScreensOutput(Integer userScreensNum, Integer userRoleId, Integer screenNum, Integer processId, String screenName,
			Integer subProcessId) {
		super();
		this.userScreensNum = userScreensNum;
		this.userRoleId = userRoleId;
		this.screenNum = screenNum;
		this.processId = processId;
		this.screenName = screenName;
		this.subProcessId = subProcessId;
	}
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getSubProcessCode() {
		return subProcessCode;
	}
	public void setSubProcessCode(String subProcessCode) {
		this.subProcessCode = subProcessCode;
	}

}
