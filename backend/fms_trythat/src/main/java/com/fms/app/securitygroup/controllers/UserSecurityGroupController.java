package com.fms.app.securitygroup.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fms.app.securitygroup.models.UserSecurityGroup;
import com.fms.app.securitygroup.services.UserSecurityGroupServices;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@RestController
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class UserSecurityGroupController {
	
	@Autowired
	UserSecurityGroupServices userSecurityGroupServices;
	
	private static final Logger logger = LogManager.getLogger(UserSecurityGroupController.class);
	
	@RequestMapping(value = "/usg/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveUserSevurityGroup(@RequestBody List<UserSecurityGroup> dataRecord) {
		try {
			this.userSecurityGroupServices.saveOrUpdate(dataRecord);
			return new ResponseEntity<>(new ResponseUtil("Records saved Successfully", 200), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserSecurityGroupController.saveUserSevurityGroup: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/usg/delete", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> deleteUserSevurityGroup(@RequestBody List<UserSecurityGroup> dataRecord) {
		try {
			dataRecord.forEach(usg -> {
				this.userSecurityGroupServices.delete(usg.getSecurityGroupId(), usg.getUserId(), usg.getUserRoleId());
			});
			
			return new ResponseEntity<>(new ResponseUtil("Records Deleted Successfully", 200), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserSecurityGroupController.deleteUserSevurityGroup: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
