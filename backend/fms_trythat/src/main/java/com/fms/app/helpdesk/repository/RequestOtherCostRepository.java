package com.fms.app.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.helpdesk.models.RequestOtherCost;

public interface RequestOtherCostRepository extends JpaRepository<RequestOtherCost, Integer> {

	List<RequestOtherCost> findByRequestId(int requestId);

	boolean existsByRequestIdAndCostTypeId(int requestId, int costTypeId);

}
