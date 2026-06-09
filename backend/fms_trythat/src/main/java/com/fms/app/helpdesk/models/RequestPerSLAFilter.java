package com.fms.app.helpdesk.models;

import java.util.Date;

import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;

public class RequestPerSLAFilter {
	
	private Integer requestId;
	private Integer accountId;
	private Integer requestedBy;
	private String priority;
	private String status;
	private Date dateFrom;
	private Date dateTo;
	
	private Integer assignedTo;
	private Integer callTypeId;
	private Date compDateFrom;
	private Date compDateTo;
	private boolean responseTarget;
	private boolean completeTarget;
	private FilterDTOCopy filterDto;
	
	
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
	 * @return the dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}
	/**
	 * @param dateFrom the dateFrom to set
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	/**
	 * @return the dateTo
	 */
	public Date getDateTo() {
		return dateTo;
	}
	/**
	 * @param dateTo the dateTo to set
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	/**
	 * @return the assignedTo
	 */
	public Integer getAssignedTo() {
		return assignedTo;
	}
	/**
	 * @param assignedTo the assignedTo to set
	 */
	public void setAssignedTo(Integer assignedTo) {
		this.assignedTo = assignedTo;
	}

	/**
	 * @return the callTypeId
	 */
	public Integer getCallTypeId() {
		return callTypeId;
	}
	/**
	 * @param callTypeId the callTypeId to set
	 */
	public void setCallTypeId(Integer callTypeId) {
		this.callTypeId = callTypeId;
	}
	/**
	 * @return the compDateFrom
	 */
	public Date getCompDateFrom() {
		return compDateFrom;
	}
	/**
	 * @param compDateFrom the compDateFrom to set
	 */
	public void setCompDateFrom(Date compDateFrom) {
		this.compDateFrom = compDateFrom;
	}
	/**
	 * @return the compDateTo
	 */
	public Date getCompDateTo() {
		return compDateTo;
	}
	/**
	 * @param compDateTo the compDateTo to set
	 */
	public void setCompDateTo(Date compDateTo) {
		this.compDateTo = compDateTo;
	}
	/**
	 * @return the responseTarget
	 */
	public boolean isResponseTarget() {
		return responseTarget;
	}
	/**
	 * @param responseTarget the responseTarget to set
	 */
	public void setResponseTarget(boolean responseTarget) {
		this.responseTarget = responseTarget;
	}
	/**
	 * @return the completeTarget
	 */
	public boolean isCompleteTarget() {
		return completeTarget;
	}
	/**
	 * @param completeTarget the completeTarget to set
	 */
	public void setCompleteTarget(boolean completeTarget) {
		this.completeTarget = completeTarget;
	}
	public FilterDTOCopy getFilterDto() {
		return filterDto;
	}
	public void setFilterDto(FilterDTOCopy filterDto) {
		this.filterDto = filterDto;
	}

}
