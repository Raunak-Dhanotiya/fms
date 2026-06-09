package com.fms.app.helpdesk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.RequestOtherCost;
import com.fms.app.helpdesk.repository.RequestOtherCostRepository;
import com.fms.app.security.AuthorizeUserInfo;

@Service
public class RequestOtherCostService {

	@Autowired
	RequestOtherCostRepository requestOtherCostRepository;

	@Autowired
	AuthorizeUserInfo userInfo;

	public RequestOtherCost saveOrUpdate(RequestOtherCost dataRecord) {
		int enteredBy = userInfo.getUserIfo().getUserId();
		dataRecord.setEnteredBy(enteredBy);

		return this.requestOtherCostRepository.save(dataRecord);
	}

	public List<RequestOtherCost> getAllRequestOtherCost()
	{
		return this.requestOtherCostRepository.findAll();
	}

	public List<RequestOtherCost> getByRequestId(int requestId) {
		return this.requestOtherCostRepository.findByRequestId(requestId);
	}

	public void deleteByRequestOtherCostId(RequestOtherCost requestOtherCostData) {
		this.requestOtherCostRepository.delete(requestOtherCostData);
	}

	public RequestOtherCost getById(int id) {
		return this.requestOtherCostRepository.findById(id).orElse(null);
	}

	public boolean checkCostTypeExist(int requestId, int costTypeId) {
		
		return this.requestOtherCostRepository.existsByRequestIdAndCostTypeId(requestId,costTypeId);
	}

}
