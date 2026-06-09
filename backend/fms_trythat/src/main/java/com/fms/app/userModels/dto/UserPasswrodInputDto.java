package com.fms.app.userModels.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserPasswrodInputDto {

	private Integer userId;
	private String userName;
	private String userPwd;
	private String userNewPwd;

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the userNewPwd
	 */
	public String getUserNewPwd() {
		return userNewPwd;
	}

	/**
	 * @param userNewPwd the userNewPwd to set
	 */
	public void setUserNewPwd(String userNewPwd) {
		this.userNewPwd = userNewPwd;
	}

	private String userDatePwdChanged;

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
	 * @return the userPwd
	 */
	public String getUserPwd() {
		return userPwd;
	}

	/**
	 * @param userPwd the userPwd to set
	 */
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}



	public Date convertStringToDate(String userDatePwdChanged) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return formatter.parse(userDatePwdChanged);
		} catch (ParseException e) {
			return new Date();
		}

	}

	public UserPasswrodInputDto() {
		super();
	}

	/**
	 * @return the userDatePwdChanged
	 */
	public String getUserDatePwdChanged() {
		return userDatePwdChanged;
	}

	/**
	 * @param userDatePwdChanged the userDatePwdChanged to set
	 */
	public void setUserDatePwdChanged(String userDatePwdChanged) {
		this.userDatePwdChanged = userDatePwdChanged;
	}

}
