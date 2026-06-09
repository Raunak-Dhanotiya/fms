package com.fms.app.ppm.controllers;

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

import com.fms.app.ppm.models.PlanStep;
import com.fms.app.ppm.services.PlanStepServices;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class PlanStepController {

	@Autowired
	PlanStepServices planStepSrv;
	private static final Logger logger = LogManager.getLogger(PlanStepController.class);

	@RequestMapping(value = "/planStep/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> savePlanStep(@RequestBody PlanStep planStep) {

		try {
			this.planStepSrv.saveOrUpdate(planStep);
			return new ResponseEntity<>(planStep, HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanStepController.savePlanStep: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/planStep/getplanStepById/{planStepId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getPlanStepById(@PathVariable("planStepId") int planStepId) {
		try {
			PlanStep planStep = this.planStepSrv.getPlanStepById(planStepId);
			return new ResponseEntity<>(planStep, HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanStepController.getPlanStepById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/planStep/getAllByPlanId/{planId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllByPlanId(@PathVariable("planId") int planId) {

		try {
			List<PlanStep> planSteps = this.planStepSrv.getAllPlanSteps();
			if (planId > 0) {
				planSteps = planSteps.stream().filter(planStep -> planStep.getPlanId() == planId)
						.collect(Collectors.toList());

			}

			return new ResponseEntity<>(planSteps, HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanStepController.getAllByPlanId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/planStep/deletePlanStepById/{planStepId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> deletePlanStepById(@PathVariable("planStepId") int planStepId) {

		try {
			this.planStepSrv.deletePlanStep(planStepId);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanStepController.deletePlanStepById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/planStep/checkExist/{planId}/{stepCode}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkStepCodeExist(@PathVariable("planId") int planId, @PathVariable("stepCode") String stepCode) {
		try {
			final boolean isStepCodeExist = this.planStepSrv.checkStepCodeExist(planId,stepCode);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isStepCodeExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanStepController.checkStepCodeExist: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
