package com.fms.app.sidenav;

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

import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class UserProcessesController {

	@Autowired
	FMProcessesServiceImpl userProcesService;
	
	private static final Logger logger = LogManager.getLogger(UserProcessesController.class);

	@RequestMapping(value = "/userPro/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getEnums() {
		try {
			List<FMProcesses> enumRecords = this.userProcesService.getFMProcesses();
			return new ResponseEntity<>(enumRecords, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserProcessesController.getEnums: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/userPro/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveEnums(@RequestBody FMProcesses record) {
		try {
			this.userProcesService.saveFMProcesses(record);
			return new ResponseEntity<>("Save successfully", HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserProcessesController.saveEnums: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/userPro/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> DeleteEnums(@PathVariable("id") String id) {
		try {
			this.userProcesService.delete(id);

			return new ResponseEntity<>("Record Deleted", HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserProcessesController.DeleteEnums: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/userPro/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getEnumsById(@PathVariable("id") String id) {
		try {
			FMProcesses rec = this.userProcesService.getFMProcesses(id);

			return new ResponseEntity<>(rec, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserProcessesController.getEnumsById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
