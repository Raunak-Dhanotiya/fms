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

import com.fms.app.spaceManagement.models.City;
import com.fms.app.spaceManagement.models.dto.CityDTO;
import com.fms.app.spaceManagement.models.dto.CityFilterInputDTO;
import com.fms.app.spaceManagement.models.dto.CityOutputDto;
import com.fms.app.spaceManagement.services.CityService;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;


@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class CityController {
	
	@Autowired  
	CityService cityService;
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(CityController.class);

	//save city
	@RequestMapping(value = "/city/saveCity", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> saveCity(@RequestBody City city)   
	{ 
		try {
			cityService.saveOrUpdate(city);  
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CityController.saveCity: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ResponseUtil<>(city, CommonConstant.SUCCESS_MSG,HttpStatus.OK.value()), HttpStatus.OK);
	}
	
	//delete city
	@DeleteMapping("/api/v1/deletecity")  
	private void deleteCtry(@RequestBody City city)   
	{  
		try {
		cityService.delete(city);  
		}
		catch (Exception e) {
			System.out.println("Failed");
		}
	} 
	 
	//GET by id
	@RequestMapping(value = "/city/getCity/{id}", method = RequestMethod.GET, produces = "application/json")  
	public ResponseEntity<Object> getCityById(@PathVariable("id") Integer id)   
	{  
		try {
			City city = this.cityService.getCityById(id);
			CityOutputDto cityOutput = new CityOutputDto();
			cityOutput.setCity(city);
			return new ResponseEntity<>(cityOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CityController.getCityById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}  
	
	@RequestMapping(value = "/city/search", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getCitis(@RequestBody CityFilterInputDTO cityFilterDto) {

		try {
			PagedResponse<City> cityData = this.cityService.getFilteredCities(cityFilterDto);

			List<CityDTO> cityOutPut = ((Collection<City>) cityData.getContent()).stream().map(element -> this.mapper.map(element, CityDTO.class))
					.collect(Collectors.toList());
			
			GenericPagedResponse<CityDTO> response = new GenericPagedResponse<CityDTO>(cityOutPut, cityData.getCurrentPage(),
					cityData.getTotalPages(), cityData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CityController.getCitis: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/city/list", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getCityList() {
		try {
			List<City> cityData = this.cityService.getAllCity();
			List<CityFilterInputDTO> cityOutput = cityData.stream().map(this::convertCityTODto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(cityOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CityController.getCityList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	private CityFilterInputDTO convertCityTODto(City city) {
		CityFilterInputDTO dto = new CityFilterInputDTO();
		dto.setCityId(city.getCityId());
		dto.setCityCode(city.getCityCode());
		dto.setName(city.getCityName());
		dto.setCntryId(city.getCtryId());
		dto.setRegnId(city.getRegnId());
		dto.setStateId(city.getStateId());

		return dto;
	}
	
	@RequestMapping(value = "/city/check", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> checkCityCodeExists(@RequestBody CityFilterInputDTO cityFilterInputDTO) {
		final boolean isStateCodeExist = this.cityService.checkCityCodeExists(cityFilterInputDTO.getCityCode(),cityFilterInputDTO.getStateId(),cityFilterInputDTO.getRegnId(),cityFilterInputDTO.getCntryId());
		return new ResponseEntity<>(new ResponseUtil(String.valueOf(isStateCodeExist), HttpStatus.OK.value()),
				HttpStatus.OK);
	}
	
	
}
