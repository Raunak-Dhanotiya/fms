package com.fms.app.helpdesk.controllers;

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

import com.fms.app.helpdesk.models.CostType;
import com.fms.app.helpdesk.services.CostTypeService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class CostTypeController {

	@Autowired
	CostTypeService costTypeService;
	private static final Logger logger = LogManager.getLogger(CostTypeController.class);

	@RequestMapping(value = "/costtype/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveCostType(@RequestBody CostType ct) {
	
		try {
			CostType costType = this.costTypeService.saveOrUpdate(ct);
			return new ResponseEntity<>(costType, HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CostTypeController.saveCostType: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/costtype/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllCostTypes() {
		try {
			List<CostType> data = this.costTypeService.getAllCostTypes();
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CostTypeController.getAllCostTypes: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/costtype/getAllPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllCostTypesPaginated(@RequestBody FilterDTOCopy filterDto) {
		try {
			PagedResponse<CostType> data = this.costTypeService.getAllCostTypesPaginated(filterDto);
			List<CostType> finalResult = ((Collection<CostType>) data.getContent()).stream().collect(Collectors.toList());
			GenericPagedResponse<CostType> response = new GenericPagedResponse<CostType>(finalResult, data.getCurrentPage(),
					data.getTotalPages(), data.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CostTypeController.getAllCostTypesPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/costtype/getByCostType/{costTypeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getByCostType(@PathVariable("costTypeId") int costTypeId) {
		try {
			CostType ctData = this.costTypeService.getByCostTypeId(costTypeId);
			return new ResponseEntity<>(ctData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CostTypeController.getByCostType: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/costtype/deleteByCostType/{costTypeId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteByCostTypeId(@PathVariable("costTypeId") int costTypeId) {
		try {
			CostType ctData = this.costTypeService.getByCostTypeId(costTypeId);
			this.costTypeService.deleteByCostType(ctData);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CostTypeController.deleteByCostTypeId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/costtype/checkExist/{costTypeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkIsExist(@PathVariable("costTypeId") String costTypeId) {
		try {
			final boolean isExists = this.costTypeService.checkCostTypeByCostType(costTypeId);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExists), HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CostTypeController.checkIsExist: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
