package com.fms.app.helpdesk.models.dto;

public class AssignUnAssignWorkTeamsDto {
	
	private Integer cfId;
	private Integer emId;
	private String enumValue;
	/**
	 * @return the cfId
	 */
	public Integer getCfId() {
		return cfId;
	}
	/**
	 * @param cfId the cfId to set
	 */
	public void setCfId(Integer cfId) {
		this.cfId = cfId;
	}
	/**
	 * @return the emId
	 */
	public Integer getEmId() {
		return emId;
	}
	/**
	 * @param emId the emId to set
	 */
	public void setEmId(Integer emId) {
		this.emId = emId;
	}
	/**
	 * @return the enumValue
	 */
	public String getEnumValue() {
		return enumValue;
	}
	/**
	 * @param enumValue the enumValue to set
	 */
	public void setEnumValue(String enumValue) {
		this.enumValue = enumValue;
	}
}
