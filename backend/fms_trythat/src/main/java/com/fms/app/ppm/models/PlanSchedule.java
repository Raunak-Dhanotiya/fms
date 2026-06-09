package com.fms.app.ppm.models;

import java.sql.Date;

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

@Entity(name = "plan_sched")
@Table(name = "plan_sched")
public class PlanSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plan_sched_id")
	private int planScheduleId;
	
	@Column(name = "plan_loc_eq_id")
	private int planLocEqId;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "freq")
	private int freq;
	
	@Column(name = "iterations")
	private int iterations;
	
	@Column(name = "week_days")
	private String weekDays;
	
	@Column(name = "weeks")
	private String weeks;
	
	@Column(name = "month_days")
	private String monthDays;
	
	@Column(name = "date_start")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateStart;
	
	@Column(name = "date_end")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateEnd;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "priority")
	private int priority;
	
	@Column(name = "is_active")
	private String isActive;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "plan_loc_eq_id", referencedColumnName = "plan_loc_eq_id", insertable = false, updatable = false)
	private LinkPlanToLocationOrAsset planLocEq;
	
	public PlanSchedule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanSchedule(int planScheduleId, int planLocEqId, String type, int freq, int iterations, String weekDays,
			String weeks, String monthDays, Date dateStart, Date dateEnd,String description,int priority,String isActive) {
		super();
		this.planScheduleId = planScheduleId;
		this.planLocEqId = planLocEqId;
		this.type = type;
		this.freq = freq;
		this.iterations = iterations;
		this.weekDays = weekDays;
		this.weeks = weeks;
		this.monthDays = monthDays;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.description = description;
		this.priority = priority;
		this.isActive = isActive;
	}

	public int getPlanScheduleId() {
		return planScheduleId;
	}

	public void setPlanScheduleId(int planScheduleId) {
		this.planScheduleId = planScheduleId;
	}

	public int getPlanLocEqId() {
		return planLocEqId;
	}

	public void setPlanLocEqId(int planLocEqId) {
		this.planLocEqId = planLocEqId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public String getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(String weekDays) {
		this.weekDays = weekDays;
	}

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public String getMonthDays() {
		return monthDays;
	}

	public void setMonthDays(String monthDays) {
		this.monthDays = monthDays;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public LinkPlanToLocationOrAsset getPlanLocEq() {
		return planLocEq;
	}

	public void setPlanLocEq(LinkPlanToLocationOrAsset planLocEq) {
		this.planLocEq = planLocEq;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
}
