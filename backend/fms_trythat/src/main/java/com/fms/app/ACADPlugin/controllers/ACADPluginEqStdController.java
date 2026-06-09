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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.ACADPlugin.dto.ACADPluginEqStd;
import com.fms.app.ACADPlugin.services.ACADPluginEqStdService;
import com.fms.app.helpdesk.models.EqStandard;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1/acad/eqstd")
public class ACADPluginEqStdController {
	
	@Autowired
	ACADPluginEqStdService eqStdService;
	
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(ACADPluginEqStdController.class);
	
	@RequestMapping(value = "/getall", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getEquipmentStandards() {
		try {
			List<EqStandard> records = this.eqStdService.getEqStds();
			List<ACADPluginEqStd> result = records.stream()
					.map(element -> this.mapper.map(element, ACADPluginEqStd.class)).collect(Collectors.toList());
			return new ResponseEntity<>(
					new ResponseUtil<>(result, CommonConstant.SUCCESS_GET_RECORDS, HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ACADPluginEqStdController.getEquipmentStandards: " + stacktrace, e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
