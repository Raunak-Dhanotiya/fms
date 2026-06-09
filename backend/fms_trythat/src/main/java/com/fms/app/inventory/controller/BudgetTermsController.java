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

import com.fms.app.inventory.model.BudgetTerms;
import com.fms.app.inventory.model.dto.BudgetTermOutputDto;
import com.fms.app.inventory.services.BudgetTermsService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1/budgetTerms")

public class BudgetTermsController {

	@Autowired
	ModelMapper mapper;
	
	@Autowired
	BudgetTermsService budgetTermsService;
	
	private static final Logger logger = LogManager.getLogger(BudgetTermsController.class);

	@RequestMapping(value = "/saveBudgetTerms", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> saveBudgetTerms(@RequestBody BudgetTerms budgetTerms)   
	{ 
		try {
			this.budgetTermsService.saveOrUpdate(budgetTerms);
			return new ResponseEntity<>(new ResponseUtil("Data Record Saved", HttpStatus.OK.value()), HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in BudgetTermsController.saveBudgetTerms: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllBudgetTerms() {

		try {
			List<BudgetTermOutputDto> data = this.budgetTermsService.getAllBudgetTerms();
			
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in BudgetTermsController.getAllBudgetTerms: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/getBudgetTermsById/{budgetTermId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getBudgetTermsById(@PathVariable("budgetTermId") int budgetTermId) {

		try {
			BudgetTermOutputDto data = this.budgetTermsService.getBudgetTermsById(budgetTermId);
			
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in BudgetTermsController.getBudgetTermsById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/deleteByBudgetTermId/{itemId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> deleteByItemId(@PathVariable("itemId") int budgetTermId) {
		try {
			this.budgetTermsService.deleteByBudgetTermId(budgetTermId);
			return new ResponseEntity<>(new ResponseUtil("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in BudgetTermsController.deleteByBudgetTermId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
