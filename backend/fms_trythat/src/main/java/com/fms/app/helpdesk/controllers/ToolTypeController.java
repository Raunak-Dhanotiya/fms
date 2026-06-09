package com.fms.app.helpdesk.controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

import com.fms.app.helpdesk.models.ToolType;
import com.fms.app.helpdesk.services.ToolTypeServices;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;


@Controller 
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class ToolTypeController {
	
	@Autowired
	ToolTypeServices toolTypeSrv;
	
	private static final Logger logger = LogManager.getLogger(ToolTypeController.class);
	
	@RequestMapping(value = "/toolType/save", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> saveToolType(@RequestBody ToolType toolTpe)   
	{ 
		try {
			ToolType savedRecord = toolTypeSrv.saveOrUpdate(toolTpe);
			return new ResponseEntity<>(savedRecord, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ToolTypeController.saveToolType: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/toolType/getList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllToolTypes() {
		
		try {
			List<ToolType> ListToolType = this.toolTypeSrv.getAll();
			return new ResponseEntity<>(ListToolType, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ToolTypeController.getAllToolTypes: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/toolType/getListPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllToolTypesPaginated(@RequestBody FilterDTOCopy filterDto) {
		
		try {
			PagedResponse<ToolType> ListToolType = this.toolTypeSrv.getAllPaginated(filterDto);
			List<ToolType> finalResult = ((Collection<ToolType>) ListToolType.getContent()).stream().collect(Collectors.toList());
			GenericPagedResponse<ToolType> response = new GenericPagedResponse<ToolType>(finalResult, ListToolType.getCurrentPage(),
					ListToolType.getTotalPages(), ListToolType.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ToolTypeController.getAllToolTypesPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/toolType/getById/{toolTypeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getToolTypeById(@PathVariable("toolTypeId") int toolTypeId) {
		
		try {
			ToolType record = this.toolTypeSrv.getById(toolTypeId);
			return new ResponseEntity<>(record, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ToolTypeController.getToolTypeById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/toolType/delete/{toolTypeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> deleteToolTypeById(@PathVariable("toolTypeId") int toolTypeId) {
		try {
			this.toolTypeSrv.deleteToolType(toolTypeId);
			return new ResponseEntity<>(new ResponseUtil("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ToolTypeController.deleteToolTypeById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/toolType/check/{toolTypeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkToolTypeExists(@PathVariable("toolTypeId") int toolTypeId) {
		try {
			final boolean isExist = this.toolTypeSrv.checkToolTypeExists(toolTypeId);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ToolTypeController.checkToolTypeExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
