package com.fms.app.divisionDepartment.controllers;

import java.util.List;
import java.util.Map;
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

import com.fms.app.divisionDepartment.models.Department;
import com.fms.app.divisionDepartment.models.dto.DepartmentFilterInputDTO;
import com.fms.app.divisionDepartment.models.dto.DepartmentOutputDTO;
import com.fms.app.divisionDepartment.services.DepartmentService;
import com.fms.app.spaceManagement.models.dto.SpaceAllocationFilterInputDTO;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(DepartmentController.class);

	@RequestMapping(value = "/department/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveDepartment(@RequestBody Department dataRecord) {
		try {
			dataRecord = this.departmentService.saveOrUpdate(dataRecord);
			return new ResponseEntity<>(dataRecord, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DepartmentController.saveDepartment: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/department/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllDepartments() {
		try {
			List<Department> data = this.departmentService.getAllDepartments();
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DepartmentController.getAllDepartments: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/department/getById/{depId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getDepartmentById(@PathVariable("depId") int depId) {
		try {
			Department data = this.departmentService.getDepartmentById(depId);
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DepartmentController.getDepartmentById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/department/check/{depId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkDepartmentExists(@PathVariable("depId") int depId) {
		try {
			final boolean isDepartmentExist = this.departmentService.checkDepartmentExists(depId);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isDepartmentExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DepartmentController.checkDepartmentExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/department/deleteById/{depId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteById(@PathVariable("depId") int depId) {
		try {
			Department departmentData = this.departmentService.getDepartmentById(depId);
			this.departmentService.deleteById(departmentData);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DepartmentController.deleteById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/department/list", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getAllDepartmentList(@RequestBody DepartmentFilterInputDTO departmentFilterDto)   
	{  
		try {
			List<Department> depData = this.departmentService.getAllDepartmentfiltered(departmentFilterDto);
			return new ResponseEntity<>(depData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DepartmentController.getAllDepartmentList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/department/getareabyfloor", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getDepartmentAreabyFloor(@RequestBody DepartmentFilterInputDTO departmentFilterDto)   
	{  
		try {
			List<Map<String, Object>> depData = this.departmentService.getdepartmentareabyfloor(departmentFilterDto);
			return new ResponseEntity<>(depData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DepartmentController.getDepartmentAreabyFloor: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/department/getallocatedarea", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getDivisionAllocatedArea(@RequestBody SpaceAllocationFilterInputDTO filter)   
	{  
		try {
			List<Map<String, Object>> divData = this.departmentService.getdepartmentallocatedarea(filter);
			return new ResponseEntity<>(divData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DepartmentController.getDivisionAllocatedArea: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/department/getallwithdivcode", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllDepartmentsWithDivCode() {

		try {
			List<Department> data = this.departmentService.getAllDepartments();
			List<DepartmentOutputDTO> depOutPut = data.stream().map(element -> this.mapper.map(element, DepartmentOutputDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(depOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DepartmentController.getAllDepartmentsWithDivCode: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
