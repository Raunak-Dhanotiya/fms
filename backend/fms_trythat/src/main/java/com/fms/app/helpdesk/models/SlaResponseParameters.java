package com.fms.app.helpdesk.models;


import java.sql.Time;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "sla_response_parameters")

public class SlaResponseParameters {

	@Id
	@Column(name = "sla_response_parameters_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer slaResponseParametersId;

	@Column(name = "sla_request_parameters_id")
	private Integer slaRequestParametersId;


	@Column(name = "working_days")
	private String workingDays;

	@Column(name = "can_work_on_holiday")
	private Integer canWorkOnHoliday;

	@Column(name = "day_start_time")
	@JsonFormat(pattern = "HH:mm:ss")
	private Time dayStartTime;

	@Column(name = "day_end_time")
	@JsonFormat(pattern = "HH:mm:ss")
	private Time dayEndTime;

	@Column(name = "supervisor")
	private Integer supervisor;

	@Column(name = "priority")
	private String priority;
	
	@Column(name = "is_default")
	private String isDefault;
	
	@Column(name = "auto_issue")
	private String autoIssue;
	
	@Column(name = "auto_approval")
	private String autoApproval;
	
	@Column(name = "notify_requestor")
	private String notifyRequestor;
	
	@Column(name="time_to_response_days")
	private Integer timeToResponseDays;
	
	@Column(name="time_to_response_hours")
	private double timeToResponseHours;
	
	@Column(name="time_to_complete_days")
	private Integer timeToCompleteDays;
	
	@Column(name="time_to_complete_hours")
	private double timeToCompleteHours;
	
	@Column(name="technician_id")
	private Integer technicianId;
	
	@Column(name="team_id")
	private Integer teamId;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "sla_response_parameters_id", referencedColumnName = "sla_response_parameters_id", insertable = false, updatable = false)
	private Set<SlaRequestSteps> slaSteps;
	
