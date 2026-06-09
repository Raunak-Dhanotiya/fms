package com.fms.app.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.helpdesk.models.RequestTechnicianLog;

public interface RequestTechnicianLogRepository extends JpaRepository<RequestTechnicianLog, Integer> {

	List<RequestTechnicianLog> findByRequestId(int requestId);

}
