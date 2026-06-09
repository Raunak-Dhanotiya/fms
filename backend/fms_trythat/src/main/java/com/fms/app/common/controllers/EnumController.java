package com.fms.app.common.controllers;

import java.util.List;

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

import com.fms.app.common.models.Enums;
import com.fms.app.common.services.EnumsService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;


@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class EnumController {

	@Autowired
	EnumsService enumsService;
	
	private static final Logger logger = LogManager.getLogger(EnumController.class);
	
	@RequestMapping(value = "/enums/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getEnums(){
		try {
			List<Enums> enumRecords = this.enumsService.getEnums();
			return new ResponseEntity<>(enumRecords,HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EnumController.getEnums: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/enums/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveEnums(@RequestBody Enums enums) {
		try {
			this.enumsService.saveEnums(enums);
			return new ResponseEntity<>("Save successfully", HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EnumController.saveEnums: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/enums/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> DeleteEnums(@PathVariable("id") int id) {
		try {
			this.enumsService.delete(id);

			return new ResponseEntity<>("Record Deleted", HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EnumController.DeleteEnums: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/enums/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getEnumsById(@PathVariable("id") int id) {
		try {
			Enums rec = this.enumsService.getEnums(id);

			return new ResponseEntity<>(rec, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EnumController.getEnumsById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
