package com.fms.app.helpdesk.models.dto;

import java.util.Set;
import com.fms.app.helpdesk.models.SlaResponseParameters;

public class SlaRequestParameterDto {

	private Integer slaRequestParametersId;

	private Integer eqId;

	private Integer eqStdId;

	private Integer siteId;

	private Integer blId;

	private Integer flId;

	private Integer rmId;

	private Integer probTypeId;

	private String problemTypeString;

	private String isActive;

	private String siteSiteName;
	
	private String blBlName;
	
	private String eqStdEqStd;

	private Set<SlaResponseParameters> slaResponseParameters;

	public Integer getSlaRequestParametersId() {
		return slaRequestParametersId;
	}

	public void setSlaRequestParametersId(Integer slaRequestParametersId) {
		this.slaRequestParametersId = slaRequestParametersId;
	}

	public Integer getEqId() {
		return eqId;
	}

	public void setEqId(Integer eqId) {
		this.eqId = eqId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getBlId() {
		return blId;
	}

	public void setBlId(Integer blId) {
		this.blId = blId;
	}

	public Integer getFlId() {
		return flId;
	}

	public void setFlId(Integer flId) {
		this.flId = flId;
	}

	public Integer getRmId() {
		return rmId;
	}

	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}

	/**
	 * @return the eqStdId
	 */
	public Integer getEqStdId() {
		return eqStdId;
	}

	/**
	 * @param eqStdId the eqStdId to set
	 */
	public void setEqStdId(Integer eqStdId) {
		this.eqStdId = eqStdId;
	}

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

	public String getProblemTypeString() {
		return problemTypeString;
	}

	public void setProblemTypeString(String problemTypeString) {
		this.problemTypeString = problemTypeString;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Set<SlaResponseParameters> getSlaResponseParameters() {
		return slaResponseParameters;
	}

	public void setSlaResponseParameters(Set<SlaResponseParameters> slaResponseParameters) {
		this.slaResponseParameters = slaResponseParameters;
	}

	public String getSiteSiteName() {
		return siteSiteName;
	}

	public void setSiteSiteName(String siteSiteName) {
		this.siteSiteName = siteSiteName;
	}

	public String getBlBlName() {
		return blBlName;
	}

	public void setBlBlName(String blBlName) {
		this.blBlName = blBlName;
	}

	public String getEqStdEqStd() {
		return eqStdEqStd;
	}

	public void setEqStdEqStd(String eqStdEqStd) {
		this.eqStdEqStd = eqStdEqStd;
	}
	
}
