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

import com.fms.app.helpdesk.models.Tools;
import com.fms.app.helpdesk.services.ToolsServices;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class ToolsController {

	@Autowired
	ToolsServices toolsServices;
	
	private static final Logger logger = LogManager.getLogger(ToolsController.class);

	@RequestMapping(value = "/tools/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveTool(@RequestBody Tools tool) {
		try {
		toolsServices.saveOrUpdate(tool);
		return new ResponseEntity<>(tool, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ToolsController.saveTool: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/tools/getList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllTools() {

		try {
			List<Tools> ListTools = this.toolsServices.getAll();
			return new ResponseEntity<>(ListTools, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ToolsController.getAllTools: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/tools/getListPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllToolsPaginated(@RequestBody FilterDTOCopy filterDto) {
		try {
			PagedResponse<Tools> ListTools = this.toolsServices.getAllPaginated(filterDto);
			List<Tools> finalResult = ((Collection<Tools>) ListTools.getContent()).stream().collect(Collectors.toList());
			GenericPagedResponse<Tools> response = new GenericPagedResponse<Tools>(finalResult, ListTools.getCurrentPage(),
					ListTools.getTotalPages(), ListTools.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ToolsController.getAllToolsPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/tools/getById/{toolsId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getToolById(@PathVariable("toolsId") int toolsId) {

		try {
			Tools record = this.toolsServices.getById(toolsId);
			return new ResponseEntity<>(record, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ToolsController.getToolById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/tools/delete/{toolsId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> deleteToolById(@PathVariable("toolsId") int toolsId) {
		try {
			this.toolsServices.deleteById(toolsId);
			return new ResponseEntity<>(new ResponseUtil("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ToolsController.deleteToolById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/tools/check/{toolsId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkToolExists(@PathVariable("toolsId") int toolsId) {
		try {
			final boolean isExist = this.toolsServices.checkToolExists(toolsId);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ToolsController.checkToolExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
