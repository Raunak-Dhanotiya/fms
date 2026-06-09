package com.fms.app.helpdesk.controllers;

import java.util.Collection;
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

import com.fms.app.helpdesk.models.SlaRequestParameters;
import com.fms.app.helpdesk.models.dto.FilterApplicableSlaDto;
import com.fms.app.helpdesk.models.dto.SlaRequestParameterDto;
import com.fms.app.helpdesk.services.ProblemTypeServices;
import com.fms.app.helpdesk.services.SlaRequestParametersServices;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class SlaRequestParametersController {

	@Autowired
	SlaRequestParametersServices service;
	
	@Autowired
	ProblemTypeServices problemTypeServices;
	
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(SlaRequestParametersController.class);

	@RequestMapping(value = "/slaRequestParameters/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllSlaRequestParameters() {

		try {
			List<SlaRequestParameterDto> slaRequestParametersData = this.service.getAllSlaRequestParameters();

			return new ResponseEntity<>(slaRequestParametersData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ClientController.getAllSlaRequestParameters: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/slaRequestParameters/getAllPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllSlaRequestParametersPaginated(@RequestBody FilterDTOCopy filterDto) {

		try {
			PagedResponse<SlaRequestParameters> slaRequestParametersData = this.service.getAllSlaRequestParametersPaginated(filterDto);
			List<SlaRequestParameterDto> finalResult = ((Collection<SlaRequestParameters>) slaRequestParametersData.getContent()).stream().map(eachRecord ->
			{
					Integer problemType = eachRecord.getProbTypeId();
					SlaRequestParameterDto data = new SlaRequestParameterDto();
					if ( problemType != null && problemType > 0) {
						String problemTypeString = this.problemTypeServices.getProblemTypeString(problemType);
						data = this.mapper.map(eachRecord, SlaRequestParameterDto.class);
						data.setProblemTypeString(problemTypeString);
					} else {
						data = this.mapper.map(eachRecord, SlaRequestParameterDto.class);
						data.setProblemTypeString(" ");
					}
					return data;
			}).collect(Collectors.toList());
			GenericPagedResponse<SlaRequestParameterDto> response = new GenericPagedResponse<SlaRequestParameterDto>(finalResult, slaRequestParametersData.getCurrentPage(),
					slaRequestParametersData.getTotalPages(), slaRequestParametersData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ClientController.getAllSlaRequestParametersPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/slaRequestParameters/getSlaRequestParametersById/{slaRequestParametersId}",
			method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getSlaRequestParametersById(@PathVariable("slaRequestParametersId") int slaRequestParametersId) {
		
		try {
			SlaRequestParameterDto data = this.service.getSlaRequestParametersId(slaRequestParametersId);
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ClientController.getSlaRequestParametersById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/slaRequestParameters/saveSlaRequestParameters", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveSlaRequestParameter(@RequestBody SlaRequestParameterDto slaRequestParameters) {
		try {
			boolean isSlaExist = service.checkIsSlaRequestExist(slaRequestParameters);
			SlaRequestParameterDto savedData = new SlaRequestParameterDto();
			if (!isSlaExist) {
				try {
					savedData = service.saveOrUpdate(slaRequestParameters);
				} catch (Exception e) {
					return new ResponseEntity<>(new ResponseUtil(e.getCause().getMessage(), HttpStatus.CONFLICT.value()), HttpStatus.OK);
				}

				return new ResponseEntity<>(savedData, HttpStatus.OK);

			} else {
				return new ResponseEntity<>(
						new ResponseUtil(String.valueOf(CommonConstant.UNABLE_TO_PROCESS_SLA), HttpStatus.PARTIAL_CONTENT.value()),
						HttpStatus.PARTIAL_CONTENT);
			}
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ClientController.saveSlaRequestParameter: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/slaRequestParameters/getApplicableSla", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getApplicableSlaRequestParameter (@RequestBody FilterApplicableSlaDto data) {
		try {
			List<SlaRequestParameters> slaData =  this.service.getApplicableSLA(data);
			return new ResponseEntity<>(slaData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ClientController.getApplicableSlaRequestParameter: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
