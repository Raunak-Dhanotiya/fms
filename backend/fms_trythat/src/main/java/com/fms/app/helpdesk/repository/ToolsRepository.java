package com.fms.app.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.helpdesk.models.Tools;

public interface ToolsRepository extends JpaRepository<Tools,Integer>,JpaSpecificationExecutor<Tools> {

	boolean existsByTool(String tool);

	boolean existsByToolsId(int toolsId);
}
