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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.app.employee.models.Employee;

@Entity(name = "request_technician_log")
@Table(name = "request_technician_log")

public class RequestTechnicianLog {

	@Id
	@Column(name = "request_technician_log_id",nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer requestTechnicianLogId;
	
	@Column(name = "actual_hours_std")
	private double actualHoursStd;

	@Column(name = "actual_hours_double")
	private double actualHoursDouble;

	@Column(name = "actual_hours_overtime")
	private double actualHoursOvertime;

	@Column(name = "work_type")
	private String workType;

	@Column(name = "technician_id")
	private Integer technicianId;
	
	@Column(name = "request_id")
	private Integer requestId;
	
	@Column(name = "resource_type")
	private String resourceType;
	
	@Column(name = "comment")
	private String comment;
	
	@Column(name = "date_started")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateStarted;

	@Column(name = "date_finished")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateFinished;

	@Column(name = "time_started")
	@JsonFormat(pattern="HH:mm:ss")
	private Time timeStarted;

	@Column(name = "time_finished")
	@JsonFormat(pattern="HH:mm:ss")
	private Time timeFinished;
	
	@Column(name = "em_id")
	private Integer emId;
	
	@Column(name = "other")
	private String other;
	
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "technician_id", referencedColumnName = "cf_id", insertable = false, updatable = false)
	private CraftsPerson technician;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "em_id", referencedColumnName = "em_id", insertable = false, updatable = false)
	private Employee em;
	
	public CraftsPerson getTechnician() {
		return technician;
	}

	public void setTechnician(CraftsPerson technician) {
		this.technician = technician;
	}
		public Integer getRequestTechnicianLogId() {
		return requestTechnicianLogId;
	}

	public void setRequestTechnicianLogId(Integer requestTechnicianLogId) {
		this.requestTechnicianLogId = requestTechnicianLogId;
	}

	public double getActualHoursStd() {
		return actualHoursStd;
	}

	public void setActualHoursStd(double actualHoursStd) {
		this.actualHoursStd = actualHoursStd;
	}

	public double getActualHoursDouble() {
		return actualHoursDouble;
	}

	public void setActualHoursDouble(double actualHoursDouble) {
		this.actualHoursDouble = actualHoursDouble;
	}

	public double getActualHoursOvertime() {
		return actualHoursOvertime;
	}

	public void setActualHoursOvertime(double actualHoursOvertime) {
		this.actualHoursOvertime = actualHoursOvertime;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public Integer getTechnicianId() {
		return technicianId;
	}

	public void setTechnicianId(Integer technicianId) {
		this.technicianId = technicianId;
	}

	public Integer getRequestId() {
		return requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public Date getDateStarted() {
		return dateStarted;
	}

	public void setDateStarted(Date dateStarted) {
		this.dateStarted = dateStarted;
	}

	public Date getDateFinished() {
		return dateFinished;
	}

	public void setDateFinished(Date dateFinished) {
		this.dateFinished = dateFinished;
	}

	public Time getTimeStarted() {
		return timeStarted;
	}

	public void setTimeStarted(Time timeStarted) {
		this.timeStarted = timeStarted;
	}

	public Time getTimeFinished() {
		return timeFinished;
	}

	public void setTimeFinished(Time timeFinished) {
		this.timeFinished = timeFinished;
	}

	public RequestTechnicianLog() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public Integer getEmId() {
		return emId;
	}

	public void setEmId(Integer emId) {
		this.emId = emId;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Employee getEm() {
		return em;
	}

	public void setEm(Employee em) {
		this.em = em;
	}

	public RequestTechnicianLog(Integer requestTechnicianLogId, double actualHoursStd,
			double actualHoursDouble, double actualHoursOvertime, String workType, Date dateStarted, Date dateFinished,
			Time timeStarted, Time timeFinished,Integer requestId,Integer technicianId,String resourceType,CraftsPerson technician,
			Integer emId, String other) {
		super();
		this.requestTechnicianLogId = requestTechnicianLogId;
	
		this.actualHoursStd = actualHoursStd;
		this.actualHoursDouble = actualHoursDouble;
		this.actualHoursOvertime = actualHoursOvertime;
		this.workType = workType;
		this.dateStarted = dateStarted;
		this.dateFinished = dateFinished;
		this.timeStarted = timeStarted;
		this.timeFinished = timeFinished;
		this.requestId=requestId;
		this.technicianId=technicianId;
		this.resourceType=resourceType;
		this.technician = technician;
		this.emId = emId;
		this.other = other;

	}


	}


