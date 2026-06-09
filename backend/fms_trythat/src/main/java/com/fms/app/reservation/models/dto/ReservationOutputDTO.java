package com.fms.app.reservation.models.dto;

import java.util.List;

import com.fms.app.reservation.models.ReserveResources;
import com.fms.app.reservation.models.RmConfig;

public class ReservationOutputDTO {
	private Integer reserveId;
	private Integer loggedBy;
	private Integer requestedBy;
	private Integer requestedFor;
	private String status;
	private String meetingName;
	private String comments;
	private String bookingType;
	private String recurringRule;
	private String dateCreated;
	private String timeCreated;
	private String dateCancelled;
	private String timeCancelled;
	private String dateStart;
	private String timeStart;
	private String timeEnd;
	private Integer blId;
	private Integer flId;
	private Integer rmId;
	private Integer cancelledBy;
	private String cancelledReason;
	private Integer configId;
	private List<RmConfig> rmConfig;
	private List<ReserveAttendeesDTO> reserveAttendees;
	private List<ReserveResources> reserveRs;
	private String rejectionReason;
	private Integer parentId;
	private String dateEnd;
    private Integer approvedBy;
    private String dateApproved;
    private Integer checkInNotifyCount;
    private byte[] rmPhoto;
    private String blDataBlCode;
    private String flDataFlCode;
    private String rmDataRmCode;
    private String requestedByUserUserName;
    private String requestedForUserUserName;
    private String approvedByUserUserName;
    private String rejectedByUserUserName;
    private String loggedByUserUserName;
	public ReservationOutputDTO() {
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
	 * @return the dateCreated
	 */
	public String getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the timeCreated
	 */
	public String getTimeCreated() {
		return timeCreated;
	}

	/**
	 * @param timeCreated the timeCreated to set
	 */
	public void setTimeCreated(String timeCreated) {
		this.timeCreated = timeCreated;
	}

	/**
	 * @return the dateCancelled
	 */
	public String getDateCancelled() {
		return dateCancelled;
	}

	/**
	 * @param dateCancelled the dateCancelled to set
	 */
	public void setDateCancelled(String dateCancelled) {
		this.dateCancelled = dateCancelled;
	}

	/**
	 * @return the timeCancelled
	 */
	public String getTimeCancelled() {
		return timeCancelled;
	}

	/**
	 * @param timeCancelled the timeCancelled to set
	 */
	public void setTimeCancelled(String timeCancelled) {
		this.timeCancelled = timeCancelled;
	}

	/**
	 * @return the dateStart
	 */
	public String getDateStart() {
		return dateStart;
	}

	/**
	 * @param dateStart the dateStart to set
	 */
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	/**
	 * @return the timeStart
	 */
	public String getTimeStart() {
		return timeStart;
	}

	/**
	 * @param timeStart the timeStart to set
	 */
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	/**
	 * @return the timeEnd
	 */
	public String getTimeEnd() {
		return timeEnd;
	}

	/**
	 * @param timeEnd the timeEnd to set
	 */
	public void setTimeEnd(String timeEnd) {
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
	 * @return the rmConfig
	 */
	public List<RmConfig> getRmConfig() {
		return rmConfig;
	}

	/**
	 * @param rmConfig the rmConfig to set
	 */
	public void setRmConfig(List<RmConfig> rmConfig) {
		this.rmConfig = rmConfig;
	}

	/**
	 * @return the reserveAttendees
	 */
	public List<ReserveAttendeesDTO> getReserveAttendees() {
		return reserveAttendees;
	}

	/**
	 * @param reserveAttendees the reserveAttendees to set
	 */
	public void setReserveAttendees(List<ReserveAttendeesDTO> reserveAttendees) {
		this.reserveAttendees = reserveAttendees;
	}

	/**
	 * @return the reserveRs
	 */
	public List<ReserveResources> getReserveRs() {
		return reserveRs;
	}

	/**
	 * @param reserveRs the reserveRs to set
	 */
	public void setReserveRs(List<ReserveResources> reserveRs) {
		this.reserveRs = reserveRs;
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
	 * @return the dateEnd
	 */
	public String getDateEnd() {
		return dateEnd;
	}

	/**
	 * @param dateEnd the dateEnd to set
	 */
	public void setDateEnd(String dateEnd) {
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
	public String getDateApproved() {
		return dateApproved;
	}

	/**
	 * @param dateApproved the dateApproved to set
	 */
	public void setDateApproved(String dateApproved) {
		this.dateApproved = dateApproved;
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
	 * @return the rmPhoto
	 */
	public byte[] getRmPhoto() {
		return rmPhoto;
	}

	/**
	 * @param rmPhoto the rmPhoto to set
	 */
	public void setRmPhoto(byte[] rmPhoto) {
		this.rmPhoto = rmPhoto;
	}

	public Integer getReserveId() {
		return reserveId;
	}

	public void setReserveId(Integer reserveId) {
		this.reserveId = reserveId;
	}

	public String getBlDataBlCode() {
		return blDataBlCode;
	}

	public void setBlDataBlCode(String blDataBlCode) {
		this.blDataBlCode = blDataBlCode;
	}

	public String getFlDataFlCode() {
		return flDataFlCode;
	}

	public void setFlDataFlCode(String flDataFlCode) {
		this.flDataFlCode = flDataFlCode;
	}

	public String getRmDataRmCode() {
		return rmDataRmCode;
	}

	public void setRmDataRmCode(String rmDataRmCode) {
		this.rmDataRmCode = rmDataRmCode;
	}

	public String getRequestedByUserUserName() {
		return requestedByUserUserName;
	}

	public void setRequestedByUserUserName(String requestedByUserUserName) {
		this.requestedByUserUserName = requestedByUserUserName;
	}

	public String getRequestedForUserUserName() {
		return requestedForUserUserName;
	}

	public void setRequestedForUserUserName(String requestedForUserUserName) {
		this.requestedForUserUserName = requestedForUserUserName;
	}

	public String getApprovedByUserUserName() {
		return approvedByUserUserName;
	}

	public void setApprovedByUserUserName(String approvedByUserUserName) {
		this.approvedByUserUserName = approvedByUserUserName;
	}

	public String getRejectedByUserUserName() {
		return rejectedByUserUserName;
	}

	public void setRejectedByUserUserName(String rejectedByUserUserName) {
		this.rejectedByUserUserName = rejectedByUserUserName;
	}

	public String getLoggedByUserUserName() {
		return loggedByUserUserName;
	}

	public void setLoggedByUserUserName(String loggedByUserUserName) {
		this.loggedByUserUserName = loggedByUserUserName;
	}
	
}
