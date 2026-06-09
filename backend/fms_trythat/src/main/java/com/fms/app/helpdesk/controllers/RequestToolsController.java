package com.fms.app.helpdesk.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import com.fms.app.helpdesk.models.RequestTechnician;
import com.fms.app.helpdesk.models.RequestTools;
import com.fms.app.helpdesk.models.dto.RequestToolsDto;
import com.fms.app.helpdesk.services.RequestToolsService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class RequestToolsController 
{
	@Autowired
	RequestToolsService requestToolsService;
	
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(RequestToolsController.class);
	
	@RequestMapping(value = "/requestTools/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveRequestTools(@RequestBody RequestToolsDto dataRecord) {
		try {
			RequestTools requestTool = this.mapper.map(dataRecord, RequestTools.class);
			java.sql.Date assifnDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			java.sql.Time assignTime = new java.sql.Time(Calendar.getInstance().getTime().getTime());
			
			if (dataRecord != null) {
				if(dataRecord.getReqToolId() == null || dataRecord.getReqToolId()==0)
				{
					requestTool.setDateAssign(assifnDate);
					requestTool.setTimeAssign(assignTime);
				}
				
				RequestTools isSave = this.requestToolsService.saveOrUpdate(requestTool);
				if (isSave != null) {
					return new ResponseEntity<>(new ResponseUtil("Data Record Saved", HttpStatus.OK.value()),
							HttpStatus.OK);
				} else {
					return new ResponseEntity<>(
							new ResponseUtil("Unable to save the user request", HttpStatus.INTERNAL_SERVER_ERROR.value()),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			return new ResponseEntity<>(
					new ResponseUtil("Unable to save the parts", HttpStatus.INTERNAL_SERVER_ERROR.value()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestToolsController.saveRequestTools: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/requestTools/getAll/{reqToolId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllRequestTools(@PathVariable("reqToolId") int reqToolId) {
		try {
			List<RequestTools> data = this.requestToolsService.getByRequestId(reqToolId);

			List<RequestToolsDto> resData = new ArrayList<RequestToolsDto>();
				resData = data.stream().map(each -> this.mapper.map(each, RequestToolsDto.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(resData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestToolsController.getAllRequestTools: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
}
	
	@RequestMapping(value = "/requestTools/getByRequestId/{reqToolId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getByRequestId(@PathVariable("reqToolId") int requestId) {

		try {
			RequestTools toolsData = this.requestToolsService.getById(requestId);
			return new ResponseEntity<>(toolsData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestToolsController.getByRequestId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/requestTools/deleteByRequestId/{reqToolId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteByRequestToolsId(@PathVariable("reqToolId") int reqToolId) {
		try {
			RequestTools toolsData = this.requestToolsService.getById(reqToolId);
			this.requestToolsService.deleteByRequestId(toolsData);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestToolsController.deleteByRequestToolsId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/requestTools/checkExist/{requestId}/{toolId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkIsToolExist(@PathVariable("requestId") int requestId,
			@PathVariable("toolId") int toolId) {
		//RequestParts data = this.requestPartsService.getById(requestPartId);
		try {
			boolean isExist = this.requestToolsService.checkIsToolsExist(requestId,toolId);
			return new ResponseEntity<>(isExist, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestToolsController.checkIsToolExist: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
}
