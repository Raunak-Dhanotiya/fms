package com.fms.app.securitygroup.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "security_group")
@Table(name = "security_group")
public class SecurityGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="security_group_id")
	private Integer securityGroupId;
	
	@Column(name="group_name")
	private String groupName;
	
	@Column(name="description")
	private String description;

	public SecurityGroup() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param securityGroupId
	 * @param groupName
	 * @param description
	 */
	public SecurityGroup(Integer securityGroupId, String groupName, String description) {
		super();
		this.securityGroupId = securityGroupId;
		this.groupName = groupName;
		this.description = description;
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
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

}
