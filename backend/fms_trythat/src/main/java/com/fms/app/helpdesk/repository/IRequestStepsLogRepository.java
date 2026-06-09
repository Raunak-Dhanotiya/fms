package com.fms.app.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.helpdesk.models.RequestStepsLog;

public interface IRequestStepsLogRepository extends JpaRepository<RequestStepsLog, Integer> {

	void deleteAllByRequestId(Integer requestId);

}
