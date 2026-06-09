package com.fms.app.reservation.models.dto;

import java.util.Date;

public class RmConfigOutputDTO {
	private Integer configId;
	private String preBlock;
	private String postBlock;
	private String maxCapacity;
	private String minCapacity;
	private String externalAllowed;
	private Date dayStart;
	private Date dayEnd;
	private String isReservable;
	private Integer blId;
	private Integer flId;
	private Integer rmId;
	private String isApprovalRequired;
	private String arrangementArrangementType;
	private String rmRmName;
	private String rmSvgElementId;
	private Integer arrangementId;
	private String rmRmCode;

	public RmConfigOutputDTO() {
		super();
	}

	/**
	 * @return the configId
	 */
	public Integer getConfigId() {
		return configId;
	}

	/**
	 * @param configId the configId to set
	 */
	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	/**
	 * @return the preBlock
	 */
	public String getPreBlock() {
		return preBlock;
	}

	/**
	 * @param preBlock the preBlock to set
	 */
	public void setPreBlock(String preBlock) {
		this.preBlock = preBlock;
	}

	/**
	 * @return the postBlock
	 */
	public String getPostBlock() {
		return postBlock;
	}

	/**
	 * @param postBlock the postBlock to set
	 */
	public void setPostBlock(String postBlock) {
		this.postBlock = postBlock;
	}

	/**
	 * @return the maxCapacity
	 */
	public String getMaxCapacity() {
		return maxCapacity;
	}

	/**
	 * @param maxCapacity the maxCapacity to set
	 */
	public void setMaxCapacity(String maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	/**
	 * @return the minCapacity
	 */
	public String getMinCapacity() {
		return minCapacity;
	}

	/**
	 * @param minCapacity the minCapacity to set
	 */
	public void setMinCapacity(String minCapacity) {
		this.minCapacity = minCapacity;
	}

	/**
	 * @return the externalAllowed
	 */
	public String getExternalAllowed() {
		return externalAllowed;
	}

	/**
	 * @param externalAllowed the externalAllowed to set
	 */
	public void setExternalAllowed(String externalAllowed) {
		this.externalAllowed = externalAllowed;
	}

	/**
	 * @return the dayStart
	 */
	public Date getDayStart() {
		return dayStart;
	}

	/**
	 * @param dayStart the dayStart to set
	 */
	public void setDayStart(Date dayStart) {
		this.dayStart = dayStart;
	}

	/**
	 * @return the dayEnd
	 */
	public Date getDayEnd() {
		return dayEnd;
	}

	/**
	 * @param dayEnd the dayEnd to set
	 */
	public void setDayEnd(Date dayEnd) {
		this.dayEnd = dayEnd;
	}

	/**
	 * @return the isReservable
	 */
	public String getIsReservable() {
		return isReservable;
	}

	/**
	 * @param isReservable the isReservable to set
	 */
	public void setIsReservable(String isReservable) {
		this.isReservable = isReservable;
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
	 * @return the isApprovalRequired
	 */
	public String getIsApprovalRequired() {
		return isApprovalRequired;
	}

	/**
	 * @param isApprovalRequired the isApprovalRequired to set
	 */
	public void setIsApprovalRequired(String isApprovalRequired) {
		this.isApprovalRequired = isApprovalRequired;
	}

	/**
	 * @return the rmRmName
	 */
	public String getRmRmName() {
		return rmRmName;
	}

	/**
	 * @param rmRmName the rmRmName to set
	 */
	public void setRmRmName(String rmRmName) {
		this.rmRmName = rmRmName;
	}

	/**
	 * @return the rmSvgElementId
	 */
	public String getRmSvgElementId() {
		return rmSvgElementId;
	}

	/**
	 * @param rmSvgElementId the rmSvgElementId to set
	 */
	public void setRmSvgElementId(String rmSvgElementId) {
		this.rmSvgElementId = rmSvgElementId;
	}

	public String getArrangementArrangementType() {
		return arrangementArrangementType;
	}

	public void setArrangementArrangementType(String arrangementArrangementType) {
		this.arrangementArrangementType = arrangementArrangementType;
	}

	public Integer getArrangementId() {
		return arrangementId;
	}

	public void setArrangementId(Integer arrangementId) {
		this.arrangementId = arrangementId;
	}

	public String getRmRmCode() {
		return rmRmCode;
	}

	public void setRmRmCode(String rmRmCode) {
		this.rmRmCode = rmRmCode;
	}
	
}
