package com.fms.app.requestTimeLog.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.requestTimeLog.model.RequestTimeLog;
import com.fms.app.requestTimeLogService.RequestTimeLogService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class RequestTimeLogControllers {
	@Autowired
	RequestTimeLogService requestTimeLogService;
	
	private static final Logger logger = LogManager.getLogger(RequestTimeLogControllers.class);

	@RequestMapping(value = "saveOrUpdate/requestTimeLog", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveRequestTimeLog(@RequestBody RequestTimeLog dataRecord) {
		try {
			RequestTimeLog data = this.requestTimeLogService.saveOrUpdate(dataRecord);

			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestTimeLogControllers.saveRequestTimeLog: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "getById/RequestTimeLog/{requestId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getRequestTimeLogById(@PathVariable("requestId") int requestId) {
		try {
			List<RequestTimeLog> data =  this.requestTimeLogService.getByRequestTimeLogById(requestId);
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestTimeLogControllers.getRequestTimeLogById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
