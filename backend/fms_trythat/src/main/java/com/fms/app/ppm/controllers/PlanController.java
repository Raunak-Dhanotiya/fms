package com.fms.app.ppm.controllers;


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

import com.fms.app.ppm.models.Plan;
import com.fms.app.ppm.services.PlanServices;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class PlanController {

	@Autowired
	PlanServices planSrv;
	
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(PlanController.class);

	@RequestMapping(value = "/plan/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> savePlan(@RequestBody Plan plan) {
		
		try {
			this.planSrv.saveOrUpdate(plan);
			return new ResponseEntity<>(plan, HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanController.savePlan: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}

	@RequestMapping(value = "/plan/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllPlans() {

		try {
			List<Plan> plansList = this.planSrv.getAllPlans();

			return new ResponseEntity<>(plansList, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanController.getAllPlans: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/plan/getAllPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllPlansPaginated(@RequestBody FilterDTOCopy filterDto) {
		try {
			PagedResponse<Plan> plansList = this.planSrv.getAllPlansPaginated(filterDto);
			List<Plan> finalResult = ((Collection<Plan>) plansList.getContent()).stream().collect(Collectors.toList());
			GenericPagedResponse<Plan> response = new GenericPagedResponse<Plan>(finalResult, plansList.getCurrentPage(),
					plansList.getTotalPages(), plansList.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanController.getAllPlansPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/plan/getPlanById/{planId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getPlanById(@PathVariable("planId") int planId) {
		try {
			Plan plan = this.planSrv.getPlanById(planId);
			return new ResponseEntity<>(plan, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanController.getPlanById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/plan/deletePlanById/{planId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> deletePlanById(@PathVariable("planId") int planId) {

		try {
			this.planSrv.deletePlanById(planId);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanController.deletePlanById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/plan/checkExist/{planName}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkPlanExists(@PathVariable("planName") String planName) {
		try {
			final boolean isPlanNameExist = this.planSrv.checkStepCodeExist(planName);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isPlanNameExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanController.checkPlanExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
