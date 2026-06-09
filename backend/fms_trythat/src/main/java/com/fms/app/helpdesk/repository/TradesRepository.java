package com.fms.app.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.helpdesk.models.Trades;

public interface TradesRepository extends JpaRepository<Trades, Integer>,JpaSpecificationExecutor<Trades>{

	boolean existsByTradeId(int tradeId);
	
	boolean existsByTradeCode(String tradeCode);

}
