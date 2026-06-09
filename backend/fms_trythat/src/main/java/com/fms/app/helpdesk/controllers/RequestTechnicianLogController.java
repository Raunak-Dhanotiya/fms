package com.fms.app.helpdesk.controllers;

import java.util.ArrayList;
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

import com.fms.app.helpdesk.models.RequestTechnicianLog;
import com.fms.app.helpdesk.models.dto.RequestTechnicianLogDto;
import com.fms.app.helpdesk.services.RequestTechnicianLogService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class RequestTechnicianLogController {

	@Autowired
	RequestTechnicianLogService requiredTechnicianLogService;

	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(RequestTechnicianLogController.class);

	@RequestMapping(value = "/RequestTechnicianLog/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveRequestTechnicianLog(@RequestBody RequestTechnicianLogDto dataRecord) {

		try {
			RequestTechnicianLog data = this.mapper.map(dataRecord, RequestTechnicianLog.class);
			if (dataRecord != null) {
				RequestTechnicianLog isSave = this.requiredTechnicianLogService.saveOrUpdate(data);
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
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestTechnicianLogController.saveRequestTechnicianLog: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/requestTechnicianLog/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllRequestTechnicianlLog() {

		try {
			List<RequestTechnicianLog> data = this.requiredTechnicianLogService.getAllRequestTechnicianLog();

			List<RequestTechnicianLogDto> resData = new ArrayList<RequestTechnicianLogDto>();
			resData = data.stream().map(each -> this.mapper.map(each, RequestTechnicianLogDto.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(resData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestTechnicianLogController.getAllRequestTechnicianlLog: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/requestTechnicianLog/getByRequestId/{requestTechnicianLogId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getByRequestId(@PathVariable("requestTechnicianLogId") int requestTechnicianLogId) {

		try {
			RequestTechnicianLog requestTechnicianLogData = this.requiredTechnicianLogService.getById(requestTechnicianLogId);
			RequestTechnicianLogDto dto = this.mapper.map(requestTechnicianLogData, RequestTechnicianLogDto.class);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestTechnicianLogController.getByRequestId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/requestTechnicianLog/getAllByRequestId/{requestId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllRequestTechnicianLog(
			@PathVariable("requestId") int requestId) {
		try {
			List<RequestTechnicianLog> data = this.requiredTechnicianLogService
					.getByRequestId(requestId);

			List<RequestTechnicianLogDto> resData = new ArrayList<RequestTechnicianLogDto>();
			for (RequestTechnicianLog each : data) {
				RequestTechnicianLogDto dto =  this.mapper.map(each, RequestTechnicianLogDto.class);
			    if (dto.getTechnicianName() != null) {
			        dto.setResourceName(dto.getTechnicianName());
			    } else if (dto.getEmId() != null) {
			        dto.setResourceName(each.getEm().getFirstName()+ " " + each.getEm().getLastName() );
			    } else if(dto.getOther() != null){
			    	 dto.setResourceName(dto.getOther());
			    }
			    resData.add(dto);
			}
			return new ResponseEntity<>(resData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestTechnicianLogController.getAllRequestTechnicianLog: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/requestTechnicianLog/deleteByRequestId/{requestTechnicianLogId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteByRequestTechnicianId(
			@PathVariable("requestTechnicianLogId") int requestTechnicianId) {
		try {
			RequestTechnicianLog requestTechnicianLogData = this.requiredTechnicianLogService
					.getById(requestTechnicianId);
			this.requiredTechnicianLogService.deleteByRequestId(requestTechnicianLogData);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestTechnicianLogController.deleteByRequestTechnicianId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
