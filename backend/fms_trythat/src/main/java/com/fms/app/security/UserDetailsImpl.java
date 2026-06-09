package com.fms.app.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fms.app.common.services.IEnumService;
import com.fms.app.userModels.User;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Integer emId;
	
	private Integer userId;
	
	private String userName;
	
	private boolean isUserActive;
	
	private Integer technicianId;
	
	private Integer userRoleId;

	@JsonIgnore
	private String userPassword;
	
	@Autowired
	IEnumService enums;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Integer emId, Integer userId, String userName,String userPassword,
			Collection<? extends GrantedAuthority> authorities,Integer technicianId,Integer userRoleId) {
		super();
		this.emId = emId;
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.authorities = authorities;
		this.technicianId = technicianId;
		this.setUserRoleId(userRoleId);
	}
	
	public UserDetailsImpl(Integer emId, Integer userId, String userName,String userPassword,
			Collection<? extends GrantedAuthority> authorities,String status,Integer technicianId,Integer userRoleId) {
		super();
		this.emId = emId;
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.authorities = authorities;
		setUserActive(status.equalsIgnoreCase("Active"));
		this.technicianId = technicianId;
		this.setUserRoleId(userRoleId);
	}

	public static UserDetailsImpl build(User user,final String userStatus) {
		// List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getUserroles().getRoleName()));
		
		return new UserDetailsImpl(
				user.getEmEmployee() != null ? user.getEmEmployee().getEmId() : 0, 
				user.getUserId(),
				user.getUserName(),
				user.getUserPwd(), 
				authorities,
				userStatus,
				user.getTechnicianId(),
				user.getUserRoleId());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Integer getEmId() {
		return emId;
	}

	public void setEmId(Integer emId) {
		this.emId = emId;
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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.isUserActive;
	}
	
	

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(userName, user.userName);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return userPassword;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	public boolean isUserActive() {
		return isUserActive;
	}

	public void setUserActive(boolean isUserActive) {
		this.isUserActive = isUserActive;
	}

	public Integer getTechnicianId() {
		return technicianId;
	}

	public void setTechnicianId(Integer technicianId) {
		this.technicianId = technicianId;
	}

	public Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
	
}
