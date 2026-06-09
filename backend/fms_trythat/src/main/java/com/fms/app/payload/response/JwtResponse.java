package com.fms.app.payload.response;

import java.util.List;

public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private String username;
	private Integer userId;
	private Integer emId;
	private Integer userRoleId;
	private Integer technicianId;
	/**
	 * @param token
	 * @param type
	 * @param username
	 * @param userId
	 * @param emId
	 * @param roles
	 * @param technicianId
	 */
	public JwtResponse(String token, String type, String username, Integer userId, Integer emId,Integer userRoleId,
			Integer technicianId) {
		super();
		this.token = token;
		this.type = type;
		this.username = username;
		this.userId = userId;
		this.emId = emId;
		this.userRoleId = userRoleId;
		this.technicianId = technicianId;
	}
	/**
	 * 
	 */
	public JwtResponse() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
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
	
	public Integer getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
	/**
	 * @return the technicianId
	 */
	public Integer getTechnicianId() {
		return technicianId;
	}
	/**
	 * @param technicianId the technicianId to set
	 */
	public void setTechnicianId(Integer technicianId) {
		this.technicianId = technicianId;
	}
		
}
