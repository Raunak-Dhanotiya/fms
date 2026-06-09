package com.fms.app.reservation.controllers;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fms.app.reservation.models.Resources;
import com.fms.app.reservation.models.dto.ResourcesDTO;
import com.fms.app.reservation.services.ResourcesService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;


@RestController 
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class ResourcesController {
	
	@Autowired  
	ResourcesService service;
	
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(ResourcesController.class);
	
	@RequestMapping(value = "/resources/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveResource(@RequestBody Resources dataRecord) {
		Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
		Time currentTime = new Time(Calendar.getInstance().getTime().getTime());
		dataRecord.setDateCreated(currentDate);
		dataRecord.setTimeCreated(currentTime);
		try {
			this.service.saveOrUpdate(dataRecord); 
			return new ResponseEntity<>(dataRecord, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ResourcesController.saveResource: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}	
	
	@RequestMapping(value = "/resources/getResourcesList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getResourcesList() {
		try {
			List<Resources> ResourcesData = this.service.getAllResources();
			List<ResourcesDTO> finalResult = ResourcesData.stream().map(x->this.mapper.map(x, ResourcesDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(finalResult, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ResourcesController.getResourcesList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/resources/getResourcesListPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getResourcesListPaginated(@RequestBody FilterDTOCopy filterDto) {
		try {
			PagedResponse<Resources> ResourcesData = this.service.getAllResourcesPaginated(filterDto);
			List<ResourcesDTO> finalResult = ((Collection<Resources>) ResourcesData.getContent()).stream().map(x->this.mapper.map(x, ResourcesDTO.class))
					.collect(Collectors.toList());
			GenericPagedResponse<ResourcesDTO> response = new GenericPagedResponse<ResourcesDTO>(finalResult, ResourcesData.getCurrentPage(),
					ResourcesData.getTotalPages(), ResourcesData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ResourcesController.getResourcesListPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/resources/getResourceById/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getResourceById(@PathVariable("id") int id) {
		try {
			Resources resource = this.service.getResourceById(id);
			ResourcesDTO finalResource = this.mapper.map(resource, ResourcesDTO.class);
					
			
			return new ResponseEntity<>(finalResource, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ResourcesController.getResourceById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/resource/check/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkResourceExists(@PathVariable("id") String id) {
		try {
			final boolean isResourceExist = this.service.resourceExists(id);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isResourceExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ResourcesController.checkResourceExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	

}
