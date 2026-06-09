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

import com.fms.app.ACADPlugin.dto.ACADPluginEquipmentInputDto;
import com.fms.app.ACADPlugin.dto.ACADPluginEquipmentOutputDto;
import com.fms.app.ACADPlugin.services.ACADPluginEquipmentService;
import com.fms.app.Equipment.models.Equipment;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1/acad/equipment")
public class ACADPluginEquipmentController {
	
	@Autowired
	ACADPluginEquipmentService equipmentService;

	@Autowired
	ModelMapper mapper;

	private static final Logger logger = LogManager.getLogger(ACADPluginEquipmentController.class);
	
	@RequestMapping(value = "/get", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getEquipments(@RequestBody ACADPluginEquipmentInputDto filter) {
		try {
			List<Equipment> records = this.equipmentService.getEquipments(filter);
			List<ACADPluginEquipmentOutputDto> result = records.stream()
					.map(element -> this.mapper.map(element, ACADPluginEquipmentOutputDto.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(
					new ResponseUtil<>(result, CommonConstant.SUCCESS_GET_RECORDS, HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ACADPluginEquipmentController.getEquipments: " + stacktrace, e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveEquipment(@RequestBody ACADPluginEquipmentInputDto dataRecord) {
		try {
			Equipment eq = new Equipment(dataRecord);
			Equipment eqData = this.equipmentService.getEquipmentByEqId(eq.getEqId());
			if (eqData != null) {
				this.equipmentService.updateAssetViaAcad(eq);
			} else {
				this.equipmentService.saveEquipment(eq);
			}
			ACADPluginEquipmentOutputDto result = this.mapper.map(eq, ACADPluginEquipmentOutputDto.class);
			return new ResponseEntity<>(
					new ResponseUtil<>(result, CommonConstant.SUCCESS_GET_RECORDS, HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ACADPluginEquipmentController.saveEquipment: " + stacktrace, e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
