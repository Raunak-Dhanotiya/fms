package com.fms.app.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.helpdesk.models.ToolType;

public interface ToolTypeRepository extends JpaRepository<ToolType,Integer>,JpaSpecificationExecutor<ToolType> {

	boolean existsByToolType(String toolType);

	boolean existsByToolTypeId(int toolTypeId);
	
}
