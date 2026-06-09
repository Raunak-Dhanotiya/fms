package com.fms.app.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.helpdesk.models.RequestLog;

public interface RequestLogRepository extends JpaRepository<RequestLog,Integer> {

	List<RequestLog> findAllByRequestId(int requestId);

}
