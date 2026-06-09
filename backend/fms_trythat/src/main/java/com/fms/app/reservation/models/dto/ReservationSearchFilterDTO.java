package com.fms.app.reservation.models.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;

public class ReservationSearchFilterDTO {
	private Integer reserveId;
	private Integer requestedBy;
	private Integer blId;
	private Integer flId;
	private Integer rmId;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date dateStart;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date dateEnd;
	private String status;
	private FilterDTOCopy filterDto;
	public ReservationSearchFilterDTO() {
		super();
	}
	/**
	 * @param id
	 * @param requestedBy
	 * @param blId
	 * @param flId
	 * @param rmId
	 * @param dateStart
	 * @param dateEnd
	 * @param status
	 */
	public ReservationSearchFilterDTO(Integer reserveId, Integer requestedBy, Integer blId, Integer flId, Integer rmId, Date dateStart,
			Date dateEnd, String status) {
		super();
		this.reserveId = reserveId;
		this.requestedBy = requestedBy;
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.status = status;
	}
	
	/**
	 * @return the requestedBy
	 */
	public Integer getRequestedBy() {
		return requestedBy;
	}
	/**
	 * @param requestedBy the requestedBy to set
	 */
	public void setRequestedBy(Integer requestedBy) {
		this.requestedBy = requestedBy;
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
	 * @return the dateStart
	 */
	public Date getDateStart() {
		return dateStart;
	}
	/**
	 * @param dateStart the dateStart to set
	 */
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	/**
	 * @return the dateEnd
	 */
	public Date getDateEnd() {
		return dateEnd;
	}
	/**
	 * @param dateEnd the dateEnd to set
	 */
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getReserveId() {
		return reserveId;
	}
	public void setReserveId(Integer reserveId) {
		this.reserveId = reserveId;
	}
	public FilterDTOCopy getFilterDto() {
		return filterDto;
	}
	public void setFilterDto(FilterDTOCopy filterDto) {
		this.filterDto = filterDto;
	}
	
}
