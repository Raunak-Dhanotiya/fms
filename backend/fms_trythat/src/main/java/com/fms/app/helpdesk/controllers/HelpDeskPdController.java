package com.fms.app.helpdesk.controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fms.app.helpdesk.models.HelpDeskProblemDescription;
import com.fms.app.helpdesk.services.HelpDeskPdService;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@RestController 
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class HelpDeskPdController {
	@Autowired
	HelpDeskPdService helpDeskPdService;
	
	private static final Logger logger = LogManager.getLogger(HelpDeskPdController.class);
	
	@RequestMapping(value = "/helpDeskPd/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> savePd(@RequestBody HelpDeskProblemDescription pd) {
		try {
			this.helpDeskPdService.saveOrUpdate(pd); 
			return new ResponseEntity<>(new ResponseUtil(CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in HelpDeskPdController.savePd: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/helpDeskPd/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllHelpDeskPd() {
		try {
			List<HelpDeskProblemDescription> data  = this.helpDeskPdService.getAllPd();
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in HelpDeskPdController.getAllHelpDeskPd: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/helpDeskPd/getAllPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllHelpDeskPdPaginated(@RequestBody FilterDTOCopy filterDto) {
		try {
			PagedResponse<HelpDeskProblemDescription> data  = this.helpDeskPdService.getAllPdPaginated(filterDto);
			List<HelpDeskProblemDescription> finalResult = ((Collection<HelpDeskProblemDescription>) data.getContent()).stream().collect(Collectors.toList());
			GenericPagedResponse<HelpDeskProblemDescription> response = new GenericPagedResponse<HelpDeskProblemDescription>(finalResult, data.getCurrentPage(),
					data.getTotalPages(), data.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in HelpDeskPdController.getAllHelpDeskPdPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/helpDeskPd/getById/{pdId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getCraftspersonByCfId(@PathVariable("pdId") int pdId) {
		try {
			HelpDeskProblemDescription data = this.helpDeskPdService.getByPdId(pdId);
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in HelpDeskPdController.getCraftspersonByCfId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/helpDeskPd/deleteById/{pdId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteByCfId(@PathVariable("pdId") int pdId) {
		try {
			this.helpDeskPdService.deleteById(pdId);
			
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
			}catch (Exception e) {
				String exceptionCause = CommonUtil.getExceptionCause(e);
				String stacktrace = CommonUtil.getStakeTrace(e);
				logger.error("Exception in HelpDeskPdController.deleteByCfId: "+stacktrace,e);
				return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
			}
	}
	
	@RequestMapping(value = "/helpDeskPd/check/{description}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkUserExists(@PathVariable("description") String description) {
		
		try {
			final boolean isExist = this.helpDeskPdService.checkPdExist(description);
			
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in HelpDeskPdController.checkUserExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
}
