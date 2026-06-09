package com.fms.app.helpdesk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.RequestTechnicianLog;
import com.fms.app.helpdesk.repository.RequestTechnicianLogRepository;

@Service
public class RequestTechnicianLogService {

	@Autowired
	RequestTechnicianLogRepository requestTechnicianLogRepository;

	
	public RequestTechnicianLog saveOrUpdate(RequestTechnicianLog dataRecord) {
		return this.requestTechnicianLogRepository.save(dataRecord);
	}

	public List<RequestTechnicianLog> getAllRequestTechnicianLog()
	{
		return this.requestTechnicianLogRepository.findAll();
	}

	public List<RequestTechnicianLog> getByRequestId(int requestId) {
		return this.requestTechnicianLogRepository.findByRequestId(requestId);

	}
	
	public void deleteByRequestId(RequestTechnicianLog RequestTechnicianData) {
		this.requestTechnicianLogRepository.delete(RequestTechnicianData);
	}

	public RequestTechnicianLog getById(int id) {
		return this.requestTechnicianLogRepository.findById(id).orElse(null);
	}

}
