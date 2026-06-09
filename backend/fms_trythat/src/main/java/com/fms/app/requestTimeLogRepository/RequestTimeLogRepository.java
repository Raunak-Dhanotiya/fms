package com.fms.app.requestTimeLogRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.requestTimeLog.model.RequestTimeLog;

public interface RequestTimeLogRepository extends JpaRepository<RequestTimeLog, Integer> {

	List<RequestTimeLog> findByRequestId(int requestId);

}
