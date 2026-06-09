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
import com.fms.app.helpdesk.models.Parts;
import com.fms.app.helpdesk.models.RequestParts;
import com.fms.app.helpdesk.models.dto.RequestPartsDto;
import com.fms.app.helpdesk.services.PartsService;
import com.fms.app.helpdesk.services.RequestPartsService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class RequestPartsController {
	@Autowired
	RequestPartsService requestPartsService;

	@Autowired
	PartsService partService;

	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(RequestPartsController.class);

	@RequestMapping(value = "/reqParts/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveRequestPart(@RequestBody RequestPartsDto dataRecord) {
		RequestParts data = this.mapper.map(dataRecord, RequestParts.class);

		try {
			if (data.getRequestPartId() == null || data.getRequestPartId() == 0) {
				java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				java.sql.Time currentTime = new java.sql.Time(Calendar.getInstance().getTime().getTime());
				data.setDateAssigned(currentDate);
				data.setTimeAssigned(currentTime);
			}
//			data.getPart().setPartCode(dataRecord.getPartId());

			Parts partRecord = this.partService.getByPartId(data.getPartId());

			if (partRecord != null && dataRecord.getReducePartQnty() <= partRecord.getQutOnHand()) {
				this.requestPartsService.saveOrUpdate(data);
				partRecord.setQutOnHand(partRecord.getQutOnHand() - (dataRecord.getReducePartQnty()));
				this.partService.saveOrUpdate(partRecord);
				return new ResponseEntity<>(dataRecord, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new ResponseUtil("Parts are not available", HttpStatus.CONFLICT.value()),
						HttpStatus.OK);
			}
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestPartsController.saveRequestPart: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/reqParts/getByRequestId/{requestId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getReqPartsByRequestId(@PathVariable("requestId") int requestId) {
		try {
			List<RequestParts> data = this.requestPartsService.getByRequestId(requestId);
			List<RequestPartsDto> resData = new ArrayList<RequestPartsDto>();
			resData = data.stream().map(each -> this.mapper.map(each, RequestPartsDto.class)).collect(Collectors.toList());
			return new ResponseEntity<>(resData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestPartsController.getReqPartsByRequestId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/reqParts/getById/{requestPartId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getReqPartById(@PathVariable("requestPartId") int requestPartId) {
		try {
			RequestParts data = this.requestPartsService.getById(requestPartId);
			RequestPartsDto dto = this.mapper.map(data, RequestPartsDto.class);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestPartsController.getReqPartById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/reqParts/deleteById/{requestPartId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteById(@PathVariable("requestPartId") int requestPartId) {

		try {
			RequestParts requestPart = this.requestPartsService.getById(requestPartId);
			Parts part = this.partService.getByPartId(requestPart.getPartId());
			part.setQutOnHand(part.getQutOnHand() +requestPart.getReqQuantity());
			this.requestPartsService.deleteById(requestPartId);
			this.partService.saveOrUpdate(part);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestPartsController.deleteById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/reqParts/checkExist/{requestId}/{partId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkIsPartExist(@PathVariable("requestId") Integer requestId,
			@PathVariable("partId") Integer partId) {
		//RequestParts data = this.requestPartsService.getById(requestPartId);
		try {
			boolean isExist = this.requestPartsService.checkIsPartExist(requestId,partId);
			return new ResponseEntity<>(isExist, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestPartsController.checkIsPartExist: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
