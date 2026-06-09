package com.fms.app.reservation.models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.Fl;
import com.fms.app.spaceManagement.models.Rm;
import com.fms.app.userModels.User;

@Entity
@Table(name = "reserve")
public class Reservation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8863852597533041658L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reserve_id")
	private Integer reserveId;

	@Column(name = "logged_by")
	private Integer loggedBy;

	@Column(name = "requested_by")
	private Integer requestedBy;

	@Column(name = "requested_for")
	private Integer requestedFor;

	@Column(name = "status")
	private String status;

	@Column(name = "name")
	private String meetingName;

	@Column(name = "description")
	private String comments;

	@Column(name = "type")
	private String bookingType;

	@Column(name = "recurring_rule")
	private String recurringRule;

	@Column(name = "date_created")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date dateCreated;

	@Column(name = "time_created")
	@JsonFormat(pattern="HH:mm:ss")
	private Time timeCreated;

	@Column(name = "date_cancelled")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date dateCancelled;

	@Column(name = "time_cancelled")
	@JsonFormat(pattern="HH:mm:ss")
	private Time timeCancelled;

	@Column(name = "date_start")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date dateStart;

	@Column(name = "time_start")
	@JsonFormat(pattern="HH:mm:ss")
	private Time timeStart;

	@Column(name = "time_end")
	@JsonFormat(pattern="HH:mm:ss")
	private Time timeEnd;

	@Column(name = "bl_id")
	private Integer blId;

	@Column(name = "fl_id")
	private Integer flId;

	@Column(name = "rm_id")
	private Integer rmId;

	@Column(name = "cancelled_by")
	private Integer cancelledBy;

	@Column(name = "cancelled_reason")
	private String cancelledReason;

	@Column(name = "parent_id")
	private Integer parentId;

	@Column(name = "config_id")
	private Integer configId;

	@Column(name = "rejection_reason")
	private String rejectionReason;
	
	@Column(name = "rejected_by")
    private Integer rejectedBy;

    @Column(name = "rejection_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateRejected;
    
    @Column(name = "date_end")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateEnd;
    
    @Column(name = "approved_by")
    private Integer approvedBy;
    
    @Column(name = "approved_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateApproved;
    
    @Column(name = "check_in_notify_count")
	private Integer checkInNotifyCount;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "config_id", referencedColumnName = "config_id", insertable = false, updatable = false)
	private List<RmConfig> rmConfig;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "reserve_id", referencedColumnName = "reserve_id", insertable = false, updatable = false)
	private List<ReserveAttendees> reserveAttendees;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "reserve_id", referencedColumnName = "reserve_id", insertable = false, updatable = false)
	private List<ReserveResources> reserveRs;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "requested_by", referencedColumnName = "user_id", insertable = false, updatable = false)
	private User requestedByUser;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "requested_for", referencedColumnName = "user_id", insertable = false, updatable = false)
	private User requestedForUser;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approved_by", referencedColumnName = "user_id", insertable = false, updatable = false)
	private User approvedByUser;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rejected_by", referencedColumnName = "user_id", insertable = false, updatable = false)
	private User rejectedByUser;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "logged_by", referencedColumnName = "user_id", insertable = false, updatable = false)
	private User loggedByUser;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cancelled_by", referencedColumnName = "user_id", insertable = false, updatable = false)
	private User cancelledByUser;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bl_id", referencedColumnName = "bl_id", insertable = false, updatable = false)
	private Bl blData;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fl_id", referencedColumnName = "fl_id", insertable = false, updatable = false)
	private Fl flData;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rm_id", referencedColumnName = "rm_id", insertable = false, updatable = false)
	private Rm rmData;
	
	public Reservation() {
		super();
	}

	/**
	 * @param reserveId
	 * @param loggedBy
	 * @param requestedBy
	 * @param requestedFor
	 * @param status
	 * @param meetingName
	 * @param comments
	 * @param bookingType
	 * @param recurringRule
	 * @param dateCreated
	 * @param timeCreated
	 * @param dateCancelled
	 * @param timeCancelled
	 * @param dateStart
	 * @param timeStart
	 * @param timeEnd
	 * @param blId
	 * @param flId
	 * @param rmId
	 * @param cancelledBy
	 * @param cancelledReason
	 * @param parentId
	 * @param configId
	 * @param rejectionReason
	 * @param rejectedBy
	 * @param dateRejected
	 * @param dateEnd
	 * @param approvedBy
	 * @param dateApproved
	 * @param checkInNotifyCount
	 * @param rmConfig
	 * @param reserveAttendees
	 * @param reserveRs
	 */
	public Reservation(Integer reserveId, Integer loggedBy, Integer requestedBy, Integer requestedFor, String status,
			String meetingName, String comments, String bookingType, String recurringRule, Date dateCreated,
			Time timeCreated, Date dateCancelled, Time timeCancelled, Date dateStart, Time timeStart, Time timeEnd,
			Integer blId, Integer flId, Integer rmId, Integer cancelledBy, String cancelledReason, Integer parentId, Integer configId,
			String rejectionReason, Integer rejectedBy, Date dateRejected, Date dateEnd, Integer approvedBy, Date dateApproved,
			Integer checkInNotifyCount, List<RmConfig> rmConfig, List<ReserveAttendees> reserveAttendees,
			List<ReserveResources> reserveRs) {
		super();
		this.reserveId = reserveId;
		this.loggedBy = loggedBy;
		this.requestedBy = requestedBy;
		this.requestedFor = requestedFor;
		this.status = status;
		this.meetingName = meetingName;
		this.comments = comments;
		this.bookingType = bookingType;
		this.recurringRule = recurringRule;
		this.dateCreated = dateCreated;
		this.timeCreated = timeCreated;
		this.dateCancelled = dateCancelled;
		this.timeCancelled = timeCancelled;
		this.dateStart = dateStart;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
		this.cancelledBy = cancelledBy;
		this.cancelledReason = cancelledReason;
		this.parentId = parentId;
		this.configId = configId;
		this.rejectionReason = rejectionReason;
		this.rejectedBy = rejectedBy;
		this.dateRejected = dateRejected;
		this.dateEnd = dateEnd;
		this.approvedBy = approvedBy;
		this.dateApproved = dateApproved;
		this.checkInNotifyCount = checkInNotifyCount;
		this.rmConfig = rmConfig;
		this.reserveAttendees = reserveAttendees;
		this.reserveRs = reserveRs;
	}

	/**
	 * @return the reserveId
	 */
	public Integer getReserveId() {
		return reserveId;
	}

	/**
	 * @param reserveId the reserveId to set
	 */
	public void setReserveId(Integer reserveId) {
		this.reserveId = reserveId;
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
	public List<ReserveAttendees> getReserveAttendees() {
		return reserveAttendees;
	}

	/**
	 * @param reserveAttendees the reserveAttendees to set
	 */
	public void setReserveAttendees(List<ReserveAttendees> reserveAttendees) {
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
	 * @return the blData
	 */
	public Bl getBlData() {
		return blData;
	}

	/**
	 * @param blData the blData to set
	 */
	public void setBlData(Bl blData) {
		this.blData = blData;
	}

	/**
	 * @return the flData
	 */
	public Fl getFlData() {
		return flData;
	}

	/**
	 * @param flData the flData to set
	 */
	public void setFlData(Fl flData) {
		this.flData = flData;
	}

	/**
	 * @return the rmData
	 */
	public Rm getRmData() {
		return rmData;
	}

	/**
	 * @param rmData the rmData to set
	 */
	public void setRmData(Rm rmData) {
		this.rmData = rmData;
	}

	public User getRequestedByUser() {
		return requestedByUser;
	}

	public void setRequestedByUser(User requestedByUser) {
		this.requestedByUser = requestedByUser;
	}

	public User getRequestedForUser() {
		return requestedForUser;
	}

	public void setRequestedForUser(User requestedForUser) {
		this.requestedForUser = requestedForUser;
	}

	public User getApprovedByUser() {
		return approvedByUser;
	}

	public void setApprovedByUser(User approvedByUser) {
		this.approvedByUser = approvedByUser;
	}

	public User getRejectedByUser() {
		return rejectedByUser;
	}

	public void setRejectedByUser(User rejectedByUser) {
		this.rejectedByUser = rejectedByUser;
	}

	public User getLoggedByUser() {
		return loggedByUser;
	}

	public void setLoggedByUser(User loggedByUser) {
		this.loggedByUser = loggedByUser;
	}

	public User getCancelledByUser() {
		return cancelledByUser;
	}

	public void setCancelledByUser(User cancelledByUser) {
		this.cancelledByUser = cancelledByUser;
	}
	
}
