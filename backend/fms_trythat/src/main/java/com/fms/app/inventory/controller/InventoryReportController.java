package com.fms.app.inventory.controller;

import java.util.List;

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

import com.fms.app.inventory.model.CenterUsage;
import com.fms.app.inventory.model.dto.BudgetTermReportInputs;
import com.fms.app.inventory.services.InventoryReportService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1/inventoryReport")
public class InventoryReportController {
	private static final Logger logger = LogManager.getLogger(ItemController.class);

	@Autowired
	ModelMapper mapper;
	
	@Autowired
	InventoryReportService inventoryReportService;
	
	@RequestMapping(value = "/getByBudgetTermId/{budgetTermId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getByBudgetTermId(@PathVariable("budgetTermId") Integer budgetTermId) {

		try {
			List<BudgetTermReportInputs> data = inventoryReportService.getByBudgetTermId(budgetTermId);
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in InventoryReportController.getByBudgetTermId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/getByBudgetTermInputs", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getByBudgetTermInputs(@RequestBody BudgetTermReportInputs selectedBlFlIda) {
			
		try {
			List<BudgetTermReportInputs> data = inventoryReportService.getCenterBudgetByBlIdFlId(selectedBlFlIda);
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in InventoryReportController.getByCenterBudgetTermId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/getExpenseByBudgetTermBlFl", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getExpenseByBudgetTermBlFl(@RequestBody BudgetTermReportInputs budgetTInput) {
		
		try {
			List<CenterUsage> centerUsages = inventoryReportService.getCenterUsageExpense(budgetTInput);
			return new ResponseEntity<>(centerUsages, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in InventoryReportController.getExpenseByBudgetTermBlFl: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/getExpenseByBudgetTermBlFlItem", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getExpenseByBudgetTermBlFlItem(@RequestBody BudgetTermReportInputs budgetTInput) {
		
		try {
			List<CenterUsage> centerUsages = inventoryReportService.getCenterUsageItemExpense(budgetTInput);
			return new ResponseEntity<>(centerUsages, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in InventoryReportController.getExpenseByBudgetTermBlFlItem: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
