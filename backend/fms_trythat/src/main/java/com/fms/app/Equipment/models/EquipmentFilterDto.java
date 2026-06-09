package com.fms.app.Equipment.models;

import java.io.Serializable;

public class EquipmentFilterDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4068618574147897645L;

	private Integer eqId;
	
	private String eqCode;

	private Integer eqStdId;

	private String description;

	private String status;

	private Integer blId;

	private Integer flId;

	private Integer rmId;
	
	private String svgElementId;
	
	private Integer docBucketId;

	/**
	 * @return the eqId
	 */
	public Integer getEqId() {
		return eqId;
	}

	/**
	 * @param eqId the eqId to set
	 */
	public void setEqId(Integer eqId) {
		this.eqId = eqId;
	}

	/**
	 * @return the eqCode
	 */
	public String getEqCode() {
		return eqCode;
	}

	/**
	 * @param eqCode the eqCode to set
	 */
	public void setEqCode(String eqCode) {
		this.eqCode = eqCode;
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
	 * @return the svgElementId
	 */
	public String getSvgElementId() {
		return svgElementId;
	}

	/**
	 * @param svgElementId the svgElementId to set
	 */
	public void setSvgElementId(String svgElementId) {
		this.svgElementId = svgElementId;
	}

	/**
	 * @return the docBucketId
	 */
	public Integer getDocBucketId() {
		return docBucketId;
	}

	/**
	 * @param docBucketId the docBucketId to set
	 */
	public void setDocBucketId(Integer docBucketId) {
		this.docBucketId = docBucketId;
	}
	
}
