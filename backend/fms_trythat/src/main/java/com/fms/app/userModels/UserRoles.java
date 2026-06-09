package com.fms.app.userModels;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRoles implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_role_id")
	private Integer userRoleId;
	
	@Column(name = "role_name", updatable = false, nullable = false)
	private String roleName;

	@Column(name = "role_title")
	private String roleTitle;

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

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the roleTitle
	 */
	public String getRoleTitle() {
		return roleTitle;
	}

	/**
	 * @param roleTitle the roleTitle to set
	 */
	public void setRoleTitle(String roleTitle) {
		this.roleTitle = roleTitle;
	}


	public UserRoles() {
		super();
	}

	public UserRoles( Integer userRoleId, String roleName, String roleTitle) {
		super();
		this.roleName = roleName;
		this.roleTitle = roleTitle;
		this.userRoleId = userRoleId;
	}

}
