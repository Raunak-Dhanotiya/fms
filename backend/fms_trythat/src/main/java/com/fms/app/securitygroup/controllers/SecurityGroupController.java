package com.fms.app.securitygroup.controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fms.app.securitygroup.models.SecurityGroup;
import com.fms.app.securitygroup.models.dto.AssignUnAssignSgDto;
import com.fms.app.securitygroup.services.SecurityGroupServices;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@RestController
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class SecurityGroupController {

	@Autowired
	SecurityGroupServices securityGroupServices;
	
	private static final Logger logger = LogManager.getLogger(SecurityGroupController.class);

	@RequestMapping(value = "/sg/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveSevurityGroup(@RequestBody SecurityGroup dataRecord) {
		try {
			this.securityGroupServices.saveOrUpdate(dataRecord);
			return new ResponseEntity<>(new ResponseUtil<>(CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()), HttpStatus.OK);			
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SecurityGroupController.saveSevurityGroup: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/sg/getSgList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllSg() {
		try {
			List<SecurityGroup> dataRecords = this.securityGroupServices.getAll();
			return new ResponseEntity<>(dataRecords, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SecurityGroupController.getAllSg: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/sg/getSgListPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllSg(@RequestBody FilterDTOCopy filterDto) {
		try {
			PagedResponse<SecurityGroup> dataRecords = this.securityGroupServices.getAllPaginated(filterDto);
			List<SecurityGroup> finalResult = ((Collection<SecurityGroup>) dataRecords.getContent()).stream().collect(Collectors.toList());
			GenericPagedResponse<SecurityGroup> response = new GenericPagedResponse<SecurityGroup>(finalResult, dataRecords.getCurrentPage(),
					dataRecords.getTotalPages(), dataRecords.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SecurityGroupController.getAllSg: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/sg/getSgBySecurityGroupId/{securityGroupId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getSgBySecurityGroupId(@PathVariable("securityGroupId") Integer securityGroupId) {
		try {
			SecurityGroup sgData = this.securityGroupServices.getBySecurityGroupId(securityGroupId);
			return new ResponseEntity<>(sgData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SecurityGroupController.getSgBySecurityGroupId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/sg/check/{groupName}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkIsShiftExists(@PathVariable("groupName") String groupName) {
		
		try {
			boolean isExist = false;
			if (groupName != null && groupName.length() > 0) {
				isExist = this.securityGroupServices.checkIsSgtExists(groupName);
			}
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExist), 200), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SecurityGroupController.checkIsShiftExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/sg/getUnAssignedSg", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getUnAssignedSg(@RequestBody AssignUnAssignSgDto dataRecord) {
		try {
			List<SecurityGroup> listUnAssignedSg = this.securityGroupServices.getUnAssignedSg(dataRecord.getUserId(),
					dataRecord.getUserRoleId());
			return new ResponseEntity<>(listUnAssignedSg, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SecurityGroupController.getUnAssignedSg: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/sg/getAssignedSg", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAssignedSg(@RequestBody AssignUnAssignSgDto dataRecord) {
		try {
			List<SecurityGroup> listAssignedSg = this.securityGroupServices.getAssignedSg(dataRecord.getUserId(),
					dataRecord.getUserRoleId());
			return new ResponseEntity<>(listAssignedSg, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SecurityGroupController.getAssignedSg: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
