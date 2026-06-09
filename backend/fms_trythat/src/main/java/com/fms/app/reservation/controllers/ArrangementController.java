package com.fms.app.reservation.controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fms.app.reservation.models.Arrangement;
import com.fms.app.reservation.services.ArrangementService;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@RestController
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class ArrangementController {

	@Autowired
	ArrangementService arrangementService;
	
	private static final Logger logger = LogManager.getLogger(ArrangementController.class);

	@RequestMapping(value = "/arrangement/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveArrangement(@RequestBody Arrangement dataRecord) {
		try {
			this.arrangementService.saveOrUpdate(dataRecord);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(CommonConstant.SUCCESS_MSG), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ArrangementController.saveArrangement: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/arrangement/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getArrangementList() {
		try {
			List<Arrangement> arrangementData = this.arrangementService.getAll();
			return new ResponseEntity<>(arrangementData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ArrangementController.getArrangementList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/arrangement/getAllPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getArrangementListPaginated(@RequestBody FilterDTOCopy filterDto) {
		try {
			PagedResponse<Arrangement> arrangementData = this.arrangementService.getAllPaginated(filterDto);
			List<Arrangement> finalResult = ((Collection<Arrangement>) arrangementData.getContent()).stream().collect(Collectors.toList());
			GenericPagedResponse<Arrangement> response = new GenericPagedResponse<Arrangement>(finalResult, arrangementData.getCurrentPage(),
					arrangementData.getTotalPages(), arrangementData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ArrangementController.getArrangementListPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/arrangement/getByType/{arrangement_type}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getArrangementByType(@PathVariable("arrangement_type") String arrangementType) {
		try {
			Arrangement arrangement = this.arrangementService.getByType(arrangementType);
			return new ResponseEntity<>(arrangement, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ArrangementController.getArrangementByType: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/arrangement/isExist/{arrangement_type}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkArrangementExist(@PathVariable("arrangement_type") String arrangementType) {
		try {
			boolean isExist = this.arrangementService.checkArrangementTypeExist(arrangementType);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ArrangementController.checkArrangementExist: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
