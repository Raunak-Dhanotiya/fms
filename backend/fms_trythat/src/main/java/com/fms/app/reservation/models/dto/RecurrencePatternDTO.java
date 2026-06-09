package com.fms.app.reservation.models.dto;

import java.time.*;

public class RecurrencePatternDTO {
	private String type;
	private Integer frequency;
	private String monthDays;
	private String weekDays;
	private String weeks;
	private LocalDate dateStart;
	private LocalDate dateEnd;
	public RecurrencePatternDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RecurrencePatternDTO(String type, Integer frequency, String monthDays, String weekDays, String weeks,
			LocalDate dateStart, LocalDate dateEnd) {
		super();
		this.type = type;
		this.frequency = frequency;
		this.monthDays = monthDays;
		this.weekDays = weekDays;
		this.weeks = weeks;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	public String getMonthDays() {
		return monthDays;
	}
	public void setMonthDays(String monthDays) {
		this.monthDays = monthDays;
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
	public LocalDate getDateStart() {
		return dateStart;
	}
	public void setDateStart(LocalDate dateStart) {
		this.dateStart = dateStart;
	}
	public LocalDate getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(LocalDate dateEnd) {
		this.dateEnd = dateEnd;
	}
	
}
