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
import com.fms.app.helpdesk.models.SlaRequestSteps;
import com.fms.app.helpdesk.services.SlaRequestStepsServices;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class SlaRequestStepsController {
	@Autowired
	SlaRequestStepsServices slaRequestStepsServices;
	
	private static final Logger logger = LogManager.getLogger(SlaRequestStepsController.class);
	
	@RequestMapping(value = "/slaRequestStep/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveslaRequestStep(@RequestBody SlaRequestSteps data) {
		boolean checkIsExist =  slaRequestStepsServices.checkIsExist(data);
		SlaRequestSteps savedData = new SlaRequestSteps();
		if (!checkIsExist) {
			try {
				savedData = slaRequestStepsServices.saveOrUpdate(data);
			} catch (Exception e) {
				String exceptionCause = CommonUtil.getExceptionCause(e);
				String stacktrace = CommonUtil.getStakeTrace(e);
				logger.error("Exception in SlaRequestStepsController.saveslaRequestStep: "+stacktrace,e);
				return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
			}

			return new ResponseEntity<>(savedData, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(
					new ResponseUtil(String.valueOf(CommonConstant.UNABLE_TO_PROCESS_SLA_STEPS), HttpStatus.PARTIAL_CONTENT.value()),
					HttpStatus.PARTIAL_CONTENT);
		}
	
	}
	
	@RequestMapping(value = "/slaRequestStep/getAll/{slaResponseParametersId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAll(@PathVariable("slaResponseParametersId") int slaResponseParametersId) {
		try {
			List<SlaRequestSteps> ListslaRequestSteps = this.slaRequestStepsServices.getAllBySlaResponseParamId(slaResponseParametersId);
			return new ResponseEntity<>(ListslaRequestSteps, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SlaRequestStepsController.getAll: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/slaRequestStep/delete/{slaRequestStepsId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> deleteById(@PathVariable("slaRequestStepsId") int slaRequestStepsId) {
		try {
			this.slaRequestStepsServices.delete(slaRequestStepsId);
			return new ResponseEntity<>(new ResponseUtil("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SlaRequestStepsController.deleteById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
