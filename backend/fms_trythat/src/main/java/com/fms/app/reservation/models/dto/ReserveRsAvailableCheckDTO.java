package com.fms.app.reservation.models.dto;
import java.sql.Date;
import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReserveRsAvailableCheckDTO {
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Time timeStart;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Time timeEnd;
	private Integer resourcesId;
	private Integer reserveRsId;
	public ReserveRsAvailableCheckDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(Time timeStart) {
		this.timeStart = timeStart;
	}
	public Time getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(Time timeEnd) {
		this.timeEnd = timeEnd;
	}
	public Integer getResourcesId() {
		return resourcesId;
	}
	public void setResourcesId(Integer resourcesId) {
		this.resourcesId = resourcesId;
	}
	public Integer getReserveRsId() {
		return reserveRsId;
	}
	public void setReserveRsId(Integer reserveRsId) {
		this.reserveRsId = reserveRsId;
	}

}
