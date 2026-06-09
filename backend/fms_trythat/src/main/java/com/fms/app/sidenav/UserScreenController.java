package com.fms.app.sidenav;

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

import com.fms.app.response.input.ScreenAssignInput;
import com.fms.app.response.output.UserScreensOutput;
import com.fms.app.response.output.UserSreenProcs;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class UserScreenController {

	@Autowired
	UserScreensService userScreenSrv;
	@Autowired
	ScreenServiceImpl screenSrv;
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(UserScreenController.class);

	@RequestMapping(value = "/user/screen/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getUserScreen() {
		try {
			List<UserScreens> records = this.userScreenSrv.getUserScreens();
			return new ResponseEntity<>(records, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserScreenController.getUserScreen: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/user/screen/search", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getUserScreens(@RequestBody ScreenAssignInput input) {
		try {
			if (input != null) {
				List<UserScreens> records = this.userScreenSrv.getUserScreensByUserRole(input.getUserRoleId());
				List<Screen> screenRecords = this.screenSrv.getScreen();

				List<UserScreensOutput> finalResults = records.stream().map(i -> convertUserScreenToOut(i, screenRecords))
						.collect(Collectors.toList());
				return new ResponseEntity<>(finalResults, HttpStatus.OK);
			}

			return new ResponseEntity<>(new ResponseUtil("Invalid input", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserScreenController.getUserScreens: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/user/screen/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveUserScreen(@RequestBody UserSreenProcs record) {
		try {
			if (record != null && record.getUserProcs().size() > 0) {
				List<UserScreensOutput> dataRecords = record.getUserProcs();
				dataRecords.stream().forEach(data -> {
					UserScreens rec = this.mapper.map(data, UserScreens.class);
					this.userScreenSrv.saveUserScreens(rec);
				});
				return new ResponseEntity<>(new ResponseUtil(CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()),
						HttpStatus.OK);
			}

			return new ResponseEntity<>(
					new ResponseUtil(CommonConstant.UNABLE_TO_PROCESS_MSG, HttpStatus.INTERNAL_SERVER_ERROR.value()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserScreenController.saveUserScreen: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/user/screen/delete", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> DeleteUserScreen(@RequestBody UserSreenProcs record) {
		try {
			if (record != null && record.getUserProcs().size() > 0) {
				List<UserScreensOutput> dataRecords = record.getUserProcs();
				dataRecords.stream().forEach(data -> {
					UserScreens rec = this.mapper.map(data, UserScreens.class);
					this.userScreenSrv.delete(rec);
				});
				return new ResponseEntity<>(new ResponseUtil(CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()),
						HttpStatus.OK);
			}

			return new ResponseEntity<>(
					new ResponseUtil(CommonConstant.UNABLE_TO_PROCESS_MSG, HttpStatus.INTERNAL_SERVER_ERROR.value()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserScreenController.DeleteUserScreen: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/user/screen/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> DeleteUserScreen(@PathVariable("id") int id) {
		try {
			if (id > 0) {
				this.userScreenSrv.delete(id);
				return new ResponseEntity<>("Record Deleted", HttpStatus.OK);
			}
			return new ResponseEntity<>("Bad Request", HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserScreenController.DeleteUserScreen: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/user/screen/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getUserScreenById(@PathVariable("id") int id) {
		try {
			UserScreens rec = this.userScreenSrv.getUserScreens(id);

			return new ResponseEntity<>(rec, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UserScreenController.getUserScreenById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	private UserScreensOutput convertUserScreenToOut(UserScreens data, List<Screen> screenRecords) {
//		Screen rec = screenRecords.stream().filter(item -> item.getScreenNum() == data.getScreenNum()).findFirst()
//				.get();
		Screen rec = data.getScreen();
		UserScreensOutput userOut = new UserScreensOutput();
		userOut.setProcessId(data.getProcessId());
		userOut.setSubProcessId(data.getSubProcessId());
		userOut.setScreenNum(data.getScreenNum());
		userOut.setUserRoleId(data.getUserRoleId());
		userOut.setUserScreensNum(data.getUserScreensNum());
		userOut.setScreenName(rec.getScreenName());
		userOut.setProcessCode(data.getFmProcess().getTitle());
		if(data.getFmSubProcess() != null) {
			userOut.setSubProcessCode(data.getFmSubProcess().getTitle());
		}
		

		return userOut;
	}
}
