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
import com.fms.app.helpdesk.models.RequestTechnician;
import com.fms.app.helpdesk.models.dto.RequestTechnicianDTO;
import com.fms.app.helpdesk.services.RequestTechnicianService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class RequestTechnicianController {
	@Autowired
	RequestTechnicianService requiredTechnicianServive;
	
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(RequestTechnicianController.class);
	
	@RequestMapping(value = "/RequestTechnician/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveRequestTechnician(@RequestBody RequestTechnicianDTO dataRecord) {
		
		try {
			RequestTechnician data	= this.mapper.map(dataRecord, RequestTechnician.class);
			if (dataRecord != null) {			
				RequestTechnician isSave = this.requiredTechnicianServive.saveOrUpdate(data);
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
			logger.error("Exception in RequestTechnicianController.saveRequestTechnician: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
		@RequestMapping(value = "/RequestTechnician/getAll/{requestId}", method = RequestMethod.GET, produces = "application/json")
		public ResponseEntity<Object> getAllRequestTechnician(@PathVariable("requestId") int requestId) {
		try {
			List<RequestTechnician> data = this.requiredTechnicianServive.getByRequestId(requestId);

			List<RequestTechnicianDTO> resData = new ArrayList<RequestTechnicianDTO>();
	 		resData = data.stream().map(each -> this.mapper.map(each, RequestTechnicianDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(resData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestTechnicianController.getAllRequestTechnician: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

		@RequestMapping(value = "/RequestTechnician/getByRequestId/{requestTechnicianId}", method = RequestMethod.GET, produces = "application/json")
		public ResponseEntity<Object> getByRequestId(@PathVariable("requestTechnicianId") int requestTechnicianId) {

			try {
				RequestTechnician requestTechnicianData = this.requiredTechnicianServive.getById(requestTechnicianId);
				RequestTechnicianDTO dto = 	this.mapper.map(requestTechnicianData, RequestTechnicianDTO.class);
				return new ResponseEntity<>(dto, HttpStatus.OK);
			}catch (Exception e) {
				String exceptionCause = CommonUtil.getExceptionCause(e);
				String stacktrace = CommonUtil.getStakeTrace(e);
				logger.error("Exception in RequestTechnicianController.getByRequestId: "+stacktrace,e);
				return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
			}
		}
		
		@RequestMapping(value = "/RequestTechnician/deleteByRequestId/{requestTechnicianId}", method = RequestMethod.DELETE, produces = "application/json")
		public ResponseEntity<Object> deleteByRequestTechnicianId(@PathVariable("requestTechnicianId") int requestTechnicianId) {
			try {
				RequestTechnician requestTechnicianData = this.requiredTechnicianServive.getById(requestTechnicianId);
				this.requiredTechnicianServive.deleteByRequestId(requestTechnicianData);
				return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
						HttpStatus.OK);
			}catch (Exception e) {
				String exceptionCause = CommonUtil.getExceptionCause(e);
				String stacktrace = CommonUtil.getStakeTrace(e);
				logger.error("Exception in RequestTechnicianController.deleteByRequestTechnicianId: "+stacktrace,e);
				return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
			}
		}
		
		@RequestMapping(value = "/RequestTechnician/checkExist/{requestId}/{technicianId}", method = RequestMethod.GET, produces = "application/json")
		public ResponseEntity<Object> checkIsTechnicianExist(@PathVariable("requestId") int requestId,
				@PathVariable("technicianId") int technicianId) {
			//RequestParts data = this.requestPartsService.getById(requestPartId);
			try {
				boolean isExist = this.requiredTechnicianServive.checkIsTechnicianExist(requestId,technicianId);
				return new ResponseEntity<>(isExist, HttpStatus.OK);
			}catch (Exception e) {
				String exceptionCause = CommonUtil.getExceptionCause(e);
				String stacktrace = CommonUtil.getStakeTrace(e);
				logger.error("Exception in RequestTechnicianController.checkIsTechnicianExist: "+stacktrace,e);
				return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
			}
		}
		
		
}
