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

import com.fms.app.helpdesk.models.Trades;
import com.fms.app.helpdesk.services.TradesService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class TradesController {

	@Autowired
	TradesService tradesService;
	
	private static final Logger logger = LogManager.getLogger(TradesController.class);

	@RequestMapping(value = "/trade/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveTrade(@RequestBody Trades dataRecord) {
		try {
			dataRecord = this.tradesService.saveOrUpdate(dataRecord);
			return new ResponseEntity<>(dataRecord, HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TradesController.saveTrade: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/trade/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllTrades() {

		try {
			List<Trades> tradesData = this.tradesService.getAllTrades();
			return new ResponseEntity<>(tradesData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TradesController.getAllTrades: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/trade/getAllPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllTradesPaginated(@RequestBody FilterDTOCopy filterDto) {

		try {
			PagedResponse<Trades> tradesData = this.tradesService.getAllTradesPaginated(filterDto);
			List<Trades> finalResult = ((Collection<Trades>) tradesData.getContent()).stream().collect(Collectors.toList());
			GenericPagedResponse<Trades> response = new GenericPagedResponse<Trades>(finalResult, tradesData.getCurrentPage(),
					tradesData.getTotalPages(), tradesData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TradesController.getAllTradesPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/trade/getTradeById/{tradeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getTradeById(@PathVariable("tradeId") int tradeId) {
		try {
			Trades data = this.tradesService.getTradeById(tradeId);
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TradesController.getTradeById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/trade/deleteTradeById/{tradeId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteTradeById(@PathVariable("tradeId") int tradeId) {
		try {
			Trades tradeData = this.tradesService.getTradeById(tradeId);
			this.tradesService.deleteTradeById(tradeData);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TradesController.deleteTradeById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/trade/check/{tradeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkTradeExists(@PathVariable("tradeId") int tradeId) {
		try {
			final boolean isTradeExist = this.tradesService.checkTradeExists(tradeId);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isTradeExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TradesController.checkTradeExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
