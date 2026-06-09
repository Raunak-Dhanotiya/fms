package com.fms.app.helpdesk.models.dto;

public class RequestFilterDTO {

	private Integer requestId;
	private Integer accountId;
	private Integer requestedBy;
	private Integer probTypeId;
	private Integer subProbTypeId;
	private String priority;
	private String status;
	private String selectedStat;
	/**
	 * @return the requestId
	 */
	public Integer getRequestId() {
		return requestId;
	}
	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	/**
	 * @return the accountId
	 */
	public Integer getAccountId() {
		return accountId;
	}
	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	/**
	 * @return the requestedBy
	 */
	public Integer getRequestedBy() {
		return requestedBy;
	}
	/**
	 * @param requestedBy the requestedBy to set
	 */
	public void setRequestedBy(Integer requestedBy) {
		this.requestedBy = requestedBy;
	}
	/**
	 * @return the probTypeId
	 */
	public Integer getProbTypeId() {
		return probTypeId;
	}
	/**
	 * @param probTypeId the probTypeId to set
	 */
	public void setProbTypeId(Integer probTypeId) {
		this.probTypeId = probTypeId;
	}
	/**
	 * @return the subProbTypeId
	 */
	public Integer getSubProbTypeId() {
		return subProbTypeId;
	}
	/**
	 * @param subProbTypeId the subProbTypeId to set
	 */
	public void setSubProbTypeId(Integer subProbTypeId) {
		this.subProbTypeId = subProbTypeId;
	}
	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the selectedStat
	 */
	public String getSelectedStat() {
		return selectedStat;
	}
	/**
	 * @param selectedStat the selectedStat to set
	 */
	public void setSelectedStat(String selectedStat) {
		this.selectedStat = selectedStat;
	}

}
