package com.fms.app.reservation.models;

import java.io.Serializable;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "reserve_filter")
@Table(name = "reserve_filter")
public class ReserveFilter implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5070934207829642776L;
	
	@Id
	@Column(name = "reserve_filter_id")
	private Integer reserveFilterId;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "seq_id")
	private Integer seqId;
	
	@Column(name = "bl_id")
	private Integer blId;
	
	@Column(name = "fl_id")
	private Integer flId;

	@Column(name = "rm_id")
	private Integer rmId;
	
	@Column(name = "time_start")
	private Time timeStart;

	@Column(name = "time_end")
	private Time timeEnd;
	
	@Column(name = "is_default")
	private Integer isDefault;
	
	@Column(name = "capacity")
	private Integer capacity;
	
	public ReserveFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param reserveFilterId
	 * @param userId
	 * @param seqId
	 * @param blId
	 * @param flId
	 * @param rmId
	 * @param timeStart
	 * @param timeEnd
	 * @param isDefault
	 * @param capacity
	 */
	public ReserveFilter(Integer reserveFilterId, Integer userId, Integer seqId, Integer blId, Integer flId, Integer rmId, Time timeStart,
			Time timeEnd, Integer isDefault, Integer capacity) {
		super();
		this.reserveFilterId = reserveFilterId;
		this.userId = userId;
		this.seqId = seqId;
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.isDefault = isDefault;
		this.capacity = capacity;
	}

	/**
	 * @return the reserveFilterId
	 */
	public Integer getReserveFilterId() {
		return reserveFilterId;
	}

	/**
	 * @param reserveFilterId the reserveFilterId to set
	 */
	public void setReserveFilterId(Integer reserveFilterId) {
		this.reserveFilterId = reserveFilterId;
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

	/**
	 * @return the seqId
	 */
	public Integer getSeqId() {
		return seqId;
	}

	/**
	 * @param seqId the seqId to set
	 */
	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}

	/**
	 * @return the blId
	 */
	public Integer getBlId() {
		return blId;
	}

	/**
	 * @param blId the blId to set
	 */
	public void setBlId(Integer blId) {
		this.blId = blId;
	}

	/**
	 * @return the flId
	 */
	public Integer getFlId() {
		return flId;
	}

	/**
	 * @param flId the flId to set
	 */
	public void setFlId(Integer flId) {
		this.flId = flId;
	}

	/**
	 * @return the rmId
	 */
	public Integer getRmId() {
		return rmId;
	}

	/**
	 * @param rmId the rmId to set
	 */
	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}

	/**
	 * @return the timeStart
	 */
	public Time getTimeStart() {
		return timeStart;
	}

	/**
	 * @param timeStart the timeStart to set
	 */
	public void setTimeStart(Time timeStart) {
		this.timeStart = timeStart;
	}

	/**
	 * @return the timeEnd
	 */
	public Time getTimeEnd() {
		return timeEnd;
	}

	/**
	 * @param timeEnd the timeEnd to set
	 */
	public void setTimeEnd(Time timeEnd) {
		this.timeEnd = timeEnd;
	}

	/**
	 * @return the isDefault
	 */
	public Integer getIsDefault() {
		return isDefault;
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * @return the capacity
	 */
	public Integer getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	
}
