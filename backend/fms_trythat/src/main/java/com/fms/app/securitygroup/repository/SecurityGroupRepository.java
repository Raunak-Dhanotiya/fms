package com.fms.app.securitygroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.securitygroup.models.SecurityGroup;

public interface SecurityGroupRepository extends JpaRepository<SecurityGroup, Integer>,JpaSpecificationExecutor<SecurityGroup> {

	boolean existsByGroupName(String groupName);

}
