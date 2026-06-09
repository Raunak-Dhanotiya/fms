package com.fms.app.helpdesk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fms.app.helpdesk.models.RequestLog;
import com.fms.app.helpdesk.repository.RequestLogRepository;

@Service
public class RequestLogServices {
	
	@Autowired
	RequestLogRepository requestLogRepository;
	
	public RequestLog saveOrUpdate(RequestLog data) {
		{
			return this.requestLogRepository.save(data);
		}
	}

	public List<RequestLog> getAllRequestLogByRequestId(int requestId) {
		
		return this.requestLogRepository.findAllByRequestId(requestId);
	}


}
