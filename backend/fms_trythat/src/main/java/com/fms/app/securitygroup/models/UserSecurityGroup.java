package com.fms.app.securitygroup.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "user_security_group")
@Table(name = "user_security_group")
public class UserSecurityGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_security_group_id")
	private Integer userSecurityGroupId;
     
	@Column(name="security_group_id")
	private Integer securityGroupId;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="user_role_id")
	private Integer userRoleId;

	public UserSecurityGroup() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param userSecurityGroupId
	 * @param securityGroupId
	 * @param userId
	 * @param userRoleId
	 */
	public UserSecurityGroup(Integer userSecurityGroupId, Integer securityGroupId, Integer userId, Integer userRoleId) {
		super();
		this.userSecurityGroupId = userSecurityGroupId;
		this.securityGroupId = securityGroupId;
		this.userId = userId;
		this.userRoleId = userRoleId;
	}

	/**
	 * @return the userSecurityGroupId
	 */
	public Integer getUserSecurityGroupId() {
		return userSecurityGroupId;
	}

	/**
	 * @param userSecurityGroupId the userSecurityGroupId to set
	 */
	public void setUserSecurityGroupId(Integer userSecurityGroupId) {
		this.userSecurityGroupId = userSecurityGroupId;
	}

	/**
	 * @return the securityGroupId
	 */
	public Integer getSecurityGroupId() {
		return securityGroupId;
	}

	/**
	 * @param securityGroupId the securityGroupId to set
	 */
	public void setSecurityGroupId(Integer securityGroupId) {
		this.securityGroupId = securityGroupId;
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
	 * @return the userRoleId
	 */
	public Integer getUserRoleId() {
		return userRoleId;
	}

	/**
	 * @param userRoleId the userRoleId to set
	 */
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

}
