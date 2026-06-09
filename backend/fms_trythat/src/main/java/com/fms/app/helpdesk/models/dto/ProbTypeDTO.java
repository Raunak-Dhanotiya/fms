package com.fms.app.helpdesk.models.dto;

import java.io.Serializable;

public class ProbTypeDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer probTypeId;
	private String probType;
	private String description;
	
	/**
	 * @return the probTypeId
	 */
	public Integer getProbTypeId() {
		return probTypeId;
	}
	/**
	 * @param probTypeId the probTypeId to set
	 */
	public void setProbTypeId(Integer probTypeId) {
		this.probTypeId = probTypeId;
	}
	public String getProbType() {
		return probType;
	}
	public void setProbType(String probType) {
		this.probType = probType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
