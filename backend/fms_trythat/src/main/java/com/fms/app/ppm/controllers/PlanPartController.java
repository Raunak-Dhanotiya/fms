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

import com.fms.app.ppm.models.PlanPart;
import com.fms.app.ppm.services.PlanPartServices;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class PlanPartController {

	@Autowired
	PlanPartServices planPartSrv;
	
	private static final Logger logger = LogManager.getLogger(PlanPartController.class);

	@RequestMapping(value = "/planPart/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveplanPart(@RequestBody PlanPart planPart) {

		try {
			this.planPartSrv.saveOrUpdate(planPart);
			return new ResponseEntity<>(new ResponseUtil("Data Record Saved", HttpStatus.OK.value()), HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanPartController.saveplanPart: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/planPart/getplanPartById/{planPartId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getplanPartById(@PathVariable("planPartId") int planPartId) {
		try {
			PlanPart planPart = this.planPartSrv.getById(planPartId);
			return new ResponseEntity<>(planPart, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanPartController.getplanPartById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/planPart/getByPlanStepId/{planStepId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllByPlanStepId(@PathVariable("planStepId") int planStepId) {
		try {
			List<PlanPart> planPartList = this.planPartSrv.getAllByPlanStepId(planStepId);
			return new ResponseEntity<>(planPartList, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanPartController.getAllByPlanStepId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/planPart/deleteplanPartById/{planPartId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> deleteplanPartById(@PathVariable("planPartId") int planPartId) {

		try {
			this.planPartSrv.deleteById(planPartId);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanPartController.deleteplanPartById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/planPart/checkExist/{planStepId}/{partId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkIsPartExist(@PathVariable("planStepId") int planStepId,
			@PathVariable("partId") Integer partId) {
		try {
			boolean isExist = this.planPartSrv.checkIsPartExist(planStepId,partId);
			return new ResponseEntity<>(isExist, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanPartController.checkIsPartExist: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
