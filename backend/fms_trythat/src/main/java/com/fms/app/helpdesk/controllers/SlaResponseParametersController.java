package com.fms.app.helpdesk.controllers;

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
import com.fms.app.helpdesk.models.SlaResponseParameters;
import com.fms.app.helpdesk.services.SlaResponseParametersServices;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class SlaResponseParametersController {

	@Autowired
	SlaResponseParametersServices slaResponseParametersServices;
	
	private static final Logger logger = LogManager.getLogger(SlaResponseParametersController.class);

	@RequestMapping(value = "/slaResponseParameter/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveSlaResponseParameter(@RequestBody SlaResponseParameters data) {
		try {
			slaResponseParametersServices.saveOrUpdate(data);
			if(data.getSlaResponseParametersId() > 0 ) {
				slaResponseParametersServices.checkIsAutoIssueAutoApprovalAndDeleteExistingSteps(data);
			}
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SlaResponseParametersController.saveSlaResponseParameter: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/slaResponseParameter/getList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllslaResponseParameters() {
		try {
			List<SlaResponseParameters> slaResponseParametersList = this.slaResponseParametersServices.getAll();
			return new ResponseEntity<>(slaResponseParametersList, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SlaResponseParametersController.getAllslaResponseParameters: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/slaResponseParameter/getById/{slaResponseParametersId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getToolById(@PathVariable("slaResponseParametersId") int slaResponseParametersId) {
		try {
			SlaResponseParameters record = this.slaResponseParametersServices.getById(slaResponseParametersId);
			return new ResponseEntity<>(record, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SlaResponseParametersController.getToolById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/slaResponseParameter/search/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getPriorityBySearch(@PathVariable("id") String id) {

		try {
			List<String> finalResult = this.slaResponseParametersServices.getPriorities(id);
			return new ResponseEntity<Object>(finalResult, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SlaResponseParametersController.getPriorityBySearch: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/slaResponseParameter/getBySlaRequestId/{slaRequestParametersId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllSlaResponseParametersBySlaRequestId(@PathVariable("slaRequestParametersId") int slaRequestParametersId) {
		try {
			List<SlaResponseParameters> data = this.slaResponseParametersServices.getAllSlaResponseParametersBySlaRequestId(slaRequestParametersId);
			return new ResponseEntity<Object>(data, HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SlaResponseParametersController.getAllSlaResponseParametersBySlaRequestId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/slaResponseParameter/updateAllPriorities", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> updateAllPriorities(@RequestBody List<SlaResponseParameters> data) {
		try {
			if(data.size() > 0 && data != null) {
				for (SlaResponseParameters eachRecord: data) {
					this.slaResponseParametersServices.saveOrUpdate(eachRecord);
				}
			}
			return new ResponseEntity<Object>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SlaResponseParametersController.updateAllPriorities: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}		
	}
	

}
