package com.fms.app.ppm.model.dto;

import java.util.List;

public class PlanScheduleDTO {
	private Integer planScheduleId;
	private Integer planLocEqId;
	private String type;
	private Integer freq;
	private Integer iterations;
	private String weekDays;
	private String weeks;
	private String monthDays;
	private String dateStart;
	private String dateEnd;
	private String description;
	private Integer priority;
	private List<String> scheduleDates;
	private boolean isEdit;
	private String isActive;
	
	public Integer getPlanScheduleId() {
		return planScheduleId;
	}
	public void setPlanScheduleId(Integer planScheduleId) {
		this.planScheduleId = planScheduleId;
	}
	public Integer getPlanLocEqId() {
		return planLocEqId;
	}
	public void setPlanLocEqId(Integer planLocEqId) {
		this.planLocEqId = planLocEqId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getFreq() {
		return freq;
	}
	public void setFreq(Integer freq) {
		this.freq = freq;
	}
	public Integer getIterations() {
		return iterations;
	}
	public void setIterations(Integer iterations) {
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
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public List<String> getScheduleDates() {
		return scheduleDates;
	}
	public void setScheduleDates(List<String> scheduleDates) {
		this.scheduleDates = scheduleDates;
	}
	public boolean getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
}
