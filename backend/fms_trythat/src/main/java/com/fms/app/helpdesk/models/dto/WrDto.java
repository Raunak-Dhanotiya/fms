package com.fms.app.helpdesk.models.dto;

public class WrDto {

	private Integer wrId;

	private Integer parentWrId;

	private String status;

	private Integer siteId;

	private Integer blId;

	private Integer flId;

	private Integer rmId;

	private Integer eqId;

	private Integer probTypeId;

	private String description;

	private String dateRequested;

	private String timeRequested;

	private String dateCompleted;

	private String timeCompleted;

	private String dateResponded;

	private String timeResponded;

	private String escDateResponded;

	private String escTimeResponded;

	private String escDateCompleted;

	private String escTimeCompleted;

	private Integer slaRequestParametersId;

	private Integer requestedBy;

	private Integer requestedFor;

	private String dateToPerform;

	private String problemTypeString;
	
	private Integer selectedSlaRespParamId;
	
	private String comments;
	
	private String isRequestor;
	
	private String isApprover;
	
	private String isTechnician;
	
	private String isSupervisor;
	
	private String eqDescription;
	
	private Integer slaResponseParametersId;
	
	private Integer completeBy;
	
	private Integer loggedInTechnicianId;
	
	private Integer docBucketId;
	
	private Integer scheduleId;
	
	private Integer planId;
	
	private String building;
	
	private String floor;
	
	private String room;
	
	private String eqCode;
	
	/**
	 * @return the wrId
	 */
	public Integer getWrId() {
		return wrId;
	}

	/**
	 * @param wrId the wrId to set
	 */
	public void setWrId(Integer wrId) {
		this.wrId = wrId;
	}

	/**
	 * @return the parentWrId
	 */
	public Integer getParentWrId() {
		return parentWrId;
	}

	/**
	 * @param parentWrId the parentWrId to set
	 */
	public void setParentWrId(Integer parentWrId) {
		this.parentWrId = parentWrId;
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
	 * @return the siteId
	 */
	public Integer getSiteId() {
		return siteId;
	}

	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	/**
	 * @return the blId
	 */
	public Integer getBlId() {
		return blId;
	}

	/**
	 * @param blId the blId to set
	 */
	public void setBlId(Integer blId) {
		this.blId = blId;
	}

	/**
	 * @return the flId
	 */
	public Integer getFlId() {
		return flId;
	}

	/**
	 * @param flId the flId to set
	 */
	public void setFlId(Integer flId) {
		this.flId = flId;
	}

	/**
	 * @return the rmId
	 */
	public Integer getRmId() {
		return rmId;
	}

	/**
	 * @param rmId the rmId to set
	 */
	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}

	/**
	 * @return the eqId
	 */
	public Integer getEqId() {
		return eqId;
	}

