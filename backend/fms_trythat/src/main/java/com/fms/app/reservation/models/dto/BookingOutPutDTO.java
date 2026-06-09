package com.fms.app.reservation.models.dto;

import java.util.Date;



public class BookingOutPutDTO {
	private Integer blId;
	private Integer flId;
	private Integer rmId;
	private String preBlock;
	private String postBlock;
	private String maxCapacity;
	private String minCapacity;
	private String externalAllowed;
	private Date dayStart;
	private Date dayEnd;
	private Integer arrangementId;
	private String rmRmName;
	private byte[] rmDocFileContent;
	private byte[] rmPhoto;
	private Integer conflicts;
	private Integer configId;
	private String isApprovalRequired;
	private String arrangementArrangementType;
	private String blBlCode;
	private String flFlCode;
	private String rmRmCode;
	private String blBlName;

	public BookingOutPutDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BookingOutPutDTO(Integer blId, Integer flId, Integer rmId, String preBlock, String postBlock,
			String maxCapacity, String minCapacity, String externalAllowed, Date dayStart, Date dayEnd, Integer arrangementId,String rmRmName,
			byte[] rmDocFileContent, byte[] rmPhoto,Integer conflicts,String isApprovalRequired,Integer configId) {
		super();
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
		this.preBlock = preBlock;
		this.postBlock = postBlock;
		this.maxCapacity = maxCapacity;
		this.minCapacity = minCapacity;
		this.externalAllowed = externalAllowed;
		this.dayStart = dayStart;
		this.dayEnd = dayEnd;
		this.arrangementId = arrangementId;
		this.setRmRmName(rmRmName);
		this.rmDocFileContent = rmDocFileContent;
		this.rmPhoto = rmPhoto;
		this.conflicts = conflicts;
		this.configId = configId;
		this.isApprovalRequired = isApprovalRequired;

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
	public String getPreBlock() {
		return preBlock;
	}
	public void setPreBlock(String preBlock) {
		this.preBlock = preBlock;
	}
	public String getPostBlock() {
		return postBlock;
	}
	public void setPostBlock(String postBlock) {
		this.postBlock = postBlock;
	}
	public String getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(String maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	public String getMinCapacity() {
		return minCapacity;
	}
	public void setMinCapacity(String minCapacity) {
		this.minCapacity = minCapacity;
	}
	public String getExternalAllowed() {
		return externalAllowed;
	}
	public void setExternalAllowed(String externalAllowed) {
		this.externalAllowed = externalAllowed;
	}
	public Date getDayStart() {
		return dayStart;
	}
	public void setDayStart(Date dayStart) {
		this.dayStart = dayStart;
	}
	public Date getDayEnd() {
		return dayEnd;
	}
	public void setDayEnd(Date dayEnd) {
		this.dayEnd = dayEnd;
	}
	
	public byte[] getRmDocFileContent() {
		return rmDocFileContent;
	}
	public void setRmDocFileContent(byte[] rmDocFileContent) {
		this.rmDocFileContent = rmDocFileContent;
	}
	public byte[] getRmPhoto() {
		return rmPhoto;
	}
	public void setRmPhoto(byte[] rmPhoto) {
		this.rmPhoto = rmPhoto;
	}
	public String getRmRmName() {
		return rmRmName;
	}
	public void setRmRmName(String rmRmName) {
		this.rmRmName = rmRmName;
	}
	
	public Integer getConflicts() {
		return conflicts;
	}
	public void setConflicts(Integer conflicts) {
		this.conflicts = conflicts;
	}
	
	public Integer getConfigId() {
		return configId;
	}
	
	public void setConfigId(Integer configId) {
		this.configId = configId;
	}
	
	public String getIsApprovalRequired() {
		return isApprovalRequired;
	}
	
	public void setIsApprovalRequired(String isApprovalRequired) {
		this.isApprovalRequired = isApprovalRequired;
	}
	public Integer getArrangementId() {
		return arrangementId;
	}
	public void setArrangementId(Integer arrangementId) {
		this.arrangementId = arrangementId;
	}
	public String getArrangementArrangementType() {
		return arrangementArrangementType;
	}
	public void setArrangementArrangementType(String arrangementArrangementType) {
		this.arrangementArrangementType = arrangementArrangementType;
	}
	public String getBlBlCode() {
		return blBlCode;
	}
	public void setBlBlCode(String blBlCode) {
		this.blBlCode = blBlCode;
	}
	public String getFlFlCode() {
		return flFlCode;
	}
	public void setFlFlCode(String flFlCode) {
		this.flFlCode = flFlCode;
	}
	public String getRmRmCode() {
		return rmRmCode;
	}
	public void setRmRmCode(String rmRmCode) {
		this.rmRmCode = rmRmCode;
	}
	public String getBlBlName() {
		return blBlName;
	}
	public void setBlBlName(String blBlName) {
		this.blBlName = blBlName;
	}
	
}
