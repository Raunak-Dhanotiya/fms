package com.fms.app.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fms.app.helpdesk.models.RequestTools;

public interface RequestToolsRepository extends JpaRepository<RequestTools, Integer>
{
	List<RequestTools> findByRequestId(int requestId);
	
	boolean existsByRequestIdAndToolId(int requestId, int toolId);

	RequestTools findByRequestIdAndToolId(int requestId,int toolId);
	
}
