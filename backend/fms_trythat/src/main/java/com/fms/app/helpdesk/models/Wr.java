package com.fms.app.helpdesk.models;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.app.Equipment.models.Equipment;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.Fl;
import com.fms.app.spaceManagement.models.Rm;

@Entity(name = "wr")
@Table(name = "wr")

public class Wr {

	@Id
	@Column(name = "wr_id" ,nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer wrId;
	
	@Column(name = "parent_wr_id")
	private Integer parentWrId;

	@Column(name = "status")
	private String status;

	@Column(name = "site_id")
	private Integer siteId;

	@Column(name = "bl_id")
	private Integer blId;

	@Column(name = "fl_id")
	private Integer flId;
	
	@Column(name = "rm_id")
	private Integer rmId;

	@Column(name = "eq_id")
	private Integer eqId;
	
	@Column(name = "prob_Type_id",nullable = true)
	private Integer probTypeId;

	@Column(name = "description")
	private String description;
	
	@Column(name = "date_requested")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateRequested;

	@Column(name = "time_Requested")
	@JsonFormat(pattern="HH:mm:ss")
	private Time timeRequested;

	@Column(name = "date_completed")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateCompleted;

	@Column(name = "time_completed")
	@JsonFormat(pattern="HH:mm:ss")
	private Time timeCompleted;
	
	@Column(name = "date_responded")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateResponded;
	
	@Column(name = "time_responded")
	@JsonFormat(pattern="HH:mm:ss")
	private Time timeResponded;
	
	@Column(name = "esc_date_responded")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date escDateResponded;
	
	@Column(name = "esc_time_responded")
	@JsonFormat(pattern="HH:mm:ss")
	private Time escTimeResponded;

	@Column(name = "esc_date_completed")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date escDateCompleted;
	
	@Column(name = "esc_time_completed")
	@JsonFormat(pattern="HH:mm:ss")
	private Time escTimeCompleted;

	@Column(name = "sla_request_parameters_id")
	private Integer slaRequestParametersId;
	
	@Column(name = "requested_by")
	private Integer requestedBy;

	@Column(name = "requested_for")
	private Integer requestedFor;
	
	@Column(name = "date_to_perform")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateToPerform;
	
	@Column(name = "is_requestor")
	private String isRequestor;
	
	@Column(name = "is_approver")
	private String isApprover;
	
	@Column(name = "is_technician")
	private String isTechnician;
	
	@Column(name = "is_supervisor")
	private String isSupervisor;
	
	@Column(name = "sla_response_parameters_id")
	private Integer slaResponseParametersId;
	
	@Column(name = "complete_by")
	private Integer completeBy;
	
	@Column(name = "doc_bucket_id")
	private Integer docBucketId;
	
	@Column(name = "sched_id",nullable = true)
	private Integer scheduleId;
	
	@Column(name = "plan_id",nullable = true)
	private Integer planId;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "eq_id", referencedColumnName = "eq_id", insertable = false, updatable = false)
	private Equipment eq;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bl_id", referencedColumnName = "bl_id", insertable = false, updatable = false)
	private Bl bl;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fl_id", referencedColumnName = "fl_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Fl fl;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rm_id", referencedColumnName = "rm_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Rm rm;
	
	
	/**
	 * @param wrId
	 * @param parentWrId
	 * @param status
	 * @param siteId
	 * @param blId
	 * @param flId
	 * @param rmId
	 * @param eqId
	 * @param probTypeId
	 * @param description
	 * @param dateRequested
	 * @param timeRequested
	 * @param dateCompleted
	 * @param timeCompleted
	 * @param dateResponded
	 * @param timeResponded
	 * @param escDateResponded
	 * @param escTimeResponded
	 * @param escDateCompleted
	 * @param escTimeCompleted
	 * @param slaRequestParametersId
	 * @param requestedBy
	 * @param requestedFor
	 * @param dateToPerform
	 * @param isRequestor
	 * @param isApprover
	 * @param isTechnician
	 * @param isSupervisor
	 * @param slaResponseParametersId
	 * @param completeBy
	 * @param docBucketId
	 * @param scheduleId
	 * @param planId
	 * @param eq
	 */
	public Wr(Integer wrId, Integer parentWrId, String status, Integer siteId, Integer blId, Integer flId, Integer rmId, Integer eqId,
			Integer probTypeId, String description, Date dateRequested, Time timeRequested, Date dateCompleted,
			Time timeCompleted, Date dateResponded, Time timeResponded, Date escDateResponded, Time escTimeResponded,
			Date escDateCompleted, Time escTimeCompleted, Integer slaRequestParametersId, Integer requestedBy, Integer requestedFor,
			Date dateToPerform, String isRequestor, String isApprover, String isTechnician, String isSupervisor,
			Integer slaResponseParametersId, Integer completeBy, Integer docBucketId, Integer scheduleId, Integer planId, Equipment eq) {
		super();
		this.wrId = wrId;
		this.parentWrId = parentWrId;
		this.status = status;
		this.siteId = siteId;
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
		this.eqId = eqId;
		this.probTypeId = probTypeId;
		this.description = description;
		this.dateRequested = dateRequested;
		this.timeRequested = timeRequested;
		this.dateCompleted = dateCompleted;
		this.timeCompleted = timeCompleted;
		this.dateResponded = dateResponded;
		this.timeResponded = timeResponded;
		this.escDateResponded = escDateResponded;
		this.escTimeResponded = escTimeResponded;
		this.escDateCompleted = escDateCompleted;
		this.escTimeCompleted = escTimeCompleted;
		this.slaRequestParametersId = slaRequestParametersId;
		this.requestedBy = requestedBy;
		this.requestedFor = requestedFor;
		this.dateToPerform = dateToPerform;
		this.isRequestor = isRequestor;
		this.isApprover = isApprover;
		this.isTechnician = isTechnician;
		this.isSupervisor = isSupervisor;
		this.slaResponseParametersId = slaResponseParametersId;
		this.completeBy = completeBy;
		this.docBucketId = docBucketId;
		this.scheduleId = scheduleId;
		this.planId = planId;
		this.eq = eq;
	}

