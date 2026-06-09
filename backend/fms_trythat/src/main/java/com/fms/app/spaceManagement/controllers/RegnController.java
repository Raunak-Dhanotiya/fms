package com.fms.app.spaceManagement.controllers;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.spaceManagement.models.Regn;
import com.fms.app.spaceManagement.models.dto.RegnDTO;
import com.fms.app.spaceManagement.models.dto.RegnFilterInputDTO;
import com.fms.app.spaceManagement.models.dto.RegnOutputDto;
import com.fms.app.spaceManagement.services.RegnService;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;


@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class RegnController {

	@Autowired  
	RegnService regnService;
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(RegnController.class);
	
	//save state
	@RequestMapping(value = "/regn/saveRegn", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveRegn(@RequestBody Regn regn) 
	{ 
		try {
		regnService.saveOrUpdate(regn);  
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RegnController.saveRegn: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ResponseUtil<>(regn, CommonConstant.SUCCESS_MSG,HttpStatus.OK.value()), HttpStatus.OK);
	}

	//delete regn
	@DeleteMapping("/api/v1/deleteregn")  
	private void deleteRegn(@RequestBody Regn regn)   
	{  
		try {
		regnService.delete(regn);  
		}catch (Exception e) {
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RegnController.deleteRegn: "+stacktrace,e);
		}
	} 
	
	@RequestMapping(value = "/regn/getRegn/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getRegnId(@PathVariable("id") Integer id) {
		try {
			Regn regn = this.regnService.getRegnById(id);
			RegnOutputDto regnOutput = new RegnOutputDto();
			regnOutput.setRegn(regn);
			return new ResponseEntity<>(regnOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RegnController.getRegnId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/regn/search", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getRegions(@RequestBody RegnFilterInputDTO regnFilterDto) {

		try {
			PagedResponse<Regn> regnData = this.regnService.getFilteredRegns(regnFilterDto);
			List<RegnDTO> regnOutPut = ((Collection<Regn>) regnData.getContent()).stream().map(element -> this.mapper.map(element, RegnDTO.class))
					.collect(Collectors.toList());
			GenericPagedResponse<RegnDTO> response = new GenericPagedResponse<RegnDTO>(regnOutPut, regnData.getCurrentPage(),
					regnData.getTotalPages(), regnData.getTotalElements());

			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RegnController.getRegions: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/regn/list", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getRegnList() {
		try {
			List<Regn> regnData = this.regnService.getAllRegn();
			List<RegnFilterInputDTO> regnOutput = regnData.stream().map(this::convertRegnTODto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(regnOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RegnController.getRegnList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	private RegnFilterInputDTO convertRegnTODto(Regn regn) {
		RegnFilterInputDTO dto = new RegnFilterInputDTO();
		dto.setId(regn.getRegnId());
		dto.setCode(regn.getRegnCode());
		dto.setName(regn.getRegnName());
		dto.setCntryId(regn.getCtryId());

		return dto;
	}
	
	@RequestMapping(value = "/regn/check", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> checkRegnCodeExists(@RequestBody RegnFilterInputDTO regnFilterDto) {
		try {
			final boolean isRegnCodeExist = this.regnService.checkRegnCodeExists(regnFilterDto.getCode(),regnFilterDto.getCntryId());
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isRegnCodeExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RegnController.checkRegnCodeExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
