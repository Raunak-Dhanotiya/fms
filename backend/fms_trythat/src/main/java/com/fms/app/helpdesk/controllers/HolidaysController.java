package com.fms.app.helpdesk.controllers;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fms.app.helpdesk.models.Holidays;
import com.fms.app.helpdesk.services.HolidayService;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@RestController 
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class HolidaysController {
	
	@Autowired  
	HolidayService service;
	private static final Logger logger = LogManager.getLogger(HolidaysController.class);
	
	@RequestMapping(value = "/holidays/saveholidays", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveHolidays(@RequestBody Holidays holidays) {
		try {
			this.service.saveOrUpdate(holidays);
			return new ResponseEntity<>(new ResponseUtil<>(holidays, CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()), HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in HolidaysController.saveHolidays: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}	

	@RequestMapping(value = "/holidays/getHolidaysList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getHolidaysList() {
		
		try {
			List<Holidays> holidaysData = this.service.getAllHolidays();
			
			return new ResponseEntity<>(holidaysData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in HolidaysController.getHolidaysList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/holidays/getHolidaysListPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getHolidaysListPaginated(@RequestBody FilterDTOCopy filterDto) {
		
		try {
			PagedResponse<Holidays> holidaysData = this.service.getAllHolidaysPaginated(filterDto);
			List<Holidays> finalResult = ((Collection<Holidays>) holidaysData.getContent()).stream().collect(Collectors.toList());
			GenericPagedResponse<Holidays> response = new GenericPagedResponse<Holidays>(finalResult, holidaysData.getCurrentPage(),
					holidaysData.getTotalPages(), holidaysData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in HolidaysController.getHolidaysListPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/holidays/getHolidays/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getHolidaysByCompIdList(@PathVariable("id") int id) {
		
		try {
			Holidays holidaysData = this.service.getAllHolidayById(id);
			
			return new ResponseEntity<>(holidaysData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in HolidaysController.getHolidaysByCompIdList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/holidays/getHolidayByDate/{date}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getCallTypeById(@PathVariable("date") String date) {
		
		try {
			System.out.println("In get Date");
			Holidays holidays = this.service.getHolidayByDate(date);
			
			return new ResponseEntity<>(holidays, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in HolidaysController.getCallTypeById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/holidays/deleteByHolidaysId/{holidaysId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteById(@PathVariable("holidaysId") Integer holidaysId) {
		try {
			System.out.println("In get Date");
			this.service.deleteHoliday(holidaysId);
		
			return new ResponseEntity<>(new ResponseUtil("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in HolidaysController.deleteById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	public Boolean getHoliday(Date date) {
		
		return this.service.checkHolidayDate(date);
	}
}
