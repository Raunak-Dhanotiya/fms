package com.fms.app.employee.models.dto;

import java.util.Date;

public class EmployeeDetails {

	private String initials;
	private String firstName;
	private String lastName;
	private String maidenName;
	private String aliasName;
	private String email;
	private Integer emstdId;
	private String emStatus;
	private String idNumber;
	private Date birthDate;
	private String gender;
	private String compName;
	private Date dateJoin;
	private Date dateLeave;
	private Integer emId;
	private Integer emPhoto;
	private Integer lineMngr;
	private Integer blId;
	private Integer flId;
	private Integer rmId;
	private byte[] emPhotoMobile;
	private String workType;
	private Integer divId;
	private Integer depId;
	private String emCode;
	private String emStdEmStd;
	private String divisionDivCode;
	private String departmentDepCode;
	private String blBlCode;
	private String flFlCode;
	private String rmRmCode;
	private Integer subDepId;
	private String subDepartmentSubDepCode;
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
	 * @return the initials
	 */
	public String getInitials() {
		return initials;
	}

	/**
	 * @param initials the initials to set
	 */
	public void setInitials(String initials) {
		this.initials = initials;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the maidenName
	 */
	public String getMaidenName() {
		return maidenName;
	}

	/**
	 * @param maidenName the maidenName to set
	 */
	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

	/**
	 * @return the aliasName
	 */
	public String getAliasName() {
		return aliasName;
	}

	/**
	 * @param aliasName the aliasName to set
	 */
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the emStatus
	 */
	public String getEmStatus() {
		return emStatus;
	}

	/**
	 * @param emStatus the emStatus to set
	 */
	public void setEmStatus(String emStatus) {
		this.emStatus = emStatus;
	}

	/**
	 * @return the idNumber
	 */
	public String getIdNumber() {
		return idNumber;
	}

	/**
	 * @param idNumber the idNumber to set
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the compName
	 */
	public String getCompName() {
		return compName;
	}

	/**
	 * @param compName the compName to set
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}

	/**
	 * @return the dateJoin
	 */
	public Date getDateJoin() {
		return dateJoin;
	}

	/**
	 * @return the lineMngr
	 */
	public Integer getLineMngr() {
		return lineMngr;
	}

	/**
	 * @param lineMngr the lineMngr to set
	 */
	public void setLineMngr(Integer lineMngr) {
		this.lineMngr = lineMngr;
	}

	/**
	 * @param dateJoin the dateJoin to set
	 */
	public void setDateJoin(Date dateJoin) {
		this.dateJoin = dateJoin;
	}

	/**
	 * @return the dateLeave
	 */
	public Date getDateLeave() {
		return dateLeave;
	}

	/**
	 * @param dateLeave the dateLeave to set
	 */
	public void setDateLeave(Date dateLeave) {
		this.dateLeave = dateLeave;
	}

	/**
	 * @return the emPhoto
	 */
	public Integer getEmPhoto() {
		return emPhoto;
	}

	/**
	 * @param emPhoto the emPhoto to set
	 */
	public void setEmPhoto(Integer emPhoto) {
		this.emPhoto = emPhoto;
	}

	public EmployeeDetails() {
		super();
	}

	public byte[] getEmPhotoMobile() {
		return emPhotoMobile;
	}

	public void setEmPhotoMobile(byte[] emPhotoMobile) {
		this.emPhotoMobile = emPhotoMobile;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public Integer getEmstdId() {
		return emstdId;
	}

	public void setEmstdId(Integer emstdId) {
		this.emstdId = emstdId;
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

	public Integer getDivId() {
		return divId;
	}

	public void setDivId(Integer divId) {
		this.divId = divId;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public String getEmCode() {
		return emCode;
	}

	public void setEmCode(String emCode) {
		this.emCode = emCode;
	}

	public String getEmStdEmStd() {
		return emStdEmStd;
	}

	public void setEmStdEmStd(String emStdEmStd) {
		this.emStdEmStd = emStdEmStd;
	}

	public String getDivisionDivCode() {
		return divisionDivCode;
	}

	public void setDivisionDivCode(String divisionDivCode) {
		this.divisionDivCode = divisionDivCode;
	}

	public String getDepartmentDepCode() {
		return departmentDepCode;
	}

	public void setDepartmentDepCode(String departmentDepCode) {
		this.departmentDepCode = departmentDepCode;
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

	public Integer getSubDepId() {
		return subDepId;
	}

	public void setSubDepId(Integer subDepId) {
		this.subDepId = subDepId;
	}

	public String getSubDepartmentSubDepCode() {
		return subDepartmentSubDepCode;
	}

	public void setSubDepartmentSubDepCode(String subDepartmentSubDepCode) {
		this.subDepartmentSubDepCode = subDepartmentSubDepCode;
	}
	
}
