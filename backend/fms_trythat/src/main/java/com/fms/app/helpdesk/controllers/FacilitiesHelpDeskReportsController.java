package com.fms.app.helpdesk.controllers;

import java.util.List;
import java.util.Map;

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

import com.fms.app.helpdesk.models.dto.HelpDeskReportsFilterDTO;
import com.fms.app.helpdesk.models.dto.WrDto;
import com.fms.app.helpdesk.models.dto.WrFilterDTO;
import com.fms.app.helpdesk.services.FacilitiesHelpDeskReportsService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class FacilitiesHelpDeskReportsController {

	@Autowired
	FacilitiesHelpDeskReportsService helpdeskReportsService;
	
	
	private static final Logger logger = LogManager.getLogger(FacilitiesHelpDeskReportsController.class);
	
	@RequestMapping(value = "/helpdesk/resportsByGroup", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getReports(@RequestBody HelpDeskReportsFilterDTO filterData) {
		
		try {
			List<Map<String, Object>> data = this.helpdeskReportsService.getReportsByGroup(filterData);
			
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in FacilitiesHelpDeskReportsController.getReports: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/helpdesk/getRequestTechnicianOrTeamReport", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getRequestTechnicianOrTeamReport(@RequestBody WrFilterDTO filterData) {
		
		try {
			List<WrDto> data = this.helpdeskReportsService.getRequestTechnicianOrTeamReport(filterData);
			
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in FacilitiesHelpDeskReportsController.getRequestTechnicianOrTeamReport: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
