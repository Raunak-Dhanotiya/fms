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

@Entity(name = "request_tools")
@Table(name = "request_tools")
public class RequestTools 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "req_tool_id")
	private Integer reqToolId;
	
	@Column(name = "request_id")
	private Integer requestId;
	
	@Column(name = "date_assign")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateAssign;
	
	@Column(name = "time_assign")
	@JsonFormat(pattern="HH:mm:ss")
	private Time timeAssign;
	
	@Column(name = "hours_required")
	private double hoursRequired;
	
	@Column(name = "tool_id")
	private Integer toolId;
	
	@Column(name = "actual_hours_std")
	private double actualHoursStd;
	
	@Column(name = "actual_hours_double")
	private double actualHoursDouble;
	
	@Column(name = "actual_hours_overtime")
	private double actualHoursOvertime;
    
	@Column(name = "added_by")
	private Integer addedBy;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "request_id", referencedColumnName = "wr_id", insertable = false, updatable = false)
	private Wr request;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tool_id", referencedColumnName = "tools_id", insertable = false, updatable = false)
	private Tools tool;
	
	public RequestTools(Integer reqToolId, Integer requestId, Date dateAssign, Time timeAssign, double hoursRequired,
			Integer toolId, double actualHoursStd, double actualHoursDouble, double actualHoursOvertime,Integer addedBy) {
		super();
		this.reqToolId = reqToolId;
		this.requestId = requestId;
		this.dateAssign = dateAssign;
		this.timeAssign = timeAssign;
		this.hoursRequired = hoursRequired;
		this.toolId = toolId;
		this.actualHoursStd = actualHoursStd;
		this.actualHoursDouble = actualHoursDouble;
		this.actualHoursOvertime = actualHoursOvertime;
		this.addedBy = addedBy;
	}

	public RequestTools() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the reqToolId
	 */
	public Integer getReqToolId() {
		return reqToolId;
	}

	/**
	 * @param reqToolId the reqToolId to set
	 */
	public void setReqToolId(Integer reqToolId) {
		this.reqToolId = reqToolId;
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
	 * @return the dateAssign
	 */
	public Date getDateAssign() {
		return dateAssign;
	}

	/**
	 * @param dateAssign the dateAssign to set
	 */
	public void setDateAssign(Date dateAssign) {
		this.dateAssign = dateAssign;
	}

	/**
	 * @return the timeAssign
	 */
	public Time getTimeAssign() {
		return timeAssign;
	}

	/**
	 * @param timeAssign the timeAssign to set
	 */
	public void setTimeAssign(Time timeAssign) {
		this.timeAssign = timeAssign;
	}

	/**
	 * @return the hoursRequired
	 */
	public double getHoursRequired() {
		return hoursRequired;
	}

	/**
	 * @param hoursRequired the hoursRequired to set
	 */
	public void setHoursRequired(double hoursRequired) {
		this.hoursRequired = hoursRequired;
	}

	/**
	 * @return the toolId
	 */
	public Integer getToolId() {
		return toolId;
	}

	/**
	 * @param toolId the toolId to set
	 */
	public void setToolId(Integer toolId) {
		this.toolId = toolId;
	}

	/**
	 * @return the actualHoursStd
	 */
	public double getActualHoursStd() {
		return actualHoursStd;
	}

	/**
	 * @param actualHoursStd the actualHoursStd to set
	 */
	public void setActualHoursStd(double actualHoursStd) {
		this.actualHoursStd = actualHoursStd;
	}

	/**
	 * @return the actualHoursDouble
	 */
	public double getActualHoursDouble() {
		return actualHoursDouble;
	}

	/**
	 * @param actualHoursDouble the actualHoursDouble to set
	 */
	public void setActualHoursDouble(double actualHoursDouble) {
		this.actualHoursDouble = actualHoursDouble;
	}

	/**
	 * @return the actualHoursOvertime
	 */
	public double getActualHoursOvertime() {
		return actualHoursOvertime;
	}

	/**
	 * @param actualHoursOvertime the actualHoursOvertime to set
	 */
	public void setActualHoursOvertime(double actualHoursOvertime) {
		this.actualHoursOvertime = actualHoursOvertime;
	}

	/**
	 * @return the addedBy
	 */
	public Integer getAddedBy() {
		return addedBy;
	}

	/**
	 * @param addedBy the addedBy to set
	 */
	public void setAddedBy(Integer addedBy) {
		this.addedBy = addedBy;
	}

	/**
	 * @return the request
	 */
	public Wr getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(Wr request) {
		this.request = request;
	}

	/**
	 * @return the tool
	 */
	public Tools getTool() {
		return tool;
	}

	/**
	 * @param tool the tool to set
	 */
	public void setTool(Tools tool) {
		this.tool = tool;
	}
	
}
