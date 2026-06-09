package com.fms.app.userServices;

import java.util.List;

import com.fms.app.userModels.UserRoles;

public interface IUserRolesService {

	public List<UserRoles> getUserRoles();

	public UserRoles getUserRoles(int userRoleId);

	public void saveUserRole(UserRoles userRole);

	public void delete(int userRoleId);

	public void delete(UserRoles userRole);

	public boolean checkRoleExist(int userRoleId);

	public void createDefaultRole(String roleName);
}
