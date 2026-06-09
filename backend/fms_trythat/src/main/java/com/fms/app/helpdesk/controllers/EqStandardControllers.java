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

import com.fms.app.helpdesk.models.EqStandard;
import com.fms.app.helpdesk.models.dto.EqStdFilterDTO;
import com.fms.app.helpdesk.services.EqStandardServices;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;


@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class EqStandardControllers {
	@Autowired
	EqStandardServices eqStdService;
	
	private static final Logger logger = LogManager.getLogger(EqStandardControllers.class);
	
	@RequestMapping(value = "/eqStd/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllEqStandards() {

		try {
			List<EqStandard> eqData = this.eqStdService.getAllEqStandards();

			return new ResponseEntity<>(eqData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EqStandardControllers.getAllEqStandards: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/eqStd/getAllPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllEqStandardsPaginated(@RequestBody FilterDTOCopy filterDto) {

		try {
			PagedResponse<EqStandard> eqData = this.eqStdService.getAllEqStandardsPaginated(filterDto);
			List<EqStandard> finalResult = ((Collection<EqStandard>) eqData.getContent()).stream().collect(Collectors.toList());
			GenericPagedResponse<EqStandard> response = new GenericPagedResponse<EqStandard>(finalResult, eqData.getCurrentPage(),
					eqData.getTotalPages(), eqData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EqStandardControllers.getAllEqStandardsPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/eqStd/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveEqStandard(@RequestBody EqStandard dataRecord) {
		try {
			dataRecord  = this.eqStdService.saveOrUpdate(dataRecord); 
			return new ResponseEntity<>(dataRecord, HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EqStandardControllers.saveEqStandard: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/eqStd/geteqStdById/{eqStdId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getEqStandardById(@PathVariable("eqStdId") int eqStdId) {
		try {
			EqStandard data = this.eqStdService.getEqStandardById(eqStdId);
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EqStandardControllers.getEqStandardById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/eqStd/geteqStdByEqstd/{eqStd}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getEqStandardByEqstd(@PathVariable("eqStd") String eqStd) {
		try {
			List<EqStandard> data = this.eqStdService.getEqStandardByEqstd(eqStd);
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EqStandardControllers.getEqStandardByEqstd: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/eqStd/check/{eqStd}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkEqStandardExists(@PathVariable("eqStd") int eqStd) {
		try {
			final boolean isResourceExist = this.eqStdService.checkEqStandardExists(eqStd);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isResourceExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EqStandardControllers.checkEqStandardExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/eqStd/deleteById/{eqStdId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteByEqStd(@PathVariable("eqStdId") int eqStdId) {
		
		try {
			EqStandard eqStdData = this.eqStdService.getEqStandardById(eqStdId);
			this.eqStdService.deleteEqStandard(eqStdData);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EqStandardControllers.deleteByEqStd: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/eqStd/getAll", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllEquipments(@RequestBody EqStdFilterDTO filter) {

		try {
			List<EqStandard> eqData = this.eqStdService.getAllEqStandards();

			if (filter.getEqStd() != null && filter.getEqStd().length() > 1) {
				eqData = eqData.stream().filter(eq ->eq.getEqStd().equals(filter.getEqStd()))
						.collect(Collectors.toList());
			}
			
			return new ResponseEntity<>(eqData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in EqStandardControllers.getAllEquipments: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}
	
}
