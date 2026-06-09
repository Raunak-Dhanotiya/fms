package com.fms.app.userRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.fms.app.userModels.UserRoles;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRoles, Integer>,JpaSpecificationExecutor<UserRoles> {

	public UserRoles findByRoleName(int userRoleId);

	public void deleteByRoleName(int userRoleId);

	boolean existsByUserRoleId(int userRoleId);

}
