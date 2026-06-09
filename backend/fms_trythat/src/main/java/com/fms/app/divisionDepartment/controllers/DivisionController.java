package com.fms.app.divisionDepartment.controllers;

import java.util.List;
import java.util.Map;
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

import com.fms.app.divisionDepartment.models.Division;
import com.fms.app.divisionDepartment.models.dto.DivisionFilterInputDTO;
import com.fms.app.divisionDepartment.models.dto.DivisionTreeDTO;
import com.fms.app.divisionDepartment.services.DivisionService;
import com.fms.app.spaceManagement.models.dto.SpaceAllocationFilterInputDTO;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class DivisionController {
	@Autowired
	DivisionService divisionService;
	
	private static final Logger logger = LogManager.getLogger(DivisionController.class);

	@RequestMapping(value = "/division/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveDivision(@RequestBody Division dataRecord) {
		try {
			dataRecord = this.divisionService.saveOrUpdate(dataRecord);
			return new ResponseEntity<>(dataRecord, HttpStatus.OK);	
		}
		catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DivisionController.saveDivision: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/division/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllDivisions() {

		try {
			List<Division> data = this.divisionService.getAllDivisions();
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DivisionController.getAllDivisions: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/division/getById/{divId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getDivisionById(@PathVariable("divId") int divId) {
		try {
			Division data = this.divisionService.getDivisionById(divId);
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DivisionController.getDivisionById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/division/check/{divId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkDivisionExists(@PathVariable("divId") int divId) {
		try {
			final boolean isDivisionExist = this.divisionService.checkDivisionExists(divId);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isDivisionExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DivisionController.checkDivisionExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/division/deleteById/{divId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteById(@PathVariable("divId") int divId) {
		try {
			Division divisionData = this.divisionService.getDivisionById(divId);
			this.divisionService.deleteById(divisionData);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DivisionController.deleteById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/division/list", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getAllDivisionList(@RequestBody DivisionFilterInputDTO divisionFilterDto)   
	{  
		try {
			List<Division> divisionData = this.divisionService.getFilteredDivisions(divisionFilterDto);
//			if (divisionFilterDto.getDivId() >0) {
//				divisionData = divisionData.stream().filter(division -> division.getDivId() == divisionFilterDto.getDivId())
//						.collect(Collectors.toList());
//			}
//			if (divisionFilterDto.getDescription() != null && divisionFilterDto.getDescription().length()>0) {
//				divisionData = divisionData.stream().filter(division -> division.getDescription().equals(divisionFilterDto.getDescription()))
//						.collect(Collectors.toList());
//			}
//			if (divisionFilterDto.getDivHead() >0) {
//				divisionData = divisionData.stream().filter(division -> division.getDivHead() == divisionFilterDto.getDivHead())
//						.collect(Collectors.toList());
//			}
			return new ResponseEntity<>(divisionData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DivisionController.getAllDivisionList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/division/gettreelist", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllDivisionAsTree() {

		try {
			List<DivisionTreeDTO> data = this.divisionService.getAllDivisionHierarchy();
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DivisionController.getAllDivisionAsTree: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/division/getareabyfloor", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getDivisionAreabyFloor(@RequestBody DivisionFilterInputDTO divFilterDto)   
	{  
		try {
			List<Map<String, Object>> divData = this.divisionService.getdivisionareabyfloor(divFilterDto);
			return new ResponseEntity<>(divData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DivisionController.getDivisionAreabyFloor: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/division/getallocatedarea", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getDivisionAllocatedArea(@RequestBody SpaceAllocationFilterInputDTO filter)   
	{  
		try {
			List<Map<String, Object>> divData = this.divisionService.getdivisionallocatedarea(filter);
			return new ResponseEntity<>(divData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DivisionController.getDivisionAllocatedArea: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
