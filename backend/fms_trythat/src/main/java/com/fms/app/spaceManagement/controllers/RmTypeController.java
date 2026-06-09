package com.fms.app.spaceManagement.controllers;

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

import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.RmType;
import com.fms.app.spaceManagement.models.dto.BLDTO;
import com.fms.app.spaceManagement.models.dto.RMTypeDTO;
import com.fms.app.spaceManagement.models.dto.RmTypeFilterInputDTO;
import com.fms.app.spaceManagement.services.RmTypeService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller 
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class RmTypeController {

	@Autowired  
	RmTypeService rmtypeService;
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(RmTypeController.class);
	
	@RequestMapping(value = "/rmType/saveRmtype", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveRmType(@RequestBody RmType rmtype) {
		
		try {
			this.rmtypeService.saveOrUpdate(rmtype);  
			return new ResponseEntity<>(rmtype, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmTypeController.saveRmType: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
			
	@RequestMapping(value = "/rmType/getList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAll() {
		try {
			List<RmType> rmtypeDatas = this.rmtypeService.getAllRmType();
			List<RmTypeFilterInputDTO> rmtypeOutput = rmtypeDatas.stream().map(this::convertRmTypeTODto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(rmtypeOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmTypeController.getAll: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	
	@RequestMapping(value = "/rmtype/list", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getAllRmType(@RequestBody RmTypeFilterInputDTO rmtypeFilterDto)   
	{  
		try {
			List<RMTypeDTO> rmTypeOutPut = this.rmtypeService.getAllRmTypeFiltered(rmtypeFilterDto);
			return new ResponseEntity<>(rmTypeOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmTypeController.getAllRmType: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rmtype/listPaginated", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getAllRmTypePaginated(@RequestBody RmTypeFilterInputDTO rmtypeFilterDto)   
	{  
		try {
			PagedResponse<RmType> rmtypeData = this.rmtypeService.getAllRmTypeFilteredPaginated(rmtypeFilterDto);
			List<RMTypeDTO> rmTypeOutPut = ((Collection<RmType>) rmtypeData.getContent()).stream().map(element -> this.mapper.map(element, RMTypeDTO.class))
					.collect(Collectors.toList());
			GenericPagedResponse<RMTypeDTO> response = new GenericPagedResponse<RMTypeDTO>(rmTypeOutPut, rmtypeData.getCurrentPage(),
					rmtypeData.getTotalPages(), rmtypeData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmTypeController.getAllRmTypePaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	private RmTypeFilterInputDTO convertRmTypeTODto(RmType rmtype) {
		RmTypeFilterInputDTO dto = new RmTypeFilterInputDTO();
		dto.setRmtypeId(rmtype.getRmtypeId());
		dto.setRmType(rmtype.getRmType());
		dto.setRmcatId(rmtype.getRmcatId());
		dto.setHighlightColor(rmtype.getHighlightColor());
		dto.setRmCat(rmtype.getRmcat().getRmCat());
		return dto;
	}
	
	@RequestMapping(value = "/rmType/getRmType/{rmcatId}/{rmtypeId}", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> getRmTypeByIds(@PathVariable("rmcatId") int rmcatId,@PathVariable("rmtypeId") int rmtypeId)   
	{  
		try {
			List<RmType> type = this.rmtypeService.getAllRmTypeByRmtypeIdAndRmcatId(rmtypeId,rmcatId);
			return new ResponseEntity<>(type, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmTypeController.getRmTypeByIds: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}

	@RequestMapping(value ="/rmType/delete/{rmcatId}/{rmtypeId}", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> deleteRmcat(@PathVariable("rmcatId") int rmcatId,@PathVariable("rmtypeId") int rmtypeId) {
		try {
			if (rmtypeId > 0) {
				this.rmtypeService.deleteRmTypeByID(rmtypeId,rmcatId);
				return new ResponseEntity<>(new ResponseUtil("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
			}
			return new ResponseEntity<>(
					new ResponseUtil("Unable to process the record", HttpStatus.INTERNAL_SERVER_ERROR.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmTypeController.deleteRmcat: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/rmType/check/{rmcatId}/{rmtypeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkRmTypeExists(@PathVariable("rmcatId") int rmcatId,@PathVariable("rmtypeId") int rmtypeId) {
		try {
			final boolean isRmTypeExists = this.rmtypeService.checkByRmTypeAndRmCat(rmtypeId,rmcatId);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isRmTypeExists), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmTypeController.checkRmTypeExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rmType/getareabyfloor", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getrmtypeAreabyFloor(@RequestBody RmTypeFilterInputDTO rmtypeFilterDto)   
	{  
		try {
			List<Map<String, Object>> catData = this.rmtypeService.getrmtypeareabyfloor(rmtypeFilterDto);
			return new ResponseEntity<>(catData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmTypeController.getrmtypeAreabyFloor: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
