																															package com.fms.app.svg.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fms.app.appParams.models.AppParams;
import com.fms.app.appParams.services.AppParamsService;
import com.fms.app.spaceManagement.models.Fl;
import com.fms.app.spaceManagement.models.Rm;
import com.fms.app.spaceManagement.models.dto.FLDTO;
import com.fms.app.spaceManagement.models.dto.FLFilterInputDTO;
import com.fms.app.spaceManagement.models.dto.RMDTO;
import com.fms.app.spaceManagement.services.FlService;
import com.fms.app.svg.dto.SVGFileDTO;

import com.fms.app.svg.services.SvgViewService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@RestController
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")

	
public class SvgViewController {
	
	@Autowired  
	FlService flService;
	
	@Autowired
	SvgViewService svgviewService;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
    Environment env;
	
	@Autowired
	AppParamsService apParamService;
	private static final Logger logger = LogManager.getLogger(SvgViewController.class);
	
	@RequestMapping(value = "/svg/getsvgfile", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getSVGfile( @RequestBody FLFilterInputDTO flInput)
	{  
		try {
			String rootDir = System.getProperty("catalina.base");//use while build
			String folderLocation = String.format("%s/webapps/fms/assets/svg/", rootDir);
			
			Fl fl = this.flService.getFlByFlId(flInput.getFlId());
			if(env.getProperty("spring.profiles.active").equals("dev")) {
				AppParams apParamData = this.apParamService.getAppParamsByParamId("svg_save_location");
				folderLocation = apParamData.getParamValue(); 
			}
			if(fl.getSvgName()!=null) {
				String fileName = fl.getSvgName()+".svg";
				File file = new File(folderLocation+fileName);
				BufferedReader br = new BufferedReader(new FileReader(file));
				String st = br.lines().collect(Collectors.joining(System.lineSeparator()));
				SVGFileDTO fileDetails = new SVGFileDTO(fileName,st);
		        return new ResponseEntity<>(fileDetails, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(new ResponseUtil<>("No file Found", HttpStatus.CONFLICT.value()), HttpStatus.OK);
			}
			
			
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SvgViewController.getSVGfile: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/svg/getroomsbysvgname", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getRoomsBySVG( @RequestBody FLFilterInputDTO flInput)
	{  
		try {
			String svgName = flInput.getSvgName();
			List<Rm> rmData = this.svgviewService.getRmBySvgName(svgName);
			List<RMDTO> rmOutPut = new ArrayList<RMDTO>();
			if(rmData != null) {
				rmOutPut= rmData.stream().map(element -> this.mapper.map(element, RMDTO.class))
						.collect(Collectors.toList());
			}
			return new ResponseEntity<>(rmOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SvgViewController.getRoomsBySVG: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}

	}
	
	@RequestMapping(value = "/svg/getfloorbysvgname", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getFloorBySVG( @RequestBody FLFilterInputDTO flInput)
	{  
		try {
			String svgName = flInput.getSvgName();
			List<Fl> flData = this.flService.getFlBySvgName(svgName);
			List<FLDTO> flOutPut = new ArrayList<FLDTO>();
			if(flData != null) {
				flOutPut= flData.stream().map(element -> this.mapper.map(element, FLDTO.class))
						.collect(Collectors.toList());
			}
			return new ResponseEntity<>(flOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SvgViewController.getFloorBySVG: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
}
