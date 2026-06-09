package com.fms.app.employee.controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

import com.fms.app.employee.models.EmStd;
import com.fms.app.employee.services.EmStdServiceImpl;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class EmStdController {
	
	
	@Autowired
	EmStdServiceImpl emStd;
	private static final Logger logger = LogManager.getLogger(EmStdController.class);
	
	@RequestMapping(value = "/emStd/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveEmStd(@RequestBody EmStd record) {
		try {
			this.emStd.saveEmStd(record);
			return new ResponseEntity<>(new ResponseUtil(CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmStdController.saveEmStd: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/emStd/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getEmStd() {
		try {
			List<EmStd> emStdRecords = this.emStd.getEmStd();
			return new ResponseEntity<>(emStdRecords, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmStdController.getEmStd: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/emStd/paginatedList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getEmStdPaginated(@RequestBody FilterDTOCopy filterDTO) {
		try {
			PagedResponse<EmStd> emStdDatas = this.emStd.getEmStdPaginated(filterDTO);
			List<EmStd> emStdRecords = ((Collection<EmStd>) emStdDatas.getContent()).stream().collect(Collectors.toList());
			GenericPagedResponse<EmStd> response = new GenericPagedResponse<EmStd>(emStdRecords, emStdDatas.getCurrentPage(),
					emStdDatas.getTotalPages(), emStdDatas.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmStdController.getEmStdPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/emStd/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> DeleteEmStd(@PathVariable("id") String id) {
		try {
			this.emStd.delete(id);
			return new ResponseEntity<>(new ResponseUtil("Delete successfully", 200), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmStdController.DeleteEmStd: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/emStd/check/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkEmStdExists(@PathVariable("id") String id) {
		try {
			final boolean isEmStdExist = this.emStd.checkStdExist(id);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isEmStdExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmStdController.checkEmStdExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
