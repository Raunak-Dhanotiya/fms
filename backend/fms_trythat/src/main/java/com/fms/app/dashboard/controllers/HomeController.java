package com.fms.app.dashboard.controllers;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.response.output.HomeDashboard;
import com.fms.app.sidenav.UserScreensService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1/home")
public class HomeController {

	@Autowired
	UserScreensService userService;
	
	private static final Logger logger = LogManager.getLogger(HomeController.class);

	@RequestMapping(value = "/index", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getDashboardByUsers(@RequestBody Map<String, Integer> userInput) {

		try {
			HomeDashboard d = new HomeDashboard();
			if (userInput.containsKey("userRole")) {
				d = this.userService.getUserDashboards(userInput.get("userRole"));
			}

			return new ResponseEntity<>(d, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in HomeController.getDashboardByUsers: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
