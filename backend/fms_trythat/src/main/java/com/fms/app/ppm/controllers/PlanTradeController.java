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

import com.fms.app.ppm.models.PlanTrade;
import com.fms.app.ppm.services.PlanTradeServices;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class PlanTradeController {
	
	@Autowired
	PlanTradeServices planTradeSrv;
	
	private static final Logger logger = LogManager.getLogger(PlanTradeController.class);
	
	@RequestMapping(value = "/planTrade/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> savePlanTrade(@RequestBody PlanTrade planTrade) {

		try {
			this.planTradeSrv.saveOrUpdate(planTrade);
			return new ResponseEntity<>(new ResponseUtil("Data Record Saved", HttpStatus.OK.value()), HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanTradeController.savePlanTrade: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/planTrade/getPlanTradeById/{planTradeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getPlanTradeById(@PathVariable("planTradeId") int planTradeId) {
		try {
			PlanTrade planTrade = this.planTradeSrv.getById(planTradeId);
			return new ResponseEntity<>(planTrade, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanTradeController.getPlanTradeById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/planTrade/getByPlanStepId/{planStepId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllByPlanStepId(@PathVariable("planStepId") int planStepId) {
		try {
			List<PlanTrade> planTradeList = this.planTradeSrv.getAllByStepId(planStepId);
			return new ResponseEntity<>(planTradeList, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanTradeController.getAllByPlanStepId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/planTrade/deletePlanTradeById/{planTradeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> deletePlanTradeById(@PathVariable("planTradeId") int planTradeId) {

		try {
			this.planTradeSrv.deleteById(planTradeId);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanTradeController.deletePlanTradeById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/planTrade/checkExist/{planStepId}/{tradeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkIsTradeExist(@PathVariable("planStepId") int planStepId,
			@PathVariable("tradeId") Integer tradeId) {
		try {
			boolean isExist = this.planTradeSrv.checkIsTradeExist(planStepId,tradeId);
			return new ResponseEntity<>(isExist, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanTradeController.checkIsTradeExist: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
