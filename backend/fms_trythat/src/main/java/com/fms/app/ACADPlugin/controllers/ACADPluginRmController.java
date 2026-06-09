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

import com.fms.app.ACADPlugin.dto.ACADPluginRmInputDto;
import com.fms.app.ACADPlugin.dto.ACADPluginRmOutputDto;
import com.fms.app.ACADPlugin.services.ACADPluginRmService;
import com.fms.app.spaceManagement.models.Rm;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1/acad/rm")
public class ACADPluginRmController {
	
	@Autowired
	ACADPluginRmService rmService;

	@Autowired
	ModelMapper mapper;

	private static final Logger logger = LogManager.getLogger(ACADPluginRmController.class);
	
	@RequestMapping(value = "/get", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getRooms(@RequestBody ACADPluginRmInputDto rmFilterDto) {
		try {
			List<Rm> records = this.rmService.getRooms(rmFilterDto);
			List<ACADPluginRmOutputDto> result = records.stream()
					.map(element -> this.mapper.map(element, ACADPluginRmOutputDto.class)).collect(Collectors.toList());
			return new ResponseEntity<>(
					new ResponseUtil<>(result, CommonConstant.SUCCESS_GET_RECORDS, HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ACADPluginRmController.getRooms: " + stacktrace, e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveRoom(@RequestBody ACADPluginRmInputDto dataRecord) {
		try {
			Rm roomdetails = new Rm(dataRecord);
			Rm rmData = this.rmService.getRmByBlIdAndFlIdAndRmCode(dataRecord.getBlId(), dataRecord.getFlId(),
					dataRecord.getRmCode());
			if (rmData != null) {
				this.rmService.udpateRoomViaAcad(roomdetails);
			} else {
				roomdetails.setRmId(0);
				this.rmService.saveRoom(roomdetails);
			}
			ACADPluginRmOutputDto result = this.mapper.map(roomdetails, ACADPluginRmOutputDto.class);
			return new ResponseEntity<>(new ResponseUtil<>(result, CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ACADPluginRmController.saveRoom: " + stacktrace, e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
