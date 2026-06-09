package com.fms.app.reservation.models.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReserveDTO {
	private Integer reserveId;
	private Integer loggedBy;
	private Integer requestedBy;
	private Integer requestedFor;
	private String status;
	private String meetingName;
	private String comments;
	private String bookingType;
	private String recurringRule;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateStart;

	@JsonFormat(pattern = "HH:mm:ss")
	private Time timeStart;

	@JsonFormat(pattern = "HH:mm:ss")
	private Time timeEnd;

	private Integer blId;
	private Integer flId;
	private Integer rmId;
	private Integer configId;
	private String cancelledReason;
	private Integer cancelledBy;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateCancelled;
	 @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Time timeCancelled;
	private String rejectionReason;
	private Integer rejectedBy;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRejected;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateEnd;
	private Integer approvedBy;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateApproved;
	private Integer parentId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateCreated;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Time timeCreated;
	private Boolean isDateOrTimeChanged;
	private List<Integer> deletedAttendeesIdList;
	private Integer checkInNotifyCount;
	private List<Integer> deletedResourcesIdList;

	public ReserveDTO() {
		super();
	}


	/**
	 * @return the loggedBy
	 */
	public Integer getLoggedBy() {
		return loggedBy;
	}

	/**
	 * @param loggedBy the loggedBy to set
	 */
	public void setLoggedBy(Integer loggedBy) {
		this.loggedBy = loggedBy;
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
	 * @return the meetingName
	 */
	public String getMeetingName() {
		return meetingName;
	}

	/**
	 * @param meetingName the meetingName to set
	 */
	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
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
	 * @return the bookingType
	 */
	public String getBookingType() {
		return bookingType;
	}

	/**
	 * @param bookingType the bookingType to set
	 */
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}

	/**
	 * @return the recurringRule
	 */
	public String getRecurringRule() {
		return recurringRule;
	}

	/**
	 * @param recurringRule the recurringRule to set
	 */
	public void setRecurringRule(String recurringRule) {
		this.recurringRule = recurringRule;
	}

	/**
	 * @return the dateStart
	 */
	public Date getDateStart() {
		return dateStart;
	}

	/**
	 * @param dateStart the dateStart to set
	 */
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	/**
	 * @return the timeStart
	 */
	public Time getTimeStart() {
		return timeStart;
	}

	/**
	 * @param timeStart the timeStart to set
	 */
	public void setTimeStart(Time timeStart) {
		this.timeStart = timeStart;
	}

	/**
	 * @return the timeEnd
	 */
	public Time getTimeEnd() {
		return timeEnd;
	}

	/**
	 * @param timeEnd the timeEnd to set
	 */
	public void setTimeEnd(Time timeEnd) {
		this.timeEnd = timeEnd;
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
	 * @return the configId
	 */
	public Integer getConfigId() {
		return configId;
	}

	/**
	 * @param configId the configId to set
	 */
	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	/**
	 * @return the cancelledReason
	 */
	public String getCancelledReason() {
		return cancelledReason;
	}

	/**
	 * @param cancelledReason the cancelledReason to set
	 */
	public void setCancelledReason(String cancelledReason) {
		this.cancelledReason = cancelledReason;
	}

	/**
	 * @return the cancelledBy
	 */
	public Integer getCancelledBy() {
		return cancelledBy;
	}

	/**
	 * @param cancelledBy the cancelledBy to set
	 */
	public void setCancelledBy(Integer cancelledBy) {
		this.cancelledBy = cancelledBy;
	}

	/**
	 * @return the dateCancelled
	 */
	public Date getDateCancelled() {
		return dateCancelled;
	}

	/**
	 * @param dateCancelled the dateCancelled to set
	 */
	public void setDateCancelled(Date dateCancelled) {
		this.dateCancelled = dateCancelled;
	}

	/**
	 * @return the timeCancelled
	 */
	public Time getTimeCancelled() {
		return timeCancelled;
	}

	/**
	 * @param timeCancelled the timeCancelled to set
	 */
	public void setTimeCancelled(Time timeCancelled) {
		this.timeCancelled = timeCancelled;
	}

	/**
	 * @return the rejectionReason
	 */
	public String getRejectionReason() {
		return rejectionReason;
	}

	/**
	 * @param rejectionReason the rejectionReason to set
	 */
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	/**
	 * @return the rejectedBy
	 */
	public Integer getRejectedBy() {
		return rejectedBy;
	}

	/**
	 * @param rejectedBy the rejectedBy to set
	 */
	public void setRejectedBy(Integer rejectedBy) {
		this.rejectedBy = rejectedBy;
	}

	/**
	 * @return the dateRejected
	 */
	public Date getDateRejected() {
		return dateRejected;
	}

	/**
	 * @param dateRejected the dateRejected to set
	 */
	public void setDateRejected(Date dateRejected) {
		this.dateRejected = dateRejected;
	}

	/**
	 * @return the dateEnd
	 */
	public Date getDateEnd() {
		return dateEnd;
	}

	/**
	 * @param dateEnd the dateEnd to set
	 */
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	/**
	 * @return the approvedBy
	 */
	public Integer getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(Integer approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the dateApproved
	 */
	public Date getDateApproved() {
		return dateApproved;
	}

	/**
	 * @param dateApproved the dateApproved to set
	 */
	public void setDateApproved(Date dateApproved) {
		this.dateApproved = dateApproved;
	}

	/**
	 * @return the parentId
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the timeCreated
	 */
	public Time getTimeCreated() {
		return timeCreated;
	}

	/**
	 * @param timeCreated the timeCreated to set
	 */
	public void setTimeCreated(Time timeCreated) {
		this.timeCreated = timeCreated;
	}

	/**
	 * @return the isDateOrTimeChanged
	 */
	public Boolean getIsDateOrTimeChanged() {
		return isDateOrTimeChanged;
	}

	/**
	 * @param isDateOrTimeChanged the isDateOrTimeChanged to set
	 */
	public void setIsDateOrTimeChanged(Boolean isDateOrTimeChanged) {
		this.isDateOrTimeChanged = isDateOrTimeChanged;
	}

	/**
	 * @return the deletedAttendeesIdList
	 */
	public List<Integer> getDeletedAttendeesIdList() {
		return deletedAttendeesIdList;
	}

	/**
	 * @param deletedAttendeesIdList the deletedAttendeesIdList to set
	 */
	public void setDeletedAttendeesIdList(List<Integer> deletedAttendeesIdList) {
		this.deletedAttendeesIdList = deletedAttendeesIdList;
	}

	/**
	 * @return the checkInNotifyCount
	 */
	public Integer getCheckInNotifyCount() {
		return checkInNotifyCount;
	}

	/**
	 * @param checkInNotifyCount the checkInNotifyCount to set
	 */
	public void setCheckInNotifyCount(Integer checkInNotifyCount) {
		this.checkInNotifyCount = checkInNotifyCount;
	}

	/**
	 * @return the deletedResourcesIdList
	 */
	public List<Integer> getDeletedResourcesIdList() {
		return deletedResourcesIdList;
	}

	/**
	 * @param deletedResourcesIdList the deletedResourcesIdList to set
	 */
	public void setDeletedResourcesIdList(List<Integer> deletedResourcesIdList) {
		this.deletedResourcesIdList = deletedResourcesIdList;
	}


	public Integer getReserveId() {
		return reserveId;
	}


	public void setReserveId(Integer reserveId) {
		this.reserveId = reserveId;
	}
	
}
