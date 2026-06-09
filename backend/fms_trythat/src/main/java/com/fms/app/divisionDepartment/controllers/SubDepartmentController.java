package com.fms.app.divisionDepartment.controllers;

import java.util.Collection;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.divisionDepartment.models.SubDepartment;
import com.fms.app.divisionDepartment.models.dto.SubDepartmentOutputDTO;
import com.fms.app.divisionDepartment.models.dto.SubDepartmentPaginatedInputDTO;
import com.fms.app.divisionDepartment.services.SubDepartmentService;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.dto.BLDTO;
import com.fms.app.spaceManagement.models.dto.SpaceAllocationFilterInputDTO;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller 
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1") 
public class SubDepartmentController {
	
	@Autowired
	SubDepartmentService subDepartmentService;
	
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(SubDepartmentController.class);

	@RequestMapping(value = "/sub_department/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveDepartment(@RequestBody SubDepartment dataRecord) {
		try {
			dataRecord = this.subDepartmentService.saveOrUpdate(dataRecord);
			return new ResponseEntity<>(dataRecord, HttpStatus.OK);
		}
		catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			return new ResponseEntity<>(new ResponseUtil(exceptionCause, HttpStatus.CONFLICT.value()),HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/sub_department/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllDepartments() {
		List<SubDepartment> data = this.subDepartmentService.getAllSubDepartments();
		List<SubDepartmentOutputDTO> result = data.stream().map(element -> this.mapper.map(element, SubDepartmentOutputDTO.class))
				.collect(Collectors.toList());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sub_department/getAllPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllDepartmentsPaginated(@RequestBody SubDepartmentPaginatedInputDTO filterDto) {
		try {
			PagedResponse<SubDepartment> data = this.subDepartmentService.getAllSubDepartmentsPaginated(filterDto);
			List<SubDepartmentOutputDTO> result = ((Collection<SubDepartment>) data.getContent()).stream().map(element -> this.mapper.map(element, SubDepartmentOutputDTO.class))
					.collect(Collectors.toList());
			GenericPagedResponse<SubDepartmentOutputDTO> response = new GenericPagedResponse<SubDepartmentOutputDTO>(result, data.getCurrentPage(),
					data.getTotalPages(), data.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SubDepartmentController.getAllPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/sub_department/getareabyfloor", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getDepartmentAreabyFloor(@RequestBody SubDepartmentOutputDTO filter)   
	{  
		List<Map<String, Object>> depData = this.subDepartmentService.getsubdepartmentareabyfloor(filter);
		return new ResponseEntity<>(depData, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sub_department/getallocatedarea", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getDivisionAllocatedArea(@RequestBody SpaceAllocationFilterInputDTO filter)   
	{  
		List<Map<String, Object>> divData = this.subDepartmentService.getsubdepartmentallocatedarea(filter);
		return new ResponseEntity<>(divData, HttpStatus.OK);
	}
	
}
