package com.fms.app.ACADPlugin.controllers;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.ACADPlugin.dto.ACADPluginFlInputDto;
import com.fms.app.ACADPlugin.dto.ACADPluginFlOutputDto;
import com.fms.app.ACADPlugin.services.ACADPluginFlService;
import com.fms.app.spaceManagement.models.Fl;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1/acad/fl")
public class ACADPluginFlController {
	
	@Autowired
	ACADPluginFlService flService;

	@Autowired
	ModelMapper mapper;

	private static final Logger logger = LogManager.getLogger(ACADPluginFlController.class);
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveFloor(@RequestBody ACADPluginFlInputDto fl) {
		try {
			Fl record = this.flService.saveFloor(fl);
			ACADPluginFlOutputDto result = this.mapper.map(record, ACADPluginFlOutputDto.class);
			return new ResponseEntity<>(new ResponseUtil<>(result, CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ACADPluginFlController.saveFloor: " + stacktrace, e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getFloors(@RequestBody ACADPluginFlInputDto flFilter) {
		try {
			List<Fl> records = this.flService.getFloors(flFilter);
			List<ACADPluginFlOutputDto> result = records.stream()
					.map(element -> this.mapper.map(element, ACADPluginFlOutputDto.class)).collect(Collectors.toList());
			return new ResponseEntity<>(
					new ResponseUtil<>(result, CommonConstant.SUCCESS_GET_RECORDS, HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ACADPluginFlController.getFloors: " + stacktrace, e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/isexist", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> isFloorExists(@RequestBody ACADPluginFlInputDto flData) {
		try {
			boolean isExist = false;
			if (flData.getFlCode() != null && flData.getFlCode().length() > 0 && flData.getBlId() != null
					&& flData.getBlId() > 0) {
				isExist = this.flService.checkFlExistForFlCodeAndBlId(flData.getFlCode(), flData.getBlId());
			}
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExist), 200), HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ACADPluginFlController.isFloorExists: " + stacktrace, e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
