package com.fms.app.helpdesk.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fms.app.helpdesk.models.RequestTools;
import com.fms.app.helpdesk.repository.RequestToolsRepository;
import com.fms.app.security.AuthorizeUserInfo;

@Service
public class RequestToolsService
{
	@Autowired
	RequestToolsRepository requestToolsRepository;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	public RequestTools saveOrUpdate(RequestTools dataRecord) {
		if(dataRecord.getAddedBy() == null || dataRecord.getAddedBy() == 0) {
			int addedBy = userInfo.getUserIfo().getUserId();
			dataRecord.setAddedBy(addedBy);
		}
		
		return this.requestToolsRepository.save(dataRecord);
	}

	public List<RequestTools> getAllRequestTools()
	{
		return this.requestToolsRepository.findAll();
	}
	
	public List<RequestTools> getByRequestId(int requestId) {
		return this.requestToolsRepository.findByRequestId(requestId);
	}
	
	public RequestTools getById(int id) {
		return this.requestToolsRepository.findById(id).orElse(null);
	}
	
	public void deleteByRequestId(RequestTools RequestToolsData) {
		this.requestToolsRepository.delete(RequestToolsData);
	}
	
	public boolean checkIsToolsExist(int requestId, int toolId) {
		
		return this.requestToolsRepository.existsByRequestIdAndToolId(requestId,toolId);
	}
	
	public RequestTools getByRequestAndToolId(int requestId,int toolId) {
		return this.requestToolsRepository.findByRequestIdAndToolId(requestId,toolId);
	}

}
