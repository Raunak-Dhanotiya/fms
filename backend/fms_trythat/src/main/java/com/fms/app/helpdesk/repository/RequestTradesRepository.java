package com.fms.app.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.helpdesk.models.RequestTrades;

public interface RequestTradesRepository extends JpaRepository<RequestTrades,Integer>{

	List<RequestTrades> findAllByRequestId(int requestId);
	
	RequestTrades findByRequestIdAndTradeId(int requestId, int tradeId);

	boolean existsByRequestIdAndTradeId(int requestId, int tradeId);

}
