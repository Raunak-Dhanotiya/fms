package com.fms.app.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.helpdesk.models.RequestParts;

public interface RequestPartsRepository extends JpaRepository<RequestParts,Integer> {

	List<RequestParts> findByRequestId(int requestId);
	
	RequestParts findByRequestIdAndPartId(int requestId, int partId);

	boolean existsByRequestIdAndPartId(int requestId, int partId);

}
