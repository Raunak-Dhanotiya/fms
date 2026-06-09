package com.fms.app.ppm.controllers;

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

import com.fms.app.ppm.models.PlanScheduleDate;
import com.fms.app.ppm.services.PlanScheduleDateServices;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class PlanScheduleDateController {
	
	@Autowired
	PlanScheduleDateServices planScheduleDateSrv;
	private static final Logger logger = LogManager.getLogger(PlanScheduleDateController.class);
	
	@RequestMapping(value = "/planScheduleDate/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> savePlanScheduleDate(@RequestBody PlanScheduleDate planScheduleDate) {
		
		try  {
			this.planScheduleDateSrv.save(planScheduleDate);
			return new ResponseEntity<>(planScheduleDate, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanScheduleDateController.savePlanScheduleDate: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}


}