	public SlaResponseParameters() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param slaResponseParametersId
	 * @param slaRequestParametersId
	 * @param workingDays
	 * @param canWorkOnHoliday
	 * @param dayStartTime
	 * @param dayEndTime
	 * @param supervisor
	 * @param priority
	 * @param isDefault
	 * @param autoIssue
	 * @param autoApproval
	 * @param notifyRequestor
	 * @param timeToResponseDays
	 * @param timeToResponseHours
	 * @param timeToCompleteDays
	 * @param timeToCompleteHours
	 * @param technicianId
	 * @param teamId
	 * @param slaSteps
	 */
	public SlaResponseParameters(Integer slaResponseParametersId, Integer slaRequestParametersId, String workingDays,
			Integer canWorkOnHoliday, Time dayStartTime, Time dayEndTime, Integer supervisor, String priority, String isDefault,
			String autoIssue, String autoApproval, String notifyRequestor, Integer timeToResponseDays,
			double timeToResponseHours, Integer timeToCompleteDays, double timeToCompleteHours, Integer technicianId,
			Integer teamId, Set<SlaRequestSteps> slaSteps) {
		super();
		this.slaResponseParametersId = slaResponseParametersId;
		this.slaRequestParametersId = slaRequestParametersId;
		this.workingDays = workingDays;
		this.canWorkOnHoliday = canWorkOnHoliday;
		this.dayStartTime = dayStartTime;
		this.dayEndTime = dayEndTime;
		this.supervisor = supervisor;
		this.priority = priority;
		this.isDefault = isDefault;
		this.autoIssue = autoIssue;
		this.autoApproval = autoApproval;
		this.notifyRequestor = notifyRequestor;
		this.timeToResponseDays = timeToResponseDays;
		this.timeToResponseHours = timeToResponseHours;
		this.timeToCompleteDays = timeToCompleteDays;
		this.timeToCompleteHours = timeToCompleteHours;
		this.technicianId = technicianId;
		this.teamId = teamId;
		this.slaSteps = slaSteps;
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
	 * @return the workingDays
	 */
	public String getWorkingDays() {
		return workingDays;
	}

	/**
	 * @param workingDays the workingDays to set
	 */
	public void setWorkingDays(String workingDays) {
		this.workingDays = workingDays;
	}

	/**
	 * @return the canWorkOnHoliday
	 */
	public Integer getCanWorkOnHoliday() {
		return canWorkOnHoliday;
	}

	/**
	 * @param canWorkOnHoliday the canWorkOnHoliday to set
	 */
	public void setCanWorkOnHoliday(Integer canWorkOnHoliday) {
		this.canWorkOnHoliday = canWorkOnHoliday;
	}

	/**
	 * @return the dayStartTime
	 */
	public Time getDayStartTime() {
		return dayStartTime;
	}

	/**
	 * @param dayStartTime the dayStartTime to set
	 */
	public void setDayStartTime(Time dayStartTime) {
		this.dayStartTime = dayStartTime;
	}

	/**
	 * @return the dayEndTime
	 */
	public Time getDayEndTime() {
		return dayEndTime;
	}

	/**
	 * @param dayEndTime the dayEndTime to set
	 */
	public void setDayEndTime(Time dayEndTime) {
		this.dayEndTime = dayEndTime;
	}

	/**
	 * @return the supervisor
	 */
	public Integer getSupervisor() {
		return supervisor;
	}

	/**
	 * @param supervisor the supervisor to set
	 */
	public void setSupervisor(Integer supervisor) {
		this.supervisor = supervisor;
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
	 * @return the isDefault
	 */
	public String getIsDefault() {
		return isDefault;
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * @return the autoIssue
	 */
	public String getAutoIssue() {
		return autoIssue;
	}

	/**
	 * @param autoIssue the autoIssue to set
	 */
	public void setAutoIssue(String autoIssue) {
		this.autoIssue = autoIssue;
	}

	/**
	 * @return the autoApproval
	 */
	public String getAutoApproval() {
		return autoApproval;
	}

	/**
	 * @param autoApproval the autoApproval to set
	 */
	public void setAutoApproval(String autoApproval) {
		this.autoApproval = autoApproval;
	}

	/**
	 * @return the notifyRequestor
	 */
	public String getNotifyRequestor() {
		return notifyRequestor;
	}

	/**
	 * @param notifyRequestor the notifyRequestor to set
	 */
	public void setNotifyRequestor(String notifyRequestor) {
		this.notifyRequestor = notifyRequestor;
	}

	/**
	 * @return the timeToResponseDays
	 */
	public Integer getTimeToResponseDays() {
		return timeToResponseDays;
	}

	/**
	 * @param timeToResponseDays the timeToResponseDays to set
	 */
	public void setTimeToResponseDays(Integer timeToResponseDays) {
		this.timeToResponseDays = timeToResponseDays;
	}

	/**
	 * @return the timeToResponseHours
	 */
	public double getTimeToResponseHours() {
		return timeToResponseHours;
	}

	/**
	 * @param timeToResponseHours the timeToResponseHours to set
	 */
	public void setTimeToResponseHours(double timeToResponseHours) {
		this.timeToResponseHours = timeToResponseHours;
	}

	/**
	 * @return the timeToCompleteDays
	 */
	public Integer getTimeToCompleteDays() {
		return timeToCompleteDays;
	}

	/**
	 * @param timeToCompleteDays the timeToCompleteDays to set
	 */
	public void setTimeToCompleteDays(Integer timeToCompleteDays) {
		this.timeToCompleteDays = timeToCompleteDays;
	}

	/**
	 * @return the timeToCompleteHours
	 */
	public double getTimeToCompleteHours() {
		return timeToCompleteHours;
	}

	/**
	 * @param timeToCompleteHours the timeToCompleteHours to set
	 */
	public void setTimeToCompleteHours(double timeToCompleteHours) {
		this.timeToCompleteHours = timeToCompleteHours;
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
	 * @return the slaSteps
	 */
	public Set<SlaRequestSteps> getSlaSteps() {
		return slaSteps;
	}

	/**
	 * @param slaSteps the slaSteps to set
	 */
	public void setSlaSteps(Set<SlaRequestSteps> slaSteps) {
		this.slaSteps = slaSteps;
	}

}
