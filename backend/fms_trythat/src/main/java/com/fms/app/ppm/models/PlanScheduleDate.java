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

@Entity//(name = "plan_sched_date")
@Table(name = "plan_sched_date")
public class PlanScheduleDate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plan_sched_date_id")
	private int planScheduleDateId;
	
	@Column(name = "plan_sched_id")
	private int planScheduleId;
	
	@Column(name = "sched_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date scheduleDate;
	
	@Column(name = "wr_id")
	private Integer wrId;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "plan_sched_id", referencedColumnName = "plan_sched_id", insertable = false, updatable = false)
	private PlanSchedule planSchedule;

	public PlanScheduleDate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanScheduleDate(int planScheduleDateId, int planScheduleId, Date scheduleDate, Integer wrId) {
		super();
		this.planScheduleDateId = planScheduleDateId;
		this.planScheduleId = planScheduleId;
		this.scheduleDate = scheduleDate;
		this.wrId = wrId;
	}

	public int getPlanScheduleDateId() {
		return planScheduleDateId;
	}

	public void setPlanScheduleDateId(int planScheduleDateId) {
		this.planScheduleDateId = planScheduleDateId;
	}

	public int getPlanScheduleId() {
		return planScheduleId;
	}

	public void setPlanScheduleId(int planScheduleId) {
		this.planScheduleId = planScheduleId;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public Integer getWrId() {
		return wrId;
	}

	public void setWrId(Integer wrId) {
		this.wrId = wrId;
	}

	public PlanSchedule getPlanSchedule() {
		return planSchedule;
	}

	public void setPlanSchedule(PlanSchedule planSchedule) {
		this.planSchedule = planSchedule;
	}
	
}
