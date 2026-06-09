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

import com.fms.app.ACADPlugin.dto.ACADPluginBlInputDto;
import com.fms.app.ACADPlugin.dto.ACADPluginBlOutputDto;
import com.fms.app.ACADPlugin.services.ACADPluginBlService;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1/acad/bl")
public class ACADPluginBlController {
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	ACADPluginBlService blService;
	
	private static final Logger logger = LogManager.getLogger(ACADPluginBlController.class);

	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveBuilding(@RequestBody ACADPluginBlInputDto bl) {
		try {
			Bl record = this.blService.saveBuilding(bl);
			ACADPluginBlOutputDto result = this.mapper.map(record, ACADPluginBlOutputDto.class);
			return new ResponseEntity<>(new ResponseUtil<>(result, CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ACADPluginBlController.saveBuilding: " + stacktrace, e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/getall", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getBuildings() {
		try {
			List<Bl> records = this.blService.getBuildings();
			List<ACADPluginBlOutputDto> result = records.stream()
					.map(element -> this.mapper.map(element, ACADPluginBlOutputDto.class)).collect(Collectors.toList());
			return new ResponseEntity<>(
					new ResponseUtil<>(result, CommonConstant.SUCCESS_GET_RECORDS, HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ACADPluginBlController.getBuildings: " + stacktrace, e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/isexist", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> isBuildingExists(@RequestBody ACADPluginBlInputDto blFilterDto) {
		try {
			boolean isExist = false;
			if (blFilterDto.getSiteCode() != null && blFilterDto.getSiteCode().length() > 0
					&& blFilterDto.getBlCode() != null && blFilterDto.getBlCode().length() > 0) {
				isExist = this.blService.checkBlExistForBlCodeAndSiteCode(blFilterDto.getBlCode(),
						blFilterDto.getSiteCode());
			}
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExist), 200), HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ACADPluginBlController.isBuildingExists: " + stacktrace, e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
