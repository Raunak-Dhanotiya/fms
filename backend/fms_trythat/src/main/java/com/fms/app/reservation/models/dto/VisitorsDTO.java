package com.fms.app.reservation.models.dto;



public class VisitorsDTO {
	private Integer visitorsId;
	private String firstName;
	private String lastName;
	private String email;
	private Integer createdBy;
	private String phoneNum;
	private Integer blId;
	private Integer flId;
	private Integer picture;
	private String comments;
	private String dateStart;
	private String dateEnd;
	private byte[] visitorPhoto;
	private String blBlCode;
	private String flFlCode;
	private String userUserName;
	private String blBlName;
	private String flFlName;
	private String flFlcode;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
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
	public Integer getPicture() {
		return picture;
	}
	public void setPicture(Integer picture) {
		this.picture = picture;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	public byte[] getVisitorPhoto() {
		return visitorPhoto;
	}
	public void setVisitorPhoto(byte[] visitorPhoto) {
		this.visitorPhoto = visitorPhoto;
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
	public String getUserUserName() {
		return userUserName;
	}
	public void setUserUserName(String userUserName) {
		this.userUserName = userUserName;
	}
	public Integer getVisitorsId() {
		return visitorsId;
	}
	public void setVisitorsId(Integer visitorsId) {
		this.visitorsId = visitorsId;
	}
	public String getBlBlName() {
		return blBlName;
	}
	public void setBlBlName(String blBlName) {
		this.blBlName = blBlName;
	}
	public String getFlFlName() {
		return flFlName;
	}
	public void setFlFlName(String flFlName) {
		this.flFlName = flFlName;
	}
	public String getFlFlcode() {
		return flFlcode;
	}
	public void setFlFlcode(String flFlcode) {
		this.flFlcode = flFlcode;
	}
	
}
