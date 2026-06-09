package com.fms.app.response.output;

import java.util.Date;

public class HelpdeskRequestOutput {

	private int requestId;

	private int loggedBy;

	private int clId;

	private int requestedBy;

	private int productId;

	private int probTypeId;

	private int subprobTypeId;

	private String priority;

	private int callTypeId;

	private Date respondByDate;

	private Date completeByDate;

	private int assignedTo;

	private String description;

	private Date dateRequested;

	private Date dateCompleted;

	private String status;

	private String problemCode;

	private int causeType;

	private String repairType;

	private int completedBy;

	private String completionNotes;

	private Date dateResolution;

	private int resolutionBy;
	
	private String clientClName;
	
	private String callTypeCallType;
	
	private String probTypeProbType;

	public HelpdeskRequestOutput() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the requestId
	 */
	public int getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the loggedBy
	 */
	public int getLoggedBy() {
		return loggedBy;
	}

	/**
	 * @param loggedBy the loggedBy to set
	 */
	public void setLoggedBy(int loggedBy) {
		this.loggedBy = loggedBy;
	}

	/**
	 * @return the clId
	 */
	public int getClId() {
		return clId;
	}

	/**
	 * @param clId the clId to set
	 */
	public void setClId(int clId) {
		this.clId = clId;
	}

	/**
	 * @return the requestedBy
	 */
	public int getRequestedBy() {
		return requestedBy;
	}

	/**
	 * @param requestedBy the requestedBy to set
	 */
	public void setRequestedBy(int requestedBy) {
		this.requestedBy = requestedBy;
	}

	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

	/**
	 * @return the probTypeId
	 */
	public int getProbTypeId() {
		return probTypeId;
	}

	/**
	 * @param probTypeId the probTypeId to set
	 */
	public void setProbTypeId(int probTypeId) {
		this.probTypeId = probTypeId;
	}

	/**
	 * @return the subprobTypeId
	 */
	public int getSubprobTypeId() {
		return subprobTypeId;
	}

	/**
	 * @param subprobTypeId the subprobTypeId to set
	 */
	public void setSubprobTypeId(int subprobTypeId) {
		this.subprobTypeId = subprobTypeId;
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
	 * @return the callTypeId
	 */
	public int getCallTypeId() {
		return callTypeId;
	}

	/**
	 * @param callTypeId the callTypeId to set
	 */
	public void setCallTypeId(int callTypeId) {
		this.callTypeId = callTypeId;
	}

	/**
	 * @return the respondByDate
	 */
	public Date getRespondByDate() {
		return respondByDate;
	}

	/**
	 * @param respondByDate the respondByDate to set
	 */
	public void setRespondByDate(Date respondByDate) {
		this.respondByDate = respondByDate;
	}

	/**
	 * @return the completeByDate
	 */
	public Date getCompleteByDate() {
		return completeByDate;
	}

	/**
	 * @param completeByDate the completeByDate to set
	 */
	public void setCompleteByDate(Date completeByDate) {
		this.completeByDate = completeByDate;
	}

	/**
	 * @return the assignedTo
	 */
	public int getAssignedTo() {
		return assignedTo;
	}

	/**
	 * @param assignedTo the assignedTo to set
	 */
	public void setAssignedTo(int assignedTo) {
		this.assignedTo = assignedTo;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the dateRequested
	 */
	public Date getDateRequested() {
		return dateRequested;
	}

	/**
	 * @param dateRequested the dateRequested to set
	 */
	public void setDateRequested(Date dateRequested) {
		this.dateRequested = dateRequested;
	}

	/**
	 * @return the dateCompleted
	 */
	public Date getDateCompleted() {
		return dateCompleted;
	}

	/**
	 * @param dateCompleted the dateCompleted to set
	 */
	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;
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
	 * @return the problemCode
	 */
	public String getProblemCode() {
		return problemCode;
	}

	/**
	 * @param problemCode the problemCode to set
	 */
	public void setProblemCode(String problemCode) {
		this.problemCode = problemCode;
	}

	/**
	 * @return the causeType
	 */
	public int getCauseType() {
		return causeType;
	}

	/**
	 * @param causeType the causeType to set
	 */
	public void setCauseType(int causeType) {
		this.causeType = causeType;
	}

	/**
	 * @return the repairType
	 */
	public String getRepairType() {
		return repairType;
	}

	/**
	 * @param repairType the repairType to set
	 */
	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}

	/**
	 * @return the completedBy
	 */
	public int getCompletedBy() {
		return completedBy;
	}

	/**
	 * @param completedBy the completedBy to set
	 */
	public void setCompletedBy(int completedBy) {
		this.completedBy = completedBy;
	}

	/**
	 * @return the completionNotes
	 */
	public String getCompletionNotes() {
		return completionNotes;
	}

	/**
	 * @param completionNotes the completionNotes to set
	 */
	public void setCompletionNotes(String completionNotes) {
		this.completionNotes = completionNotes;
	}

	/**
	 * @return the dateResolution
	 */
	public Date getDateResolution() {
		return dateResolution;
	}

	/**
	 * @param dateResolution the dateResolution to set
	 */
	public void setDateResolution(Date dateResolution) {
		this.dateResolution = dateResolution;
	}

	/**
	 * @return the resolutionBy
	 */
	public int getResolutionBy() {
		return resolutionBy;
	}

	/**
	 * @param resolutionBy the resolutionBy to set
	 */
	public void setResolutionBy(int resolutionBy) {
		this.resolutionBy = resolutionBy;
	}

	public String getClientClName() {
		return clientClName;
	}

	public void setClientClName(String clientClName) {
		this.clientClName = clientClName;
	}

	public String getCallTypeCallType() {
		return callTypeCallType;
	}

	public void setCallTypeCallType(String callTypeCallType) {
		this.callTypeCallType = callTypeCallType;
	}

	public String getProbTypeProbType() {
		return probTypeProbType;
	}

	public void setProbTypeProbType(String probTypeProbType) {
		this.probTypeProbType = probTypeProbType;
	}
	
}
