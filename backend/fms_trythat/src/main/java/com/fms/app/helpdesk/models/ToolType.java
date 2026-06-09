package com.fms.app.helpdesk.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "tool_type")
@Table(name = "tool_type")
public class ToolType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tool_type_id")
	private Integer toolTypeId;
	
	@Column(name = "tool_type")
	private String toolType;
	
	@Column(name="description")
	private String description;

	public ToolType() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param toolTypeId
	 * @param toolType
	 * @param description
	 */
	public ToolType(Integer toolTypeId, String toolType, String description) {
		super();
		this.toolTypeId = toolTypeId;
		this.toolType = toolType;
		this.description = description;
	}

	/**
	 * @return the toolTypeId
	 */
	public Integer getToolTypeId() {
		return toolTypeId;
	}

	/**
	 * @param toolTypeId the toolTypeId to set
	 */
	public void setToolTypeId(Integer toolTypeId) {
		this.toolTypeId = toolTypeId;
	}

	/**
	 * @return the toolType
	 */
	public String getToolType() {
		return toolType;
	}

	/**
	 * @param toolType the toolType to set
	 */
	public void setToolType(String toolType) {
		this.toolType = toolType;
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
	
}
