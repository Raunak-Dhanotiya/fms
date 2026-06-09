package com.fms.app.helpdesk.models.dto;

import java.io.Serializable;

public class EqStdFilterDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2704649962010634526L;
	
	private String eqStdId; 
	private String eqStd; 
	private String description;
	
	/**
	 * @return the eqStdId
	 */
	public String getEqStdId() {
		return eqStdId;
	}
	/**
	 * @param eqStdId the eqStdId to set
	 */
	public void setEqStdId(String eqStdId) {
		this.eqStdId = eqStdId;
	}
	/**
	 * @return the eqStd
	 */
	public String getEqStd() {
		return eqStd;
	}
	/**
	 * @param eqStd the eqStd to set
	 */
	public void setEqStd(String eqStd) {
		this.eqStd = eqStd;
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
