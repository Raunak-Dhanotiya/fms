package com.fms.app.reservation.controllers;

import java.sql.Date;
import java.sql.Time;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.reservation.models.Resources;
import com.fms.app.reservation.models.RmResources;
import com.fms.app.reservation.models.dto.AssignResourceInputDTO;
import com.fms.app.reservation.models.dto.ResourcesDTO;
import com.fms.app.reservation.models.dto.RmResourcesDTO;
import com.fms.app.reservation.models.dto.UnAssignResourceInputDTO;
import com.fms.app.reservation.services.RmResourcesService;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class RmResourcesController {

	@Autowired
	RmResourcesService Service;

	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(RmResourcesController.class);

	@RequestMapping(value = "/rmResources/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveResource(@RequestBody RmResourcesDTO dataRecord) {
		RmResources rmResourceData = this.mapper.map(dataRecord, RmResources.class);
		Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
		Time currentTime = new Time(Calendar.getInstance().getTime().getTime());
		rmResourceData.setDateCreated(currentDate);
		rmResourceData.setTimeCreated(currentTime);
		try {
			this.Service.saveOrUpdate(rmResourceData);
			return new ResponseEntity<>(rmResourceData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmResourcesController.saveResource: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/rmResources/delete", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> DeleteUserScreen(@RequestBody List<UnAssignResourceInputDTO> record) {
		try {
			if (record != null) {
				record.stream().forEach(rmInput -> {
					this.Service.deleteById(rmInput.getRmResourcesId());
				});
				return new ResponseEntity<>(new ResponseUtil(CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(
					new ResponseUtil(CommonConstant.UNABLE_TO_PROCESS_MSG, HttpStatus.INTERNAL_SERVER_ERROR.value()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmResourcesController.DeleteUserScreen: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}

	@RequestMapping(value = "/resources/getUnAssigned", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getUnAssignResources(@RequestBody AssignResourceInputDTO input) {
		try {
			if (input != null) {

				List<Resources> ResourcesData = this.Service.getUnAssignResources(input.getBlId(),
						input.getFlId(), input.getRmId());

				List<ResourcesDTO> finalResult = ResourcesData.stream().map(x -> this.mapper.map(x, ResourcesDTO.class))
						.collect(Collectors.toList());
				return new ResponseEntity<>(finalResult, HttpStatus.OK);
			}
			return new ResponseEntity<>(CommonConstant.UNABLE_TO_PROCESS_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmResourcesController.getUnAssignResources: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/resources/getAssigned", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAssignResources(@RequestBody AssignResourceInputDTO input) {
		try {
			if (input != null) {

				List<RmResources> ResourcesData = this.Service.getAssignResources(input.getBlId(),
						input.getFlId(), input.getRmId());

				List<RmResourcesDTO> finalResult = ResourcesData.stream().map(x -> this.mapper.map(x, RmResourcesDTO.class))
						.collect(Collectors.toList());
				return new ResponseEntity<>(finalResult, HttpStatus.OK);
			}
			return new ResponseEntity<>(CommonConstant.UNABLE_TO_PROCESS_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmResourcesController.getAssignResources: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}

	}

}
