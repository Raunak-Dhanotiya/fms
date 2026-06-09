
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

import com.fms.app.inventory.model.CenterBudget;
import com.fms.app.inventory.services.CenterBudgetService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;


@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1/centerBudget")
public class CenterBudgetController {

	@Autowired
	ModelMapper mapper;
	
	@Autowired
	CenterBudgetService centerBudgetService;
	
	private static final Logger logger = LogManager.getLogger(CenterBudgetController.class);

	@RequestMapping(value = "/saveCenterBudget", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> saveCenterBudget(@RequestBody CenterBudget centerBudget)   
	{ 
		try {
			this.centerBudgetService.saveOrUpdate(centerBudget);
			return new ResponseEntity<>(new ResponseUtil("Data Record Saved", HttpStatus.OK.value()), HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CenterBudgetController.saveCenterBudget: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllCenterBudget() {

		try {
			List<CenterBudget> data = this.centerBudgetService.getAllCenterBudget();
			
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ItemController.getAllItem: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/getCenterBudgetById/{centerBudgetId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getCenterBudgetById(@PathVariable("centerBudgetId") int centerBudgetId) {

		try {
			CenterBudget data = this.centerBudgetService.getCenterBudgetById(centerBudgetId);
			
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CenterBudgetController.getCenterBudgetById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/deleteCenterBudgetById/{centerBudgetId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> deleteCenterBudgetById(@PathVariable("centerBudgetId") int centerBudgetId) {
		try {
			this.centerBudgetService.deleteCenterBudgetById(centerBudgetId);
			return new ResponseEntity<>(new ResponseUtil("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CenterBudgetController.deleteCenterBudgetById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
}
