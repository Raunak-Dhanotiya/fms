package com.fms.app.helpdesk.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "tools")
@Table(name = "tools")
public class Tools {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tools_id")
	private int toolsId;
	
	@Column(name = "tool")
	private String tool;
	
	@Column(name = "tool_type_id")
	private String toolTypeId;
	
	@Column(name= "rate_hourly")
	private double hourlyRate;
	
	@Column(name= "rate_over")
	private double overTimeRate;
	
	@Column(name= "std_hours_avail")
	private double standardAvalTime;
	
	@Column(name="description")
	private String description;
	
	@Column(name= "rate_double")
	private double doubleRate;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tool_type_id", referencedColumnName = "tool_type_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private ToolType toolType;

	public Tools() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param toolsId
	 * @param tool
	 * @param toolTypeId
	 * @param hourlyRate
	 * @param overTimeRate
	 * @param standardAvalTime
	 * @param description
	 * @param doubleRate
	 */
	public Tools(int toolsId, String tool, String toolTypeId, double hourlyRate, double overTimeRate,
			double standardAvalTime, String description, double doubleRate) {
		super();
		this.toolsId = toolsId;
		this.tool = tool;
		this.toolTypeId = toolTypeId;
		this.hourlyRate = hourlyRate;
		this.overTimeRate = overTimeRate;
		this.standardAvalTime = standardAvalTime;
		this.description = description;
		this.doubleRate = doubleRate;
	}

	/**
	 * @return the toolsId
	 */
	public int getToolsId() {
		return toolsId;
	}

	/**
	 * @param toolsId the toolsId to set
	 */
	public void setToolsId(int toolsId) {
		this.toolsId = toolsId;
	}

	/**
	 * @return the tool
	 */
	public String getTool() {
		return tool;
	}

	/**
	 * @param tool the tool to set
	 */
	public void setTool(String tool) {
		this.tool = tool;
	}

	/**
	 * @return the toolTypeId
	 */
	public String getToolTypeId() {
		return toolTypeId;
	}

	/**
	 * @param toolTypeId the toolTypeId to set
	 */
	public void setToolTypeId(String toolTypeId) {
		this.toolTypeId = toolTypeId;
	}

	/**
	 * @return the hourlyRate
	 */
	public double getHourlyRate() {
		return hourlyRate;
	}

	/**
	 * @param hourlyRate the hourlyRate to set
	 */
	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	/**
	 * @return the overTimeRate
	 */
	public double getOverTimeRate() {
		return overTimeRate;
	}

	/**
	 * @param overTimeRate the overTimeRate to set
	 */
	public void setOverTimeRate(double overTimeRate) {
		this.overTimeRate = overTimeRate;
	}

	/**
	 * @return the standardAvalTime
	 */
	public double getStandardAvalTime() {
		return standardAvalTime;
	}

	/**
	 * @param standardAvalTime the standardAvalTime to set
	 */
	public void setStandardAvalTime(double standardAvalTime) {
		this.standardAvalTime = standardAvalTime;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the doubleRate
	 */
	public double getDoubleRate() {
		return doubleRate;
	}

	/**
	 * @param doubleRate the doubleRate to set
	 */
	public void setDoubleRate(double doubleRate) {
		this.doubleRate = doubleRate;
	}

	public ToolType getToolType() {
		return toolType;
	}

	public void setToolType(ToolType toolType) {
		this.toolType = toolType;
	}
	
}
