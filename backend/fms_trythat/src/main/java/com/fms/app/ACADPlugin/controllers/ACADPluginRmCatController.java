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

import com.fms.app.ACADPlugin.dto.ACADPluginRmCat;
import com.fms.app.ACADPlugin.services.ACADPluginRmCatService;
import com.fms.app.spaceManagement.models.Rmcat;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1/acad/rmcat")
public class ACADPluginRmCatController {
	
	@Autowired
	ACADPluginRmCatService rmcatService;

	@Autowired
	ModelMapper mapper;

	private static final Logger logger = LogManager.getLogger(ACADPluginRmCatController.class);
	
	@RequestMapping(value = "/getall", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> getRmCats() {
		try {
			List<Rmcat> records = this.rmcatService.getRmCats();
			List<ACADPluginRmCat> result = records.stream()
					.map(element -> this.mapper.map(element, ACADPluginRmCat.class)).collect(Collectors.toList());
			return new ResponseEntity<>(
					new ResponseUtil<>(result, CommonConstant.SUCCESS_GET_RECORDS, HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ACADPluginRmCatController.getRmCats: " + stacktrace, e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
