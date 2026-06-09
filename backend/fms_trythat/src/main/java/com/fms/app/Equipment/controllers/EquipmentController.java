package com.fms.app.Equipment.controllers;

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

import com.fms.app.Equipment.models.Equipment;
import com.fms.app.Equipment.models.EquipmentFilterDto;
import com.fms.app.Equipment.models.EquipmentOutPutDto;
import com.fms.app.EquipmentService.EquipmentService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class EquipmentController {

	@Autowired
	EquipmentService equipmentService;
	
	@Autowired
	ModelMapper mapper;
	
	
	private static final Logger logger = LogManager.getLogger(EquipmentController.class);

	@RequestMapping(value = "/equipment/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllEquipments() {

		try {
			List<Equipment> eqData = this.equipmentService.getAllEquipments();
			
			List<EquipmentOutPutDto> eqOutPutData = eqData.stream().map(this::convertEqToDto)
					.collect(Collectors.toList());		

			return new ResponseEntity<>(eqOutPutData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EquipmentController.getAllEquipments: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/equipment/getAllPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllEquipmentsPaginated(@RequestBody FilterDTOCopy filterDto) {
		try {
			PagedResponse<Equipment> eqData = this.equipmentService.getAllEquipmentsPaginated(filterDto);
			List<EquipmentOutPutDto> finalResult = ((Collection<Equipment>) eqData.getContent()).stream().map(this::convertEqToDto).collect(Collectors.toList());
			GenericPagedResponse<EquipmentOutPutDto> response = new GenericPagedResponse<EquipmentOutPutDto>(finalResult, eqData.getCurrentPage(),
					eqData.getTotalPages(), eqData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EquipmentController.getAllEquipmentsPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/equipment/getAll", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllEquipments(@RequestBody EquipmentFilterDto filter) {

		try {
			List<Equipment> eqData = this.equipmentService.getFilteredEquipments(filter);

//			if (filter.getEqId()!=null && filter.getEqId() > 0) {
//				eqData = eqData.stream().filter(eq ->eq.getEqId() == filter.getEqId())
//						.collect(Collectors.toList());
//			}
//			if (filter.getEqStdId()!=null && filter.getEqStdId() > 0) {
//				eqData = eqData.stream().filter(eq -> eq.getEqStdId() == filter.getEqStdId())
//						.collect(Collectors.toList());
//			}
//			if (filter.getDescription() != null && filter.getDescription().length() > 0) {
//				eqData = eqData.stream().filter(eq -> eq.getDescription().equals(filter.getDescription()))
//						.collect(Collectors.toList());
//			}
//			if (filter.getStatus()!=null && filter.getStatus() != "" ) {
//				eqData = eqData.stream().filter(eq -> eq.getStatus() == filter.getStatus())
//						.collect(Collectors.toList());
//			}
//			if (filter.getBlId()!=null && filter.getBlId()>0) {
//				eqData = eqData.stream().filter(eq -> eq.getBlId() == filter.getBlId())
//						.collect(Collectors.toList());
//			}
//			if (filter.getFlId()!=null && filter.getFlId()>0) {
//				eqData = eqData.stream().filter(eq -> eq.getFlId() == filter.getFlId())
//						.collect(Collectors.toList());
//			}
//			if (filter.getRmId()!=null && filter.getRmId()>0) {
//				eqData = eqData.stream().filter(eq -> eq.getRmId() == filter.getRmId())
//						.collect(Collectors.toList());
//			}
//			if (filter.getSvgElementId() != null && filter.getSvgElementId().length() > 0) {
//				eqData = eqData.stream().filter(eq -> filter.getSvgElementId().equals(eq.getSvgElementId()))
//						.collect(Collectors.toList());
//			}
//			if (filter.getEqCode() != null && filter.getEqCode().length() > 0) {
//				eqData = eqData.stream().filter(eq -> filter.getEqCode().equals(eq.getEqCode()))
//						.collect(Collectors.toList());
//			}
			List<EquipmentOutPutDto> eqOutPutData = eqData.stream().map(this::convertEqToDto)
					.collect(Collectors.toList());		
			
			return new ResponseEntity<>(eqOutPutData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EquipmentController.getAllEquipments: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/equipment/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveEquipment(@RequestBody EquipmentOutPutDto dataRecord) {
		try {
			
			Equipment eqData = this.mapper.map(dataRecord,Equipment.class);
			 eqData = this.equipmentService.saveOrUpdate(eqData); 
			return new ResponseEntity<>(eqData, HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EquipmentController.saveEquipment: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/equipment/getEquipmentById/{eqId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getEquipmentByEqId(@PathVariable("eqId") int eqId) {
		
		try {
			Equipment data = this.equipmentService.getEquipmentByEqId(eqId);
			EquipmentOutPutDto dto = this.mapper.map(data,EquipmentOutPutDto.class);
			
			return new ResponseEntity<>(dto, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EquipmentController.getEquipmentByEqId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/equipment/check/{eqId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkEquipmentExists(@PathVariable("eqId") int eqId) {
		try {
			final boolean isResourceExist = this.equipmentService.checkEquipmentExists(eqId);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isResourceExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EquipmentController.checkEquipmentExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	
	@RequestMapping(value = "/equipment/deleteById/{eqId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteByEqId(@PathVariable("eqId") int eqId) {
		
		try {
			Equipment eqData = this.equipmentService.getEquipmentByEqId(eqId);
			this.equipmentService.deleteEquipment(eqData);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EquipmentController.deleteByEqId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/equipment/getLinked/{planId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getLinedEquipments(@PathVariable("planId") int planId) {
		try {
			List<Equipment> eqData = this.equipmentService.getAllLinkedEquipments(planId);
			List<EquipmentOutPutDto> eqOutPutData = eqData.stream().map(this::convertEqToDto)
					.collect(Collectors.toList());		
			return new ResponseEntity<>(eqOutPutData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EquipmentController.getLinedEquipments: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/equipment/getUnLinked/{planId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getUnLinedEquipments(@PathVariable("planId") int planId) {
		try  {
			List<Equipment> eqData = this.equipmentService.getAllUnLinkedEquipments(planId);
			List<EquipmentOutPutDto> eqOutPutData = eqData.stream().map(this::convertEqToDto)
					.collect(Collectors.toList());		
			return new ResponseEntity<>(eqOutPutData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EquipmentController.getUnLinedEquipments: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	private EquipmentOutPutDto convertEqToDto(Equipment eq) {
		EquipmentOutPutDto dto = new EquipmentOutPutDto();
		dto = this.mapper.map(eq,EquipmentOutPutDto.class);
		if(eq.getBlId() != null) {
			dto.setBlName(eq.getBl().getBlCode() + (eq.getBl().getBlName()==null ? "": " - " +eq.getBl().getBlName()));
			dto.setBlCode(eq.getBl().getBlCode());
		}
		if(eq.getFlId() != null) {
			dto.setFloorName(eq.getFl().getFlCode() + (eq.getFl().getFlName()==null ? "":" - " + eq.getFl().getFlName()));
			dto.setFlCode(eq.getFl().getFlCode());
		}
		if(eq.getRmId() != null) {
			dto.setRmName(eq.getRm().getRmCode() +  (eq.getRm().getRmName()==null ? "":" - " +eq.getRm().getRmName()));
			dto.setRmCode(eq.getRm().getRmCode());
		}
		dto.setEqStdId(eq.getEqStdId());
		dto.setSvgElementId(eq.getSvgElementId()); 	
		return dto;
	}
	
}