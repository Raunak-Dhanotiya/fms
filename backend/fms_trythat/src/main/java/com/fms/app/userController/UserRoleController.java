package com.fms.app.userController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.userModels.User;
import com.fms.app.userModels.UserRoles;
import com.fms.app.userModels.dto.UserDTO;
import com.fms.app.userServices.UserRolesServiceImpl;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class UserRoleController {

	@Autowired
	UserRolesServiceImpl userRole;
	
	private static final Logger logger = LogManager.getLogger(UserRoleController.class);

	@RequestMapping(value = "/userRole/all", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getUserRoles(@RequestBody FilterDTOCopy filterDto) {
		try {
			PagedResponse<UserRoles> userRoleRecords = this.userRole.getAllUserRolesPaginated(filterDto);
			List<UserRoles> finalResult = ((Collection<UserRoles>) userRoleRecords.getContent()).stream().collect(Collectors.toList());
			GenericPagedResponse<UserRoles> response = new GenericPagedResponse<UserRoles>(finalResult, userRoleRecords.getCurrentPage(),
					userRoleRecords.getTotalPages(), userRoleRecords.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserRoleController.getUserRoles: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/userRole/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveUserRole(@RequestBody UserRoles record) {
		try {			
			this.userRole.saveUserRole(record);
			return new ResponseEntity<>(new ResponseUtil("Save successfully", 200), HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserRoleController.saveUserRole: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/userRole/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> DeleteUserRoles(@PathVariable("id") int id) {
		try {
			this.userRole.delete(id);

			return new ResponseEntity<>(new ResponseUtil("Delete successfully", 200), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserRoleController.DeleteUserRoles: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/userRole/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getUserRoleById(@PathVariable("id") int id) {
		try {
			UserRoles rec = this.userRole.getUserRoles(id);

			return new ResponseEntity<>(rec, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserRoleController.getUserRoleById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/userRole/check/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkUserExists(@PathVariable("id") int id) {
		try {
			final boolean isUserExist = this.userRole.checkRoleExist(id);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isUserExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserRoleController.checkUserExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/userRole/getList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getUserRolesList() {
		try {
			
			List<UserRoles> finalResult = this.userRole.getUserRoles();
			return new ResponseEntity<>(finalResult, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserRoleController.getUserRolesList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
