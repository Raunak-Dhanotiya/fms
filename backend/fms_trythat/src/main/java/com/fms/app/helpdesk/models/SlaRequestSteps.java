package com.fms.app.helpdesk.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.app.employee.models.Employee;
@Entity(name = "sla_request_steps")
@Table(name = "sla_request_steps")
public class SlaRequestSteps {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sla_request_steps_id")
	private Integer slaRequestStepsId;
	
	@Column(name = "sla_response_parameters_id")
	private Integer slaResponseParametersId;

	@Column(name = "step_id")
	private Integer stepId;
	
	@Column(name = "em_id")
	private Integer emId;
	
	@Column(name = "team_id")
	private Integer teamId;
	
	@Column(name = "cf_id")
	private Integer cfId;
	
	@Column(name = "next_step")
	private Integer nextStep;
	
	@Column(name = "previous_step")
	private Integer previousStep;
	
	@Column(name = "other_emails")
	private String otherEmails;
	
	@Column(name = "notify_responsable")
	private String notifyResponsable;
	
	@Column(name = "notify_requestor")
	private String notifyRequestor;
	
	@Column(name = "notify_supervisor")
	private String notifySupervisor;
	
	@Column(name = "notify_others")
	private String notifyOthers;
	
	@Column(name = "wr_status")
	private String wrStatus;
	
	@Column(name = "step_type")
	private String stepType;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cf_id", referencedColumnName = "cf_id", insertable = false, updatable = false)
	private CraftsPerson technicianData;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "em_id", referencedColumnName = "em_id", insertable = false, updatable = false)
	private Employee em;

	public SlaRequestSteps() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param slaRequestStepsId
	 * @param slaResponseParametersId
	 * @param stepId
	 * @param emId
	 * @param teamId
	 * @param cfId
	 * @param nextStep
	 * @param previousStep
	 * @param otherEmails
	 * @param notifyResponsable
	 * @param notifyRequestor
	 * @param notifySupervisor
	 * @param notifyOthers
	 * @param wrStatus
	 * @param stepType
	 * @param technicianData
	 */
	public SlaRequestSteps(Integer slaRequestStepsId, Integer slaResponseParametersId, Integer stepId, Integer emId, Integer teamId,
			Integer cfId, Integer nextStep, Integer previousStep, String otherEmails, String notifyResponsable,
			String notifyRequestor, String notifySupervisor, String notifyOthers, String wrStatus, String stepType,
			CraftsPerson technicianData) {
		super();
		this.slaRequestStepsId = slaRequestStepsId;
		this.slaResponseParametersId = slaResponseParametersId;
		this.stepId = stepId;
		this.emId = emId;
		this.teamId = teamId;
		this.cfId = cfId;
		this.nextStep = nextStep;
		this.previousStep = previousStep;
		this.otherEmails = otherEmails;
		this.notifyResponsable = notifyResponsable;
		this.notifyRequestor = notifyRequestor;
		this.notifySupervisor = notifySupervisor;
		this.notifyOthers = notifyOthers;
		this.wrStatus = wrStatus;
		this.stepType = stepType;
		this.technicianData = technicianData;
	}

	/**
	 * @return the slaRequestStepsId
	 */
	public Integer getSlaRequestStepsId() {
		return slaRequestStepsId;
	}

	/**
	 * @param slaRequestStepsId the slaRequestStepsId to set
	 */
	public void setSlaRequestStepsId(Integer slaRequestStepsId) {
		this.slaRequestStepsId = slaRequestStepsId;
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
	 * @return the stepId
	 */
	public Integer getStepId() {
		return stepId;
	}

	/**
	 * @param stepId the stepId to set
	 */
	public void setStepId(Integer stepId) {
		this.stepId = stepId;
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
	 * @return the cfId
	 */
	public Integer getCfId() {
		return cfId;
	}

	/**
	 * @param cfId the cfId to set
	 */
	public void setCfId(Integer cfId) {
		this.cfId = cfId;
	}

	/**
	 * @return the nextStep
	 */
	public Integer getNextStep() {
		return nextStep;
	}

	/**
	 * @param nextStep the nextStep to set
	 */
	public void setNextStep(Integer nextStep) {
		this.nextStep = nextStep;
	}

	/**
	 * @return the previousStep
	 */
	public Integer getPreviousStep() {
		return previousStep;
	}

	/**
	 * @param previousStep the previousStep to set
	 */
	public void setPreviousStep(Integer previousStep) {
		this.previousStep = previousStep;
	}

	/**
	 * @return the otherEmails
	 */
	public String getOtherEmails() {
		return otherEmails;
	}

	/**
	 * @param otherEmails the otherEmails to set
	 */
	public void setOtherEmails(String otherEmails) {
		this.otherEmails = otherEmails;
	}

	/**
	 * @return the notifyResponsable
	 */
	public String getNotifyResponsable() {
		return notifyResponsable;
	}

	/**
	 * @param notifyResponsable the notifyResponsable to set
	 */
	public void setNotifyResponsable(String notifyResponsable) {
		this.notifyResponsable = notifyResponsable;
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
	 * @return the notifySupervisor
	 */
	public String getNotifySupervisor() {
		return notifySupervisor;
	}

	/**
	 * @param notifySupervisor the notifySupervisor to set
	 */
	public void setNotifySupervisor(String notifySupervisor) {
		this.notifySupervisor = notifySupervisor;
	}

	/**
	 * @return the notifyOthers
	 */
	public String getNotifyOthers() {
		return notifyOthers;
	}

	/**
	 * @param notifyOthers the notifyOthers to set
	 */
	public void setNotifyOthers(String notifyOthers) {
		this.notifyOthers = notifyOthers;
	}

	/**
	 * @return the wrStatus
	 */
	public String getWrStatus() {
		return wrStatus;
	}

	/**
	 * @param wrStatus the wrStatus to set
	 */
	public void setWrStatus(String wrStatus) {
		this.wrStatus = wrStatus;
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
	 * @return the technicianData
	 */
	public CraftsPerson getTechnicianData() {
		return technicianData;
	}

	/**
	 * @param technicianData the technicianData to set
	 */
	public void setTechnicianData(CraftsPerson technicianData) {
		this.technicianData = technicianData;
	}

	public Employee getEm() {
		return em;
	}

	public void setEm(Employee em) {
		this.em = em;
	}
	
}
