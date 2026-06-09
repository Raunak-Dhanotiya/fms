package com.fms.app.helpdesk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.RequestTrades;
import com.fms.app.helpdesk.repository.RequestTradesRepository;
import com.fms.app.security.AuthorizeUserInfo;

@Service
public class RequestTradesServices {
	
	@Autowired
	RequestTradesRepository requestTradesRepo;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	public void saveOrUpdate(RequestTrades requestTrade) {
		
		if(requestTrade.getAddedBy() == null || requestTrade.getAddedBy() == 0) {
			Integer userId = this.userInfo.getUserIfo().getUserId();
			requestTrade.setAddedBy(userId);
		}
		this.requestTradesRepo.save(requestTrade);
	}
	
	public RequestTrades getById(int requestTradeId) {
		return this.requestTradesRepo.findById(requestTradeId).orElse(null);
	}
	
	public List<RequestTrades> getAllByRequestId(int requestId) {
		return this.requestTradesRepo.findAllByRequestId(requestId);
	}
	
	public void deleteById(int requestTradeId) {
		this.requestTradesRepo.deleteById(requestTradeId);
	}
	
	public boolean checkTradeExists(int requestId, int tradeId) {
		return this.requestTradesRepo.existsByRequestIdAndTradeId(requestId,tradeId);
	}
	
	public RequestTrades getByRequestIdAndTradeId(int requestId, int tradeId) {
		return this.requestTradesRepo.findByRequestIdAndTradeId(requestId,tradeId);
	}

}
