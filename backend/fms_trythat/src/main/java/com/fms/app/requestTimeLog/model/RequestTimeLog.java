package com.fms.app.requestTimeLog.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "request_time_log")
@Table(name = "request_time_log")
public class RequestTimeLog {
	@Id
	@Column(name = "request_time_log_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer requestTimeLogId;

	@Column(name = "request_id")
	private Integer requestId;

	@Column(name = "date_start")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateStart;

	@Column(name = "date_end")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateEnd;

	@Column(name = "time_start")
	@JsonFormat(pattern = "HH:mm:ss")
	private Time timeStart;

	@Column(name = "time_end")
	@JsonFormat(pattern = "HH:mm:ss")
	private Time timeEnd;

	@Column(name = "logged_by")
	private Integer loggedBy;

	public RequestTimeLog(Integer requestTimeLogId, Integer requestId, Date dateStart, Date dateEnd, Time timeStart, Time timeEnd,
			Integer loggedBy) {
		super();
		this.requestTimeLogId = requestTimeLogId;
		this.requestId = requestId;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.loggedBy = loggedBy;
	}

	public RequestTimeLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the requestTimeLogId
	 */
	public Integer getRequestTimeLogId() {
		return requestTimeLogId;
	}

	/**
	 * @param requestTimeLogId the requestTimeLogId to set
	 */
	public void setRequestTimeLogId(Integer requestTimeLogId) {
		this.requestTimeLogId = requestTimeLogId;
	}

	/**
	 * @return the requestId
	 */
	public Integer getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
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
	 * @return the loggedBy
	 */
	public Integer getLoggedBy() {
		return loggedBy;
	}

	/**
	 * @param loggedBy the loggedBy to set
	 */
	public void setLoggedBy(Integer loggedBy) {
		this.loggedBy = loggedBy;
	}

}
