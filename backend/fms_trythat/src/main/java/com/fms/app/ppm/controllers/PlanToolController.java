package com.fms.app.ppm.controllers;

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

import com.fms.app.ppm.models.PlanTool;
import com.fms.app.ppm.services.PlanToolServices;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class PlanToolController {

	@Autowired
	PlanToolServices planToolSrv;
	
	private static final Logger logger = LogManager.getLogger(PlanToolController.class);
	
	@RequestMapping(value = "/planTool/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveplanTool(@RequestBody PlanTool planTool) {

		try {
			this.planToolSrv.saveOrUpdate(planTool);
			return new ResponseEntity<>(new ResponseUtil("Data Record Saved", HttpStatus.OK.value()), HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanToolController.saveplanTool: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/planTool/getplanToolById/{planToolId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getplanToolById(@PathVariable("planToolId") int planToolId) {
		try {
			PlanTool planTool = this.planToolSrv.getById(planToolId);
			return new ResponseEntity<>(planTool, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanToolController.getplanToolById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/planTool/getByPlanStepId/{planStepId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllByPlanStepId(@PathVariable("planStepId") int planStepId) {
		try {
			List<PlanTool> planToolList = this.planToolSrv.getAllByPlanStepId(planStepId);
			return new ResponseEntity<>(planToolList, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanToolController.getAllByPlanStepId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/planTool/deleteplanToolById/{planToolId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> deletePlanToolById(@PathVariable("planToolId") int planToolId) {

		try {
			this.planToolSrv.deletePlanTool(planToolId);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanToolController.deletePlanToolById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/planTool/checkExist/{planStepId}/{toolId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkIsToolExist(@PathVariable("planStepId") int planStepId,
			@PathVariable("toolId") Integer toolId) {
		try {
			boolean isExist = this.planToolSrv.checkIsToolExist(planStepId,toolId);
			return new ResponseEntity<>(isExist, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanToolController.checkIsToolExist: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
