package com.fms.app.securitygroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fms.app.securitygroup.models.UserSecurityGroup;

public interface UserSecurityGroupRepository extends JpaRepository<UserSecurityGroup, Integer> {

}
