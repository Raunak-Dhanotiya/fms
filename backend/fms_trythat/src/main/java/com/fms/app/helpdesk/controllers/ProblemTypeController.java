package com.fms.app.helpdesk.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.helpdesk.models.ProblemType;
import com.fms.app.helpdesk.models.dto.ProblemTypeDto;
import com.fms.app.helpdesk.services.ProblemTypeServices;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class ProblemTypeController {
	@Autowired
	ProblemTypeServices problemTypeServices;
	
	private static final Logger logger = LogManager.getLogger(ProblemTypeController.class);
	
	@RequestMapping(value = "/problemType/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveProblemType(@RequestBody ProblemType data) {
		
		try {
			this.problemTypeServices.saveOrUpdate(data);
			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ProblemTypeController.saveProblemType: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/problemType/getList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllproblemTypes() {

		try {
			List<ProblemTypeDto> probTypeDtos = this.problemTypeServices.getProblemTypeHierarchy();
			return new ResponseEntity<>(probTypeDtos, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ProblemTypeController.getAllproblemTypes: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
