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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.spaceManagement.models.Ctry;
import com.fms.app.spaceManagement.models.dto.CountryDTO;
import com.fms.app.spaceManagement.models.dto.CountryFilterInputDTO;
import com.fms.app.spaceManagement.models.dto.CountryOutputDto;
import com.fms.app.spaceManagement.services.CtryService;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller 
//@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Access-Control-Allow-Origin")
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class CtryController {
	
	@Autowired  
	CtryService ctryService;
	
	@Autowired
	ModelMapper mapper;
	private static final Logger logger = LogManager.getLogger(CtryController.class);
	
	@RequestMapping(value = "/ctry/getCntry/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getCntryById(@PathVariable("id") Integer id) {
		
		try {
			Ctry cntry = this.ctryService.getCtry(id);
			CountryOutputDto cntryOutput = new CountryOutputDto();
			cntryOutput.setCtry(cntry);
			return new ResponseEntity<>(cntryOutput, HttpStatus.OK);
			
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CtryController.getCntryById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
    
    @RequestMapping(value = "/cntry/cntrys", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getCountry(@RequestBody CountryFilterInputDTO cntryFilterDto) {

		try {
			PagedResponse<Ctry> cntryData = this.ctryService.getAllCtry(cntryFilterDto);
			List<CountryDTO> ctryOutPut = ((Collection<Ctry>) cntryData.getContent()).stream().map(element -> this.mapper.map(element, CountryDTO.class))
					.collect(Collectors.toList());
			
			GenericPagedResponse<CountryDTO> response = new GenericPagedResponse<CountryDTO>(ctryOutPut, cntryData.getCurrentPage(),
					cntryData.getTotalPages(), cntryData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CtryController.getCountry: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/ctry/list", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getCntryList() {
		try {
			List<Ctry> cntryDatas = this.ctryService.getAll();
			List<CountryFilterInputDTO> cntryOutput = cntryDatas.stream().map(this::convertCntryTODto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(cntryOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CtryController.getCntryList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	private CountryFilterInputDTO convertCntryTODto(Ctry ctry) {
		CountryFilterInputDTO dto = new CountryFilterInputDTO();
		dto.setId(ctry.getCtryId());
		dto.setName(ctry.getCtryName());
		dto.setCtryCode(ctry.getCtryCode());
		return dto;
	}
	
	@RequestMapping(value = "/cntry/saveCntry", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveCountry(@RequestBody CountryOutputDto countryOutputDto) {
		
		
		try {
			Ctry c = countryOutputDto.getCtry();
			this.ctryService.saveOrUpdate(c);
			
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CtryController.saveCountry: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(new ResponseUtil<>(countryOutputDto, CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ctry/check/{ctryCode}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkCtryCodeExists(@PathVariable("ctryCode") String ctryCode) {
		try {
			final boolean isCtryCodeExist = this.ctryService.checkCtryCodeExists(ctryCode);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isCtryCodeExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CtryController.checkCtryCodeExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
