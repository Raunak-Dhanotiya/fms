package com.fms.app.spaceManagement.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fms.app.spaceManagement.models.RmTrans;
import com.fms.app.spaceManagement.models.dto.RmTransDTO;
import com.fms.app.spaceManagement.services.RmTransService;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@RestController
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class RmTransController {
	
	@Autowired  
	RmTransService rmtransservice;
	
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(RmTransController.class);
	
	@RequestMapping(value = "/rmtrans/save", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> saveRmTrans(@RequestBody RmTransDTO data)   
	{ 
		try {
	    RmTrans record = this.mapper.map(data,RmTrans.class); 
	    RmTrans sr = this.rmtransservice.savermtrans(record);
	    return new ResponseEntity<>(sr, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmTransController.saveRmTrans: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rmtrans/all", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> getAllRmTRans(){
		try {
			List<RmTrans> tData = this.rmtransservice.getAllRmTrans();
			List<RmTransDTO> result = tData.stream().map(each -> this.mapper.map(each, RmTransDTO.class)).collect(Collectors.toList());
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmTransController.getAllRmTRans: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
