package com.fms.app.appParams.controllers;
import java.util.ArrayList;
import java.util.Collection;
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

import com.fms.app.appParams.models.AppParams;
import com.fms.app.appParams.models.dto.AppParamsDTO;
import com.fms.app.appParams.services.AppParamsService;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class AppParamsController {
	
	@Autowired
	AppParamsService apService;
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	private static final Logger logger = LogManager.getLogger(AppParamsController.class);
	
	@RequestMapping(value = "/ap/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllAppParams(){
		try {
			List<AppParamsDTO> apRecords = new ArrayList<AppParamsDTO>();
			List<AppParams> apData = this.apService.getAllAppParams();
			if (!apData.isEmpty()) {
				apRecords = apData.stream().map(element -> this.mapper.map(element, AppParamsDTO.class))
						.collect(Collectors.toList());
			}
			return new ResponseEntity<>(apRecords, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in AppParamsController.getAllAppParams: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/ap/allPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllAppParamsPaginated(@RequestBody FilterDTOCopy filterDto){
		try {
			PagedResponse<AppParams> apData = this.apService.getAllAppParamsPaginated(filterDto);
			List<AppParamsDTO> finalResult = ((Collection<AppParams>) apData.getContent()).stream().map(element -> this.mapper.map(element, AppParamsDTO.class))
					.collect(Collectors.toList());
			GenericPagedResponse<AppParamsDTO> response = new GenericPagedResponse<AppParamsDTO>(finalResult, apData.getCurrentPage(),
					apData.getTotalPages(), apData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in AppParamsController.getAllAppParamsPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/ap/get/{paramId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAppParamById(@PathVariable("paramId") int paramId){
		try {
			AppParamsDTO apRecord = new AppParamsDTO();
			AppParams apData = this.apService.getAppParamsById(paramId);
			if (apData!=null) {
				apRecord = this.mapper.map(apData, AppParamsDTO.class);
			}
			return new ResponseEntity<>(apRecord, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in AppParamsController.getAppParamById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/ap/getByCode/{paramCOde}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAppParamByCode(@PathVariable("paramCOde") String paramCOde){
		try {
			AppParamsDTO apRecord = new AppParamsDTO();
			AppParams apData = this.apService.getAppParamsByParamId(paramCOde);
			if (apData!=null) {
				apRecord = this.mapper.map(apData, AppParamsDTO.class);
			}
			return new ResponseEntity<>(apRecord, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in AppParamsController.getAppParamByCode: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/ap/delete/{paramId}", method = RequestMethod.DELETE, produces = "application/json")
	private ResponseEntity<Object> deleteAppParamById(@PathVariable("paramId") int paramId) {
		try {
			this.apService.deleteAppParamById(paramId);
			return new ResponseEntity<>(new ResponseUtil("Record Deleted", 200), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in AppParamsController.deleteAppParamById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/ap/save", method = RequestMethod.POST)
	public ResponseEntity<Object> saveAppParam(@RequestBody AppParamsDTO dataRecord) {
		try {
			AppParams apRecord = this.mapper.map(dataRecord,AppParams.class);
			this.apService.updateAppParams(apRecord);;
			return new ResponseEntity<>(new ResponseUtil(CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in AppParamsController.saveAppParam: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/ap/check/{paramId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkAppParamExists(@PathVariable("paramId") int paramId) {
		try {
			boolean isExist = false;
			if (paramId > 0) {
				isExist = this.apService.checkAppParamExistForCode(paramId);
			}
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExist), 200), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in AppParamsController.checkAppParamExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/ap/getbyparamid/{paramId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAppParamByParamId(@PathVariable("paramId") String paramId){
		try {
			AppParamsDTO apRecord = new AppParamsDTO();
			AppParams apData = this.apService.getAppParamsByParamId(paramId);
			if (apData!=null) {
				apRecord = this.mapper.map(apData, AppParamsDTO.class);
			}
			return new ResponseEntity<>(apRecord, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in AppParamsController.getAppParamByParamId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
