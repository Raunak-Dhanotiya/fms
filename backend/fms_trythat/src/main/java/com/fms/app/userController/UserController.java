package com.fms.app.userController;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.employee.models.Employee;
import com.fms.app.employee.models.dto.EmployeeContact;
import com.fms.app.employee.models.dto.EmployeeDetails;
import com.fms.app.employee.models.dto.EmployeeLocation;
import com.fms.app.employee.models.dto.EmployeeOutput;
import com.fms.app.employee.services.EmployeeService;
import com.fms.app.notification.controllers.NotifyRequestor;
import com.fms.app.response.output.UserProfile;
import com.fms.app.userModels.User;
import com.fms.app.userModels.dto.UserDTO;
import com.fms.app.userModels.dto.UserFilterInputDTO;
import com.fms.app.userModels.dto.UserOutput;
import com.fms.app.userModels.dto.UserOutputDto;
import com.fms.app.userModels.dto.UserPasswrodInputDto;
import com.fms.app.userServices.UserServiceImpl;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.EncrytedPasswordUtils;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

/*
 * Need to remove in later onwards
 * 
<p> Just Test class to check API</p>
*/

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class UserController {
	@Autowired
	UserServiceImpl userService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	NotifyRequestor notifyRequestor;
	

	private static final Logger logger = LogManager.getLogger(UserController.class);
	
	@RequestMapping(value = "/user/users", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getUsers(@RequestBody UserFilterInputDTO userFilterDto) {

		try {
			List<User> userDatas = this.userService.getFilteredUsers(userFilterDto);
//			if (userFilterDto != null) {
//				List<User> userDatas = this.userService.getAllUsers();
//		
//			if (userFilterDto.getId()!=null && userFilterDto.getId() > 0) {
//				userDatas = userDatas.stream().filter(user -> user.getUserId() == userFilterDto.getId())
//						.collect(Collectors.toList());
//			}
//			if (userFilterDto.getUserRole() != null && userFilterDto.getUserRole().length() > 1) {
//				userDatas = userDatas.stream().filter(user -> user.getUserroles().getRoleName().equals(userFilterDto.getUserRole()))
//						.collect(Collectors.toList());
//			}
//			if (userFilterDto.getStatus() != null && userFilterDto.getStatus().length() > 1) {
//				userDatas = userDatas.stream().filter(user -> user.getUserStatus().equals(userFilterDto.getStatus()))
//						.collect(Collectors.toList());
///		}
			
			
			List<UserDTO> usetOutPut = userDatas.stream().map(element -> this.mapper.map(element, UserDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(usetOutPut, HttpStatus.OK);


		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserController.getUsers: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/user/usersPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getUsersPaginated(@RequestBody UserFilterInputDTO userFilterDto) {
		try {
			PagedResponse<User> userDatas = this.userService.getFilteredUsersPaginated(userFilterDto);
			List<UserDTO> finalResult = ((Collection<User>) userDatas.getContent()).stream().map(element -> this.mapper.map(element, UserDTO.class))
					.collect(Collectors.toList());
			GenericPagedResponse<UserDTO> response = new GenericPagedResponse<UserDTO>(finalResult, userDatas.getCurrentPage(),
					userDatas.getTotalPages(), userDatas.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserController.getUsersPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/user/id/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getUserById(@PathVariable("id") Integer id) {


		try {
			User usr = this.userService.getUser(id);
			if (usr != null) {
				final Employee empData = usr.getEmEmployee();
				UserOutputDto userOutputDto = new UserOutputDto();
				UserOutput userOutput = this.mapper.map(usr, UserOutput.class);
				userOutputDto.setUser(userOutput);
				EmployeeOutput empOutput = new EmployeeOutput();
				if (empData != null) {
					EmployeeDetails empDetails = this.mapper.map(empData, EmployeeDetails.class);
//					if (empData.getEmPhoto() > 0 && empData.getDoc() != null) {
//						empDetails.setEmPhoto(0);
//					}
					EmployeeLocation emplocation = this.mapper.map(empData, EmployeeLocation.class);
					EmployeeContact empContact = this.mapper.map(empData, EmployeeContact.class);

					empOutput.setEmployeeContact(empContact);
					empOutput.setEmployeeDetails(empDetails);
					empOutput.setEmployeeLocation(emplocation);
					empOutput.setEm(empData);

				}
				userOutputDto.setEmployee(empOutput);
				userOutputDto.setNewRecord(false);
				//
				return new ResponseEntity<>(userOutputDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new ResponseUtil("User not found", HttpStatus.OK.value()), HttpStatus.OK);
			}
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserController.getUserById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/user/list", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getUsersList() {
		try {
			List<User> userDatas = this.userService.getAllUsers();
			List<UserFilterInputDTO> userOutput = userDatas.stream().map(this::convertUserTODto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(userOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserController.getUsersList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/user/listPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getUsersListPaginated(@RequestBody FilterDTOCopy filterDto) {
		try {
			PagedResponse<User> userDatas = this.userService.getAllUsersPaginated(filterDto);
			List<UserFilterInputDTO> finalResult = ((Collection<User>) userDatas.getContent()).stream().map(this::convertUserTODto)
					.collect(Collectors.toList());
			GenericPagedResponse<UserFilterInputDTO> response = new GenericPagedResponse<UserFilterInputDTO>(finalResult, userDatas.getCurrentPage(),
					userDatas.getTotalPages(), userDatas.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserController.getUsersListPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/user/save", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> saveUserRecord(@RequestBody UserOutputDto userOutputDto) {
		try {
			if (userOutputDto != null) {
				UserOutput useroutput = userOutputDto.getUser();
				User user = (this.mapper.map(useroutput, User.class));
				if (userOutputDto.isNewRecord()) {
					user.setDatePwdChanged(new Date());
					user.setUserPwd(EncrytedPasswordUtils.encrytePassword(CommonConstant.DEFAUL_PWD));
//					
//					String mail = userOutputDto.getUser().getUserName();
//					String pswd = CommonConstant.DEFAUL_PWD;
//					
//					Company dataRecord = this.cService.getCompany(0);
//					
//					String compEmail = dataRecord.getEmail();
//					
//					notifyRequestor.notifyUser(mail,pswd,compEmail);
					
				}
				else {
					user = getUserfromDTO(user);
				}
				saveEmployee(userOutputDto.getEmployee(), user);
				User isSave = this.userService.saveUser(user);
				if (isSave != null) {
					return new ResponseEntity<>(new ResponseUtil("Data Record Saved", HttpStatus.OK.value()),
							HttpStatus.OK);
				} else {
					return new ResponseEntity<>(
							new ResponseUtil("Unable to save the user request", HttpStatus.INTERNAL_SERVER_ERROR.value()),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}

			}

			return new ResponseEntity<>("Unable to process the request", HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserController.saveUserRecord: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/user/check/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkUserExists(@PathVariable("id") String id) {
		try {
			final boolean isUserExist = this.userService.userExists(id);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isUserExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserController.checkUserExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/auth/pwd", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> checkUserExists(@RequestBody UserPasswrodInputDto userPwdInput) {
		try {
			if (userPwdInput != null && userPwdInput.getUserName() != null) {
				User userData = this.userService.getUser(userPwdInput.getUserName());
				if (userData != null) {
					userData.setDatePwdChanged(userPwdInput.convertStringToDate(userPwdInput.getUserDatePwdChanged()));
					final String updatedPwd = EncrytedPasswordUtils.encrytePassword(userPwdInput.getUserNewPwd().trim());
					userData.setUserPwd(updatedPwd);
					this.userService.saveUser(userData);
					return new ResponseEntity<>(new ResponseUtil(CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()),
							HttpStatus.OK);
				}
			}
			return new ResponseEntity<>(
					new ResponseUtil(CommonConstant.UNABLE_TO_PROCESS_MSG, HttpStatus.BAD_REQUEST.value()),
					HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserController.checkUserExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@Transactional
	@RequestMapping(value = "/user/profile/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getUserProfile(@PathVariable("id") Integer id) {
		try {
			User userData = this.userService.getUser(id);
			UserProfile finalResult = this.mapper.map(userData, UserProfile.class);
			if (userData.getEmEmployee() != null && userData.getEmEmployee().getBlId() != null && userData.getEmEmployee().getBlId() > 0) {
				finalResult.setBlName(userData.getEmEmployee().getBl().getBlName());
				finalResult.setBlId(userData.getEmEmployee().getBlId());
			}
			if (userData.getEmEmployee() != null && userData.getEmEmployee().getFlId() != null && userData.getEmEmployee().getFlId() > 0) {
				finalResult.setFlName(userData.getEmEmployee().getFl().getFlName());
				finalResult.setFlId(userData.getEmEmployee().getFlId());
			}

			if (userData.getEmEmployee() != null && userData.getEmEmployee().getRmId() != null &&  userData.getEmEmployee().getRmId() > 0) {
				finalResult.setRmName(userData.getEmEmployee().getRm().getRmName());
				finalResult.setRmId(userData.getEmEmployee().getRmId());
			}
			if (userData.getEmEmployee() != null) {
				finalResult.setName(userData.getEmEmployee().getFirstName() + " " + userData.getEmEmployee().getLastName());
				String initials = userData.getEmEmployee().getFirstName().charAt(0) + ""
						+ userData.getEmEmployee().getLastName().charAt(0);
				finalResult.setInitials(initials);
			}
			if (userData.getEmEmployee() != null && userData.getEmEmployee().getDoc() != null) {
				finalResult.setEmPhoto(CommonUtil.decompressBytes(userData.getEmEmployee().getDoc().getFileContent()));
			}
			if(userData.getUserRoleId() != null && userData.getUserRoleId() != 0 ) {
				finalResult.setUserRole(userData.getUserroles().getRoleName());
			}

			return new ResponseEntity<>(finalResult, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserController.getUserProfile: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	private void saveEmployee(EmployeeOutput empOut, User user) {
		if (empOut != null) {
			Employee emRecord = this.mapper.map(empOut.getEmployeeDetails(), Employee.class);
			this.mapper.map(empOut.getEmployeeLocation(), emRecord);
			if (empOut.getEmployeeContact() != null) {
				this.mapper.map(empOut.getEmployeeContact(), emRecord);
//				if(emRecord.getBl() != null) {
//					emRecord.getFl().setBlId(emRecord.getBlId());
//					emRecord.getRm().setFlId(emRecord.getFlId());
//					emRecord.getRm().setBlId(emRecord.getBlId());
//				}
			}
			emRecord.setBl(null);
			emRecord.setFl(null);
			emRecord.setRm(null);
			emRecord.setDivision(null);
			emRecord.setDepartment(null);
			emRecord.setSubDepartment(null);
			Employee empData = this.employeeService.saveEmployee(emRecord);
			user.setEmId(empData.getEmId());
			user.setEmEmployee(empData);
		}
	}
	private UserFilterInputDTO convertUserTODto(User user) {
		UserFilterInputDTO dto = new UserFilterInputDTO();
		dto.setId(user.getUserId());
		dto.setName(user.getUserName());

//		dto.setName(user.getEmEmployee() != null
//				? user.getEmEmployee().getFirstName().trim() + " " + user.getEmEmployee().getLastName().trim()
//				: "");
	
		if(user.getUserroles() != null) {
			  dto.setUserRole(user.getUserroles().getRoleName());
		}
       dto.setStatus(user.getUserStatus());
       dto.setUserName(user.getUserName());
		return dto;
	}


	private User getUserfromDTO(User userData) {
		User userRecord = this.userService.getUser(userData.getUserName());
		userRecord.setDeviceId(userData.getDeviceId());
		userRecord.setUserRoleId(userData.getUserRoleId());
		userRecord.setUserStatus(userData.getUserStatus());
		userRecord.setIpAddress(userData.getIpAddress());
		userRecord.setTechnicianId(userData.getTechnicianId());
		return userRecord;
	}

}
