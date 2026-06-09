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

@Entity(name = "request_technician")
@Table(name = "request_technician")
public class RequestTechnician
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "request_technician_id")
	private Integer requestTechnicianId;
	
	@Column(name = "technician_id")
	private Integer technicianId;
	
	@Column(name = "date_assign")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateAssign;

	@Column(name = "time_assign")
	@JsonFormat(pattern="HH:mm:ss")
	private Time timeAssign;
	
	@Column(name = "hours_required")
	private double hoursRequired;

	@Column(name = "request_id")
	private Integer requestId;
	
	@Column(name = "actual_hours_std")
	private double actualHoursStd;
	
	@Column(name = "actual_hours_double")
	private double actualHoursDouble;
	
	@Column(name = "actual_hours_overtime")
	private double actualHoursOvertime;
	
	@Column(name = "team_id")
	private Integer teamId;
	
	@Column(name = "complete_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date completeDate;

	@Column(name = "complete_time")
	@JsonFormat(pattern="HH:mm:ss")
	private Time completeTime;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "technician_id", referencedColumnName = "cf_id", insertable = false, updatable = false)
	private CraftsPerson technician;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "request_id", referencedColumnName = "wr_id", insertable = false, updatable = false)
	private Wr request;

	public RequestTechnician() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Integer getRequestId() {
		return requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	public Integer getRequestTechnicianId() {
		return requestTechnicianId;
	}
	public void setRequestTechnicianId(Integer requestTechnicianId) {
		this.requestTechnicianId = requestTechnicianId;
	}
	public Integer getTechnicianId() {
		return technicianId;
	}
	public void setTechnicianId(Integer technicianId) {
		this.technicianId = technicianId;
	}

	public Date getDateAssign() {
		return dateAssign;
	}

	public void setDateAssign(Date dateAssign) {
		this.dateAssign = dateAssign;
	}

	public Time getTimeAssign() {
		return timeAssign;
	}

	public void setTimeAssign(Time timeAssign) {
		this.timeAssign = timeAssign;
	}

	public double getHoursRequired() {
		return hoursRequired;
	}

	public void setHoursRequired(double hoursRequired) {
		this.hoursRequired = hoursRequired;
	}

	public CraftsPerson getTechnician() {
		return technician;
	}

	public void setTechnician(CraftsPerson technician) {
		this.technician = technician;
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
	
	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public Time getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Time completeTime) {
		this.completeTime = completeTime;
	}
	
	public Wr getRequest() {
		return request;
	}

	public void setRequest(Wr request) {
		this.request = request;
	}

	public RequestTechnician(Integer requestTechnicianId, Integer technicianId, Date dateAssign, Time timeAssign,
			double hoursRequired, Integer requestId, double actualHoursStd, double actualHoursDouble,
			double actualHoursOvertime, CraftsPerson technician,String teamId, Date completeDate, Time completeTime) {
		super();
		this.requestTechnicianId = requestTechnicianId;
		this.technicianId = technicianId;
		this.dateAssign = dateAssign;
		this.timeAssign = timeAssign;
		this.hoursRequired = hoursRequired;
		this.requestId = requestId;
		this.actualHoursStd = actualHoursStd;
		this.actualHoursDouble = actualHoursDouble;
		this.actualHoursOvertime = actualHoursOvertime;
		this.technician = technician;
		this.completeDate = completeDate;
		this.completeTime = completeTime;
	}
}
