package com.fms.app.helpdesk.models;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "request_steps_log")
@Table(name = "request_steps_log")
public class RequestStepsLog {

	@Id
	@Column(name = "request_step_log_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer requestStepLogId;

	@Column(name = "request_id")
	private Integer requestId;

	@Column(name = "step_status")
	private String stepStatus;

	@Column(name = "time_created")
	@JsonFormat(pattern = "HH:mm:ss")
	private Time timeCreated;

	@Column(name = "date_created")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateCreated;

	@Column(name = "time_completed")
	@JsonFormat(pattern = "HH:mm:ss")
	private Time timeCompleted;

	@Column(name = "date_completed")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateCompleted;

	@Column(name = "em_id")
	private Integer emId;

	@Column(name = "technician_id")
	private Integer technicianId;

	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "step_type")
	private String stepType;
	
	@Column(name = "team_id")
	private Integer teamId;
	
	@Column(name = "responsible")
	private String responsible;
	
	@Column(name = "req_status")
	private String requestStatus;

	/**
	 * 
	 */
	public RequestStepsLog() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * @param requestStepLogId
	 * @param requestId
	 * @param stepStatus
	 * @param timeCreated
	 * @param dateCreated
	 * @param timeCompleted
	 * @param dateCompleted
	 * @param emId
	 * @param technicianId
	 * @param userId
	 * @param stepType
	 * @param teamId
	 * @param responsible
	 * @param requestStatus
	 */
	public RequestStepsLog(Integer requestStepLogId, Integer requestId, String stepStatus, Time timeCreated, Date dateCreated,
			Time timeCompleted, Date dateCompleted, Integer emId, Integer technicianId, Integer userId, String stepType, Integer teamId,
			String responsible, String requestStatus) {
		super();
		this.requestStepLogId = requestStepLogId;
		this.requestId = requestId;
		this.stepStatus = stepStatus;
		this.timeCreated = timeCreated;
		this.dateCreated = dateCreated;
		this.timeCompleted = timeCompleted;
		this.dateCompleted = dateCompleted;
		this.emId = emId;
		this.technicianId = technicianId;
		this.userId = userId;
		this.stepType = stepType;
		this.teamId = teamId;
		this.responsible = responsible;
		this.requestStatus = requestStatus;
	}

	/**
	 * @return the requestStepLogId
	 */
	public Integer getRequestStepLogId() {
		return requestStepLogId;
	}

	/**
	 * @param requestStepLogId the requestStepLogId to set
	 */
	public void setRequestStepLogId(Integer requestStepLogId) {
		this.requestStepLogId = requestStepLogId;
	}

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
	 * @return the stepStatus
	 */
	public String getStepStatus() {
		return stepStatus;
	}

	/**
	 * @param stepStatus the stepStatus to set
	 */
	public void setStepStatus(String stepStatus) {
		this.stepStatus = stepStatus;
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
	 * @return the emId
	 */
	public Integer getEmId() {
		return emId;
	}

	/**
	 * @param emId the emId to set
	 */
	public void setEmId(Integer emId) {
		this.emId = emId;
	}

	/**
	 * @return the technicianId
	 */
	public Integer getTechnicianId() {
		return technicianId;
	}

	/**
	 * @param technicianId the technicianId to set
	 */
	public void setTechnicianId(Integer technicianId) {
		this.technicianId = technicianId;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the stepType
	 */
	public String getStepType() {
		return stepType;
	}

	/**
	 * @param stepType the stepType to set
	 */
	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	/**
	 * @return the teamId
	 */
	public Integer getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	/**
	 * @return the responsible
	 */
	public String getResponsible() {
		return responsible;
	}

	/**
	 * @param responsible the responsible to set
	 */
	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	/**
	 * @return the requestStatus
	 */
	public String getRequestStatus() {
		return requestStatus;
	}

	/**
	 * @param requestStatus the requestStatus to set
	 */
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	
}