	public Wr() {
		super();
		// TODO Auto-generated constructor stub
	}

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
	 * @return the timeRequested
	 */
	public Time getTimeRequested() {
		return timeRequested;
	}

	/**
	 * @param timeRequested the timeRequested to set
	 */
	public void setTimeRequested(Time timeRequested) {
		this.timeRequested = timeRequested;
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
	 * @return the timeCompleted
	 */
	public Time getTimeCompleted() {
		return timeCompleted;
	}

	/**
	 * @param timeCompleted the timeCompleted to set
	 */
	public void setTimeCompleted(Time timeCompleted) {
		this.timeCompleted = timeCompleted;
	}

	/**
	 * @return the dateResponded
	 */
	public Date getDateResponded() {
		return dateResponded;
	}

	/**
	 * @param dateResponded the dateResponded to set
	 */
	public void setDateResponded(Date dateResponded) {
		this.dateResponded = dateResponded;
	}

	/**
	 * @return the timeResponded
	 */
	public Time getTimeResponded() {
		return timeResponded;
	}

	/**
	 * @param timeResponded the timeResponded to set
	 */
	public void setTimeResponded(Time timeResponded) {
		this.timeResponded = timeResponded;
	}

	/**
	 * @return the escDateResponded
	 */
	public Date getEscDateResponded() {
		return escDateResponded;
	}

	/**
	 * @param escDateResponded the escDateResponded to set
	 */
	public void setEscDateResponded(Date escDateResponded) {
		this.escDateResponded = escDateResponded;
	}

	/**
	 * @return the escTimeResponded
	 */
	public Time getEscTimeResponded() {
		return escTimeResponded;
	}

	/**
	 * @param escTimeResponded the escTimeResponded to set
	 */
	public void setEscTimeResponded(Time escTimeResponded) {
		this.escTimeResponded = escTimeResponded;
	}

	/**
	 * @return the escDateCompleted
	 */
	public Date getEscDateCompleted() {
		return escDateCompleted;
	}

	/**
	 * @param escDateCompleted the escDateCompleted to set
	 */
	public void setEscDateCompleted(Date escDateCompleted) {
		this.escDateCompleted = escDateCompleted;
	}

	/**
	 * @return the escTimeCompleted
	 */
	public Time getEscTimeCompleted() {
		return escTimeCompleted;
	}

	/**
	 * @param escTimeCompleted the escTimeCompleted to set
	 */
	public void setEscTimeCompleted(Time escTimeCompleted) {
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
	public Date getDateToPerform() {
		return dateToPerform;
	}

	/**
	 * @param dateToPerform the dateToPerform to set
	 */
	public void setDateToPerform(Date dateToPerform) {
		this.dateToPerform = dateToPerform;
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

	/**
	 * @return the eq
	 */
	public Equipment getEq() {
		return eq;
	}

	/**
	 * @param eq the eq to set
	 */
	public void setEq(Equipment eq) {
		this.eq = eq;
	}

	public Bl getBl() {
		return bl;
	}

	public void setBl(Bl bl) {
		this.bl = bl;
	}

	public Fl getFl() {
		return fl;
	}

	public void setFl(Fl fl) {
		this.fl = fl;
	}

	public Rm getRm() {
		return rm;
	}

	public void setRm(Rm rm) {
		this.rm = rm;
	}
	
}
