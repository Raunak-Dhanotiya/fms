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

import com.fms.app.helpdesk.models.Parts;
import com.fms.app.helpdesk.services.PartsService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class PartsController {

	@Autowired
	PartsService partsService;
	
	private static final Logger logger = LogManager.getLogger(PartsController.class);

	@RequestMapping(value = "/parts/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveParts(@RequestBody Parts dataRecord) {
		
		try {
			Parts part = this.partsService.saveOrUpdate(dataRecord);
			return new ResponseEntity<>(part, HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PartsController.saveParts: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/parts/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllParts() {
		try {
			List<Parts> data = this.partsService.getAllParts();
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PartsController.getAllParts: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/parts/getAllPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllPartsPaginated(@RequestBody FilterDTOCopy filterDto) {
		try {
			PagedResponse<Parts> data = this.partsService.getAllPartsPaginated(filterDto);
			List<Parts> finalResult = ((Collection<Parts>) data.getContent()).stream().collect(Collectors.toList());
			GenericPagedResponse<Parts> response = new GenericPagedResponse<Parts>(finalResult, data.getCurrentPage(),
					data.getTotalPages(), data.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PartsController.getAllPartsPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/parts/getByPartCode/{partId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getByPartCode(@PathVariable("partId") Integer partId) {

		try {
			Parts partData = this.partsService.getByPartId(partId);
			return new ResponseEntity<>(partData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PartsController.getByPartCode: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/parts/deleteByPartCode/{partId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteByPartCode(@PathVariable("partId") Integer partId) {
		try {
			Parts partData = this.partsService.getByPartId(partId);
			this.partsService.deleteByPartCode(partData);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PartsController.deleteByPartCode: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/parts/checkExist/{partCode}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkIsExist(@PathVariable("partCode") String partCode) {
		try {
			final boolean isExists = this.partsService.isExistsPartsByPartCode(partCode);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExists), HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PartsController.checkIsExist: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
