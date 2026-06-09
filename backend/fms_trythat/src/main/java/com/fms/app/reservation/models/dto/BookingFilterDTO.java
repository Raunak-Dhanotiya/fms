package com.fms.app.reservation.models.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonFormat;



public class BookingFilterDTO {
	private String bookFor;
	private String bookingType;
	private Integer blId;
	private Integer flId;
	private Integer rmId;
	private String arrangementType;
	private Integer capacity;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date date;
	@JsonFormat(pattern="HH:mm:ss")
	private Time fromTime;
	@JsonFormat(pattern="HH:mm:ss")
	private Time toTime;
	private Integer isDefault;
	private Collection<Integer> resourceIdList;
	private Integer arrangementId;
	
	public BookingFilterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param bookFor
	 * @param bookingType
	 * @param blId
	 * @param flId
	 * @param rmId
	 * @param arrangementType
	 * @param capacity
	 * @param date
	 * @param fromTime
	 * @param toTime
	 * @param isDefault
	 * @param resourceIdList
	 */
	public BookingFilterDTO(String bookFor, String bookingType, Integer blId, Integer flId, Integer rmId, String arrangementType,
			Integer capacity, Date date, Time fromTime, Time toTime, Integer isDefault, Collection<Integer> resourceIdList,
			Integer arrangementId) {
		super();
		this.bookFor = bookFor;
		this.bookingType = bookingType;
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
		this.arrangementType = arrangementType;
		this.capacity = capacity;
		this.date = date;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.isDefault = isDefault;
		this.resourceIdList = resourceIdList;
		this.arrangementId = arrangementId;
	}

	/**
	 * @return the bookFor
	 */
	public String getBookFor() {
		return bookFor;
	}

	/**
	 * @param bookFor the bookFor to set
	 */
	public void setBookFor(String bookFor) {
		this.bookFor = bookFor;
	}

	/**
	 * @return the bookingType
	 */
	public String getBookingType() {
		return bookingType;
	}

	/**
	 * @param bookingType the bookingType to set
	 */
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
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
	 * @return the arrangementType
	 */
	public String getArrangementType() {
		return arrangementType;
	}

	/**
	 * @param arrangementType the arrangementType to set
	 */
	public void setArrangementType(String arrangementType) {
		this.arrangementType = arrangementType;
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

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the fromTime
	 */
	public Time getFromTime() {
		return fromTime;
	}

	/**
	 * @param fromTime the fromTime to set
	 */
	public void setFromTime(Time fromTime) {
		this.fromTime = fromTime;
	}

	/**
	 * @return the toTime
	 */
	public Time getToTime() {
		return toTime;
	}

	/**
	 * @param toTime the toTime to set
	 */
	public void setToTime(Time toTime) {
		this.toTime = toTime;
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
	 * @return the resourceIdList
	 */
	public Collection<Integer> getResourceIdList() {
		return resourceIdList;
	}

	/**
	 * @param resourceIdList the resourceIdList to set
	 */
	public void setResourceIdList(Collection<Integer> resourceIdList) {
		this.resourceIdList = resourceIdList;
	}

	public Integer getArrangementId() {
		return arrangementId;
	}

	public void setArrangementId(Integer arrangementId) {
		this.arrangementId = arrangementId;
	}
	
}
