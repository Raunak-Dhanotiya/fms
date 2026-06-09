package com.fms.app.helpdesk.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity(name = "holidays")
@Table(name = "holidays")
public class Holidays {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="holidays_id", updatable = false, nullable = false)
	private Integer holidaysId;
	
	@Column(name="holiday_date", updatable = false, nullable = false)
	@Temporal(TemporalType.DATE)
	private Date holidayDate;
	
	@Column(name="holiday_desc")
	private String holidayDesc;

	public Holidays(Integer holidaysId, Date holidayDate, String holidayDesc) {
		super();
		this.holidaysId = holidaysId;
		this.holidayDate = holidayDate;
		this.holidayDesc = holidayDesc;
	}

	public Holidays() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getHolidaysId() {
		return holidaysId;
	}

	public void setHolidaysId(Integer holidaysId) {
		this.holidaysId = holidaysId;
	}

	public Date getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
	}

	public String getHolidayDesc() {
		return holidayDesc;
	}

	public void setHolidayDesc(String holidayDesc) {
		this.holidayDesc = holidayDesc;
	}

}
