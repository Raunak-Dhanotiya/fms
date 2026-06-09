package com.fms.app.helpdesk.models;

import java.sql.Date;
import java.sql.Time;

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
import com.fms.app.userModels.User;

@Entity(name = "request_log")
@Table(name = "request_log")
public class RequestLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "request_log_id")
	private Integer requestLogId;
	
	@Column(name = "request_id")
	private Integer requestId; 
	
	@Column(name = "changed_by")
	private Integer changedBy; 
	
	@Column(name = "date_changed")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateChanged;

	@Column(name = "time_changed")
	@JsonFormat(pattern="HH:mm:ss")
	private Time timeChanged;
	
	@Column(name = "comments")
	private String comments; 
	
	@Column(name = "status")
	private String status;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "changed_by",referencedColumnName = "user_id", insertable = false, updatable = false)
	private User user;

	public RequestLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestLog(Integer requestLogId, Integer requestId, Integer changedBy, Date dateChanged, Time timeChanged,
			String comments, String status) {
		super();
		this.requestLogId = requestLogId;
		this.requestId = requestId;
		this.changedBy = changedBy;
		this.dateChanged = dateChanged;
		this.timeChanged = timeChanged;
		this.comments = comments;
		this.status = status;
	}

	public Integer getRequestLogId() {
		return requestLogId;
	}

	public void setRequestLogId(Integer requestLogId) {
		this.requestLogId = requestLogId;
	}

	public Integer getRequestId() {
		return requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	public Integer getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(Integer changedBy) {
		this.changedBy = changedBy;
	}

	public Date getDateChanged() {
		return dateChanged;
	}

	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}

	public Time getTimeChanged() {
		return timeChanged;
	}

	public void setTimeChanged(Time timeChanged) {
		this.timeChanged = timeChanged;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	} 
	
}
