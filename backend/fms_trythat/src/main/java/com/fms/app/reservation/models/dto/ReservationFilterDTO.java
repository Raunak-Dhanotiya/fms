package com.fms.app.reservation.models.dto;

import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonFormat;
public class ReservationFilterDTO {
	
	private Integer userId;
	
	private Integer blId;
	
	private Integer flId;

	private Integer rmId;
	@JsonFormat(pattern = "HH:mm:ss")
	private Time timeStart;

	@JsonFormat(pattern = "HH:mm:ss")
	private Time timeEnd;
	
	private Integer isDefault;
	
	private Integer seqId;
	
	private Integer capacity;
	
	public Integer getBlId() {
		return blId;
	}
	public void setBlId(Integer blId) {
		this.blId = blId;
	}
	public Integer getFlId() {
		return flId;
	}
	public void setFlId(Integer flId) {
		this.flId = flId;
	}
	public Integer getRmId() {
		return rmId;
	}
	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getSeqId() {
		return seqId;
	}
	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

}
