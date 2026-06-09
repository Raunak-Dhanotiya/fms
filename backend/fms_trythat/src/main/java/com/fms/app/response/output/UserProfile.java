package com.fms.app.response.output;


public class UserProfile {

	private String userName;
	private String userRole;
	private String employeeEmail;
	private String name;
	private String employeeGender;
	private String employeeEmCode;
	private int employeeEmStdId;
	private String blName;
	private String flName;
	private String rmName;
	private String employeePhonePersonal;
	private String employeePhoneWork;
	private String employeeFaxNum;
	private String employeeIdNumber;
	private String initials;
	private byte[] emPhoto;
	private int blId;
	private int flId;
	private int rmId;
	private Integer employeeEmId;
	private Integer userId;
	private String employeeEmStdEmStd;


	public int getBlId() {
		return blId;
	}


	public void setBlId(int blId) {
		this.blId = blId;
	}


	public int getFlId() {
		return flId;
	}


	public void setFlId(int flId) {
		this.flId = flId;
	}


	public int getRmId() {
		return rmId;
	}


	public void setRmId(int rmId) {
		this.rmId = rmId;
	}


	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}


	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}


	/**
	 * @return the employeeEmail
	 */
	public String getEmployeeEmail() {
		return employeeEmail;
	}


	/**
	 * @param employeeEmail the employeeEmail to set
	 */
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the employeeGender
	 */
	public String getEmployeeGender() {
		return employeeGender;
	}


	/**
	 * @param employeeGender the employeeGender to set
	 */
	public void setEmployeeGender(String employeeGender) {
		this.employeeGender = employeeGender;
	}

	/**
	 * @return the employeeEmStd
	 */
	public int getEmployeeEmStdId() {
		return employeeEmStdId;
	}


	/**
	 * @param employeeEmStd the employeeEmStd to set
	 */
	public void setEmployeeEmStdId(int employeeEmStdId) {
		this.employeeEmStdId = employeeEmStdId;
	}


	/**
	 * @return the blName
	 */
	public String getBlName() {
		return blName;
	}


	/**
	 * @param blName the blName to set
	 */
	public void setBlName(String blName) {
		this.blName = blName;
	}


	/**
	 * @return the flName
	 */
	public String getFlName() {
		return flName;
	}


	/**
	 * @param flName the flName to set
	 */
	public void setFlName(String flName) {
		this.flName = flName;
	}


	/**
	 * @return the rmName
	 */
	public String getRmName() {
		return rmName;
	}


	/**
	 * @param rmName the rmName to set
	 */
	public void setRmName(String rmName) {
		this.rmName = rmName;
	}


	/**
	 * @return the employeePhonePersonal
	 */
	public String getEmployeePhonePersonal() {
		return employeePhonePersonal;
	}


	/**
	 * @param employeePhonePersonal the employeePhonePersonal to set
	 */
	public void setEmployeePhonePersonal(String employeePhonePersonal) {
		this.employeePhonePersonal = employeePhonePersonal;
	}


	/**
	 * @return the employeePhoneWork
	 */
	public String getEmployeePhoneWork() {
		return employeePhoneWork;
	}


	/**
	 * @param employeePhoneWork the employeePhoneWork to set
	 */
	public void setEmployeePhoneWork(String employeePhoneWork) {
		this.employeePhoneWork = employeePhoneWork;
	}


	/**
	 * @return the employeeFaxNum
	 */
	public String getEmployeeFaxNum() {
		return employeeFaxNum;
	}


	/**
	 * @param employeeFaxNum the employeeFaxNum to set
	 */
	public void setEmployeeFaxNum(String employeeFaxNum) {
		this.employeeFaxNum = employeeFaxNum;
	}


	/**
	 * @return the employeeIdNumber
	 */
	public String getEmployeeIdNumber() {
		return employeeIdNumber;
	}


	/**
	 * @param employeeIdNumber the employeeIdNumber to set
	 */
	public void setEmployeeIdNumber(String employeeIdNumber) {
		this.employeeIdNumber = employeeIdNumber;
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

	public UserProfile() {
		super();
	}

	/**
	 * @return the emPhoto
	 */
	public byte[] getEmPhoto() {
		return emPhoto;
	}

	/**
	 * @param emPhoto the emPhoto to set
	 */
	public void setEmPhoto(byte[] emPhoto) {
		this.emPhoto = emPhoto;
	}


	public String getEmployeeEmCode() {
		return employeeEmCode;
	}


	public void setEmployeeEmCode(String employeeEmCode) {
		this.employeeEmCode = employeeEmCode;
	}


	public Integer getEmployeeEmId() {
		return employeeEmId;
	}


	public void setEmployeeEmId(Integer employeeEmId) {
		this.employeeEmId = employeeEmId;
	}


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public String getEmployeeEmStdEmStd() {
		return employeeEmStdEmStd;
	}


	public void setEmployeeEmStdEmStd(String employeeEmStdEmStd) {
		this.employeeEmStdEmStd = employeeEmStdEmStd;
	}
	
}
