package com.fms.app.requestTimeLogService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.requestTimeLog.model.RequestTimeLog;
import com.fms.app.requestTimeLogRepository.RequestTimeLogRepository;

@Service
public class RequestTimeLogService {

	@Autowired
	RequestTimeLogRepository requestTimeLogRepo;

	public RequestTimeLog saveOrUpdate(RequestTimeLog data) {
		return this.requestTimeLogRepo.save(data);
	}
	public List<RequestTimeLog> getByRequestTimeLogById(int requestId) {
		List<RequestTimeLog> data =  this.requestTimeLogRepo.findByRequestId(requestId);
		if(data != null) {
			return data;
		}else {
			 return new ArrayList<RequestTimeLog>();
		}
	}

}
