package com.fms.app.helpdesk.controllers;

import java.util.Calendar;
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

import com.fms.app.helpdesk.models.RequestTrades;
import com.fms.app.helpdesk.models.dto.RequestTradesDto;
import com.fms.app.helpdesk.services.RequestTradesServices;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class RequestTradesController {
	@Autowired
	RequestTradesServices requestTradesSrv;

	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(RequestTradesController.class);

	@RequestMapping(value = "/requestTrade/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveRequestTrades(@RequestBody RequestTradesDto requestTradesDto) {
		try {
			RequestTrades requestTrades = this.mapper.map(requestTradesDto, RequestTrades.class);
			java.sql.Date assignDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			java.sql.Time assignTime = new java.sql.Time(Calendar.getInstance().getTime().getTime());

			if (requestTrades.getRequestTradeId() == null || requestTrades.getRequestTradeId() == 0) {
				requestTrades.setDateAssign(assignDate);
				requestTrades.setTimeAssign(assignTime);
			}
			this.requestTradesSrv.saveOrUpdate(requestTrades);
			return new ResponseEntity<>(requestTrades, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestTradesController.saveRequestTrades: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/requestTrade/getById/{requestTradeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getByRequestTradeId(@PathVariable("requestTradeId") int requestTradeId) {
		try {
			RequestTrades requestTrade = this.requestTradesSrv.getById(requestTradeId);
			RequestTradesDto requestTradesDto = this.mapper.map(requestTrade, RequestTradesDto.class);
			return new ResponseEntity<>(requestTradesDto, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestTradesController.getByRequestTradeId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/requestTrade/getAllByRequestId/{requestId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getByAllByRequestId(@PathVariable("requestId") int requestId) {
		try {
			List<RequestTrades> requestTradeList = this.requestTradesSrv.getAllByRequestId(requestId);
			List<RequestTradesDto> requestTradesDtoList = requestTradeList.stream()
					.map(requestTrade -> this.mapper.map(requestTrade, RequestTradesDto.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(requestTradesDtoList, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestTradesController.getByAllByRequestId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/requestTrade/delete/{requestTradeId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteByRequestTradeId(@PathVariable("requestTradeId") int requestTradeId) {
		try {
			this.requestTradesSrv.deleteById(requestTradeId);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestTradesController.deleteByRequestTradeId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/requestTrade/checkExist/{requestId}/{tradeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkIsTradeExists(@PathVariable("requestId") Integer requestId,
			@PathVariable("tradeId") Integer tradeId) {
		try {
			boolean isExist = this.requestTradesSrv.checkTradeExists(requestId, tradeId);
			return new ResponseEntity<>(isExist, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestTradesController.checkIsTradeExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
