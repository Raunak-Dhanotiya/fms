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

import com.fms.app.helpdesk.models.RequestOtherCost;
import com.fms.app.helpdesk.models.dto.RequestOtherCostDto;
import com.fms.app.helpdesk.services.RequestOtherCostService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")

public class RequestOtherCostController {

	@Autowired
	RequestOtherCostService requestOtherCostService;

	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(RequestOtherCostController.class);

	@RequestMapping(value = "/requestOtherCost/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveRequestTechnicianLog(@RequestBody RequestOtherCostDto dataRecord) {

		try {
			if (dataRecord != null) {
				
				RequestOtherCost record  = this.mapper.map(dataRecord, RequestOtherCost.class);
				if (record.getRequestOtherCostId() == null || record.getRequestOtherCostId() == 0) {
					java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					java.sql.Time currentTime = new java.sql.Time(Calendar.getInstance().getTime().getTime());
					record.setDateEntered(currentDate);
					record.setTimeEntered(currentTime);
				}
				RequestOtherCost isSave = this.requestOtherCostService.saveOrUpdate(record);
				if (isSave != null) {
					return new ResponseEntity<>(isSave, HttpStatus.OK);
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
			logger.error("Exception in RequestOtherCostController.saveRequestTechnicianLog: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/requestOtherCost/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllRequestTechnicianlLog() {

		try {
			List<RequestOtherCost> data = this.requestOtherCostService.getAllRequestOtherCost();

			List<RequestOtherCostDto> resData = new ArrayList<RequestOtherCostDto>();
				resData = data.stream().map(each -> this.mapper.map(each, RequestOtherCostDto.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(resData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestOtherCostController.getAllRequestTechnicianlLog: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/requestOtherCost/getByrequestOtherCostId/{requestOtherCostId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getByRequestId(@PathVariable("requestOtherCostId") int requestOtherCostId) {

		try {
			RequestOtherCost requestOtherCostData = this.requestOtherCostService.getById(requestOtherCostId);
			RequestOtherCostDto record  = this.mapper.map(requestOtherCostData, RequestOtherCostDto.class);
			return new ResponseEntity<>(record, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestOtherCostController.getByRequestId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/requestOtherCost/deleteByRequestOtherCostId/{requestOtherCostId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteByRequestTechnicianId(@PathVariable("requestOtherCostId") int requestOtherCostId) {
		try {
			RequestOtherCost requestOtherCostData = this.requestOtherCostService.getById(requestOtherCostId);
			this.requestOtherCostService.deleteByRequestOtherCostId(requestOtherCostData);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestOtherCostController.deleteByRequestTechnicianId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/requestOtherCost/getAllCostType/{requestId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllCostType(@PathVariable("requestId") int requestId) {
		try {
			List<RequestOtherCost> data = this.requestOtherCostService.getByRequestId(requestId);
			List<RequestOtherCostDto> resData = new ArrayList<RequestOtherCostDto>();
				resData = data.stream().map(each -> this.mapper.map(each, RequestOtherCostDto.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(resData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestOtherCostController.getAllCostType: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/requestOtherCost/checkExist/{requestId}/{costTypeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkCostTypeExist(@PathVariable("requestId") int requestId,
			@PathVariable("costTypeId") int costTypeId) {
		//RequestParts data = this.requestPartsService.getById(requestPartId);
		try {
			boolean isExist = this.requestOtherCostService.checkCostTypeExist(requestId,costTypeId);
			return new ResponseEntity<>(isExist, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestOtherCostController.checkCostTypeExist: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
