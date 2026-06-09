package com.fms.app.userModels;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.app.employee.models.Employee;
import com.fms.app.helpdesk.models.CraftsPerson;

@Entity
@Table(name = "fm_users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "user_full_name")
	private String userFullName;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "em_id", nullable = true)
	private Integer emId;

	@Column(name = "user_status")
	private String userStatus;

	@Column(name = "user_role_id")
	private Integer userRoleId;

	@Column(name = "user_pwd")
	private String userPwd;

	@Column(name = "date_pwd_changed")
	private Date datePwdChanged;

	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "failed_login_attempts")
	private Integer failedLoginAttempts;

	@Column(name = "technician_id")
	private Integer technicianId;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "em_id", referencedColumnName = "em_id", insertable = false, updatable = false)
	private Employee emEmployee;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "technician_id", referencedColumnName = "cf_id", insertable = false, updatable = false)
	private CraftsPerson technician;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_role_id", referencedColumnName = "user_role_id", insertable = false, updatable = false)
	private UserRoles userroles;

	public User() {
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getEmId() {
		return emId;
	}

	public void setEmId(Integer emId) {
		this.emId = emId;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public Date getDatePwdChanged() {
		return datePwdChanged;
	}

	public void setDatePwdChanged(Date datePwdChanged) {
		this.datePwdChanged = datePwdChanged;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void setFailedLoginAttempts(Integer failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	public Integer getTechnicianId() {
		return technicianId;
	}

	public void setTechnicianId(Integer technicianId) {
		this.technicianId = technicianId;
	}

	public Employee getEmEmployee() {
		return emEmployee;
	}

	public void setEmEmployee(Employee emEmployee) {
		this.emEmployee = emEmployee;
	}

	public CraftsPerson getTechnician() {
		return technician;
	}

	public void setTechnician(CraftsPerson technician) {
		this.technician = technician;
	}

	public UserRoles getUserroles() {
		return userroles;
	}

	public void setUserroles(UserRoles userroles) {
		this.userroles = userroles;
	}
}
