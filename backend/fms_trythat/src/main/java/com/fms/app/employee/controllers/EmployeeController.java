package com.fms.app.employee.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.common.models.dto.FilterDto;
import com.fms.app.employee.models.Employee;
import com.fms.app.employee.models.dto.EmployeeContact;
import com.fms.app.employee.models.dto.EmployeeDetails;
import com.fms.app.employee.models.dto.EmployeeFilterInputDTO;
import com.fms.app.employee.models.dto.EmployeeFilterPaginationDTO;
import com.fms.app.employee.models.dto.EmployeeLocation;
import com.fms.app.employee.models.dto.EmployeeOutput;
import com.fms.app.employee.services.EmployeeService;
import com.fms.app.helpdesk.models.WorkTeams;
import com.fms.app.helpdesk.repository.WorkTeamsRepository;
import com.fms.app.spaceManagement.models.dto.SpaceFilterReportDTO;
import com.fms.app.spaceManagement.models.dto.SpaceReportInputDTO;
import com.fms.app.spaceManagement.models.dto.SpaceUtilizationStatisticsFilterInputDTO;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterCriteria;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;


@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	@Autowired
	ModelMapper mapper;
	@Autowired
	WorkTeamsRepository wrteamrepo;
	
	private static final Logger logger = LogManager.getLogger(EmployeeController.class);

	@RequestMapping(value = "/em/saveEmp", method = RequestMethod.POST)
	public ResponseEntity<Object> saveEmployee(@RequestBody EmployeeOutput empOut) {
		try {
			Employee emRecord = this.mapper.map(empOut.getEmployeeDetails(), Employee.class);
			this.mapper.map(empOut.getEmployeeLocation(), emRecord);
			this.mapper.map(empOut.getEmployeeContact(), emRecord);
			emRecord.setBl(null);
			emRecord.setFl(null);
			emRecord.setRm(null);
			emRecord.setDivision(null);
			emRecord.setDepartment(null);
			emRecord.setSubDepartment(null);
			this.employeeService.saveEmployee(emRecord);
			return new ResponseEntity<>(new ResponseUtil(CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EmployeeController.saveEmployee: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/em/getAllEm", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllEmployee() {
		
		try {
			List<Employee> emData = this.employeeService.getEmployess();
			List<EmployeeDetails> empDetails = new ArrayList<EmployeeDetails>();
			empDetails = emData.stream().map(each -> this.mapper.map(each, EmployeeDetails.class))
			.collect(Collectors.toList());
			 empDetails.forEach(em -> {
				 emData.forEach( e -> {
					 if(e.getEmId() == em.getEmId()) {
						 if (e.getEmPhoto()!=null && e.getEmPhoto() > 0 && e.getDoc() != null) {
							 em.setEmPhotoMobile(CommonUtil.decompressBytes(e.getDoc().getFileContent()));
							}
					 }
				 });	
			});

			return new ResponseEntity<>(empDetails, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EmployeeController.getAllEmployee: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/em/getAllEmPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllEmployeePaginated(@RequestBody EmployeeFilterPaginationDTO filterDTO) {
		
		try {
			PagedResponse<Employee> emData = this.employeeService.getEmployessPaginated(filterDTO);
			List<EmployeeDetails> empDetails =  ((Collection<Employee>) emData.getContent()).stream().map(each -> 
			{
	            EmployeeDetails employeeDetails = this.mapper.map(each, EmployeeDetails.class);
	            if (each.getEmPhoto() != null && each.getEmPhoto() > 0 && each.getDoc() != null) {
	                employeeDetails.setEmPhotoMobile(CommonUtil.decompressBytes(each.getDoc().getFileContent()));
	            }
	            return employeeDetails;
	        })
			.collect(Collectors.toList());
			GenericPagedResponse<EmployeeDetails> response = new GenericPagedResponse<EmployeeDetails>(empDetails, emData.getCurrentPage(),
					emData.getTotalPages(), emData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EmployeeController.getAllEmployeePaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/em/getEmById/{id}", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> getEmById(@PathVariable("id") int id)   
	{  
		try {
			Employee em = this.employeeService.getEmployeeByID(id);
			EmployeeOutput finalResult = new EmployeeOutput();
			if (em != null) {
				EmployeeDetails empDetails = this.mapper.map(em, EmployeeDetails.class);
				if (em.getEmPhoto()!=null && em.getEmPhoto() > 0 && em.getDoc() != null) {
					empDetails.setEmPhotoMobile(CommonUtil.decompressBytes(em.getDoc().getFileContent()));
				}
				EmployeeLocation emplocation = this.mapper.map(em, EmployeeLocation.class);
				EmployeeContact empContact = this.mapper.map(em, EmployeeContact.class);

				finalResult.setEmployeeContact(empContact);
				finalResult.setEmployeeDetails(empDetails);
				finalResult.setEmployeeLocation(emplocation);
				finalResult.setEm(em);
			}
			return new ResponseEntity<>(finalResult, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EmployeeController.getEmById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/em/getAllUnAssign/{emId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllUnAssignEmployee(@PathVariable("emId") Integer emId) {
	
		try {
			List<FilterDto> finalObj = this.employeeService.getAllUnAssignEmployee(emId).stream().map(this::convertDTOFilter)
					.collect(Collectors.toList());
			
			return new ResponseEntity<>(finalObj, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EmployeeController.getAllUnAssignEmployee: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	//get emp list for client panel
	@RequestMapping(value = "/em/getAllEmp", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getEmpList() {
		try {
			List<Employee> empDatas = this.employeeService.getEmployess();
			List<EmployeeFilterInputDTO> empOutput = empDatas.stream().map(this::convertEmployeeTODto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(empOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EmployeeController.getEmpList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/em/check/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkUserExists(@PathVariable("id") String id) {
		try {
			final boolean isUserExist = this.employeeService.checkEmployeeExistsByEmCode(id);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isUserExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EmployeeController.checkUserExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	private EmployeeFilterInputDTO convertEmployeeTODto(Employee employee) {
		EmployeeFilterInputDTO dto = new EmployeeFilterInputDTO();
		dto.setEmId(employee.getEmId());
		dto.setEmCode(employee.getEmCode());
		dto.setFirstName(employee.getFirstName()+" "+employee.getLastName());
		return dto;
	}

	private FilterDto convertDTOFilter(Employee e) {
		FilterDto dto = new FilterDto();
		dto.setId(e.getEmId());
		dto.setName(e.getFirstName().trim() + " " + e.getLastName());
		return dto;
	}
	
	@RequestMapping(value = "/em/resportsByGroup", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getReports(@RequestBody SpaceReportInputDTO filter) {
		try {
			List<Map<String, Object>> data = this.employeeService.getReportsByGroup(filter);
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EmployeeController.getReports: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/em/resportsByGroupPaginated", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getReportsPaginated(@RequestBody SpaceReportInputDTO filter) {
		try {
			Map<String, Object> data = this.employeeService.getReportsByGroupPaginated(filter);
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EmployeeController.getReports: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/em/reportfilter", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getEmployeesWithFilter(@RequestBody SpaceFilterReportDTO filter) {
		try {
			List<Employee> empData = this.employeeService.getEmployeesWithFilter(filter);
//			if(filter.getBlId()!=null && filter.getBlId()>0) {
//				empData = empData.stream().filter(eq -> filter.getBlId() == eq.getBlId())
//						.collect(Collectors.toList());
//			}
//			if(filter.getFlId()!=null && filter.getFlId()>0) {
//				empData = empData.stream().filter(eq -> filter.getFlId() == eq.getFlId())
//						.collect(Collectors.toList());
//			}
//			if(filter.getDivId()!=null &&filter.getDivId()>0) {
//				empData = empData.stream().filter(eq -> filter.getDivId() == eq.getDivId())
//						.collect(Collectors.toList());
//			}
//			if(filter.getDepId()!=null &&filter.getDepId()>0) {
//				empData = empData.stream().filter(eq -> filter.getDepId() == eq.getDepId())
//						.collect(Collectors.toList());
//			}
			if(filter.getTeamId()!=null && filter.getTeamId()>0) {
				List<WorkTeams> wrteams = this.wrteamrepo.findAllByTeamId(filter.getTeamId());
				if (!wrteams.isEmpty()) {
					List<Integer> emIds = wrteams.stream().map(WorkTeams::getEmId).collect(Collectors.toList());
					empData = empData.stream().filter(eq -> emIds.contains(eq.getEmId())).collect(Collectors.toList());
				}
			}
			List<EmployeeDetails> empDetails = new ArrayList<EmployeeDetails>();
			empDetails = empData.stream().map(each -> this.mapper.map(each, EmployeeDetails.class))
			.collect(Collectors.toList());
			return new ResponseEntity<>(empDetails, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EmployeeController.getEmployeeforreport: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/em/spaceutilizationbygroup", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getspaceutilization(@RequestBody SpaceUtilizationStatisticsFilterInputDTO filter) {
		try {
			List<Map<String, Object>> data = this.employeeService.getspaceutilizationstatistics(filter);
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EmployeeController.getspaceutilization: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/em/getAllEmByScroll", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getAllEmByScroll(@RequestBody FilterCriteria filter)   
	{  
		try {
			List<Employee> empDatas = this.employeeService.getEmOnScroll(filter);
			List<EmployeeFilterInputDTO> empOutput = empDatas.stream().map(this::convertEmployeeTODto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(empOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EmployeeController.getAllEmByScroll: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}