	/**
	 * @param eqId the eqId to set
	 */
	public void setEqId(Integer eqId) {
		this.eqId = eqId;
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
	public String getDateRequested() {
		return dateRequested;
	}

	/**
	 * @param dateRequested the dateRequested to set
	 */
	public void setDateRequested(String dateRequested) {
		this.dateRequested = dateRequested;
	}

	/**
	 * @return the timeRequested
	 */
	public String getTimeRequested() {
		return timeRequested;
	}

	/**
	 * @param timeRequested the timeRequested to set
	 */
	public void setTimeRequested(String timeRequested) {
		this.timeRequested = timeRequested;
	}

	/**
	 * @return the dateCompleted
	 */
	public String getDateCompleted() {
		return dateCompleted;
	}

	/**
	 * @param dateCompleted the dateCompleted to set
	 */
	public void setDateCompleted(String dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

	/**
	 * @return the timeCompleted
	 */
	public String getTimeCompleted() {
		return timeCompleted;
	}

	/**
	 * @param timeCompleted the timeCompleted to set
	 */
	public void setTimeCompleted(String timeCompleted) {
		this.timeCompleted = timeCompleted;
	}

	/**
	 * @return the dateResponded
	 */
	public String getDateResponded() {
		return dateResponded;
	}

	/**
	 * @param dateResponded the dateResponded to set
	 */
	public void setDateResponded(String dateResponded) {
		this.dateResponded = dateResponded;
	}

	/**
	 * @return the timeResponded
	 */
	public String getTimeResponded() {
		return timeResponded;
	}

	/**
	 * @param timeResponded the timeResponded to set
	 */
	public void setTimeResponded(String timeResponded) {
		this.timeResponded = timeResponded;
	}

	/**
	 * @return the escDateResponded
	 */
	public String getEscDateResponded() {
		return escDateResponded;
	}

	/**
	 * @param escDateResponded the escDateResponded to set
	 */
	public void setEscDateResponded(String escDateResponded) {
		this.escDateResponded = escDateResponded;
	}

	/**
	 * @return the escTimeResponded
	 */
	public String getEscTimeResponded() {
		return escTimeResponded;
	}

	/**
	 * @param escTimeResponded the escTimeResponded to set
	 */
	public void setEscTimeResponded(String escTimeResponded) {
		this.escTimeResponded = escTimeResponded;
	}

	/**
	 * @return the escDateCompleted
	 */
	public String getEscDateCompleted() {
		return escDateCompleted;
	}

	/**
	 * @param escDateCompleted the escDateCompleted to set
	 */
	public void setEscDateCompleted(String escDateCompleted) {
		this.escDateCompleted = escDateCompleted;
	}

	/**
	 * @return the escTimeCompleted
	 */
	public String getEscTimeCompleted() {
		return escTimeCompleted;
	}

	/**
	 * @param escTimeCompleted the escTimeCompleted to set
	 */
	public void setEscTimeCompleted(String escTimeCompleted) {
		this.escTimeCompleted = escTimeCompleted;
	}

	/**
	 * @return the slaRequestParametersId
	 */
	public Integer getSlaRequestParametersId() {
		return slaRequestParametersId;
	}

	/**
	 * @param slaRequestParametersId the slaRequestParametersId to set
	 */
	public void setSlaRequestParametersId(Integer slaRequestParametersId) {
		this.slaRequestParametersId = slaRequestParametersId;
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
	 * @return the requestedFor
	 */
	public Integer getRequestedFor() {
		return requestedFor;
	}

	/**
	 * @param requestedFor the requestedFor to set
	 */
	public void setRequestedFor(Integer requestedFor) {
		this.requestedFor = requestedFor;
	}

	/**
	 * @return the dateToPerform
	 */
	public String getDateToPerform() {
		return dateToPerform;
	}

	/**
	 * @param dateToPerform the dateToPerform to set
	 */
	public void setDateToPerform(String dateToPerform) {
		this.dateToPerform = dateToPerform;
	}

	/**
	 * @return the problemTypeString
	 */
	public String getProblemTypeString() {
		return problemTypeString;
	}

	/**
	 * @param problemTypeString the problemTypeString to set
	 */
	public void setProblemTypeString(String problemTypeString) {
		this.problemTypeString = problemTypeString;
	}

	/**
	 * @return the selectedSlaRespParamId
	 */
	public Integer getSelectedSlaRespParamId() {
		return selectedSlaRespParamId;
	}

	/**
	 * @param selectedSlaRespParamId the selectedSlaRespParamId to set
	 */
	public void setSelectedSlaRespParamId(Integer selectedSlaRespParamId) {
		this.selectedSlaRespParamId = selectedSlaRespParamId;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the isRequestor
	 */
	public String getIsRequestor() {
		return isRequestor;
	}

	/**
	 * @param isRequestor the isRequestor to set
	 */
	public void setIsRequestor(String isRequestor) {
		this.isRequestor = isRequestor;
	}

	/**
	 * @return the isApprover
	 */
	public String getIsApprover() {
		return isApprover;
	}

	/**
	 * @param isApprover the isApprover to set
	 */
	public void setIsApprover(String isApprover) {
		this.isApprover = isApprover;
	}

	/**
	 * @return the isTechnician
	 */
	public String getIsTechnician() {
		return isTechnician;
	}

	/**
	 * @param isTechnician the isTechnician to set
	 */
	public void setIsTechnician(String isTechnician) {
		this.isTechnician = isTechnician;
	}

	/**
	 * @return the isSupervisor
	 */
	public String getIsSupervisor() {
		return isSupervisor;
	}

	/**
	 * @param isSupervisor the isSupervisor to set
	 */
	public void setIsSupervisor(String isSupervisor) {
		this.isSupervisor = isSupervisor;
	}

	/**
	 * @return the eqDescription
	 */
	public String getEqDescription() {
		return eqDescription;
	}

	/**
	 * @param eqDescription the eqDescription to set
	 */
	public void setEqDescription(String eqDescription) {
		this.eqDescription = eqDescription;
	}

	/**
	 * @return the slaResponseParametersId
	 */
	public Integer getSlaResponseParametersId() {
		return slaResponseParametersId;
	}

	/**
	 * @param slaResponseParametersId the slaResponseParametersId to set
	 */
	public void setSlaResponseParametersId(Integer slaResponseParametersId) {
		this.slaResponseParametersId = slaResponseParametersId;
	}

	/**
	 * @return the completeBy
	 */
	public Integer getCompleteBy() {
		return completeBy;
	}

	/**
	 * @param completeBy the completeBy to set
	 */
	public void setCompleteBy(Integer completeBy) {
		this.completeBy = completeBy;
	}

	/**
	 * @return the loggedInTechnicianId
	 */
	public Integer getLoggedInTechnicianId() {
		return loggedInTechnicianId;
	}

	/**
	 * @param loggedInTechnicianId the loggedInTechnicianId to set
	 */
	public void setLoggedInTechnicianId(Integer loggedInTechnicianId) {
		this.loggedInTechnicianId = loggedInTechnicianId;
	}

	/**
	 * @return the docBucketId
	 */
	public Integer getDocBucketId() {
		return docBucketId;
	}

	/**
	 * @param docBucketId the docBucketId to set
	 */
	public void setDocBucketId(Integer docBucketId) {
		this.docBucketId = docBucketId;
	}

	/**
	 * @return the scheduleId
	 */
	public Integer getScheduleId() {
		return scheduleId;
	}

	/**
	 * @param scheduleId the scheduleId to set
	 */
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	/**
	 * @return the planId
	 */
	public Integer getPlanId() {
		return planId;
	}

	/**
	 * @param planId the planId to set
	 */
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getEqCode() {
		return eqCode;
	}

	public void setEqCode(String eqCode) {
		this.eqCode = eqCode;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
	
}
