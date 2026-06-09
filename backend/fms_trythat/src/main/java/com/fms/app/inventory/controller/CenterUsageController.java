package com.fms.app.inventory.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.inventory.model.CenterUsage;
import com.fms.app.inventory.services.CenterUsageService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1/centerUsage")
public class CenterUsageController {

	@Autowired
	ModelMapper mapper;
	
	@Autowired
	CenterUsageService centerUsageService;
	
	private static final Logger logger = LogManager.getLogger(CenterUsageController.class);

	@RequestMapping(value = "/saveCenterUsage", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> saveCenterUsage(@RequestBody CenterUsage centerUsage)   
	{ 
		try {
			this.centerUsageService.saveOrUpdate(centerUsage);
			return new ResponseEntity<>(new ResponseUtil("Data Record Saved", HttpStatus.OK.value()), HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CenterUsageController.saveCenterUsage: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllCenterUsage() {

		try {
			List<CenterUsage> data = this.centerUsageService.getAllCenterBudget();
			
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CenterUsageController.getAllCenterUsage: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/getCenterUsageById/{centerUsageId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getCenterUsageById(@PathVariable("centerUsageId") int centerUsageId) {

		try {
			CenterUsage data = this.centerUsageService.getCenterUsageById(centerUsageId);
			
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CenterUsageController.getCenterUsageById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/deleteCenterUsageById/{centerUsageId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> deleteCenterUsageById(@PathVariable("centerUsageId") int centerUsageId) {
		try {
			this.centerUsageService.deleteCenterUsageById(centerUsageId);
			return new ResponseEntity<>(new ResponseUtil("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CenterUsageController.deleteCenterUsageById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/checkIfTotalCostExceed", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> checkIfTotalCostExceed(@RequestBody Map<String, Double> requestBody) {
		try {
			Map<String, BigDecimal>  data = this.centerUsageService.checkIfTotalCostExceed(requestBody);
			ResponseEntity<Object> responseEntity = ResponseEntity.ok(new ResponseUtil<>(data, "", HttpStatus.OK.value()));
			return responseEntity;

		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CenterUsageController.checkIfTotalCostExceed: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	
	}
}
