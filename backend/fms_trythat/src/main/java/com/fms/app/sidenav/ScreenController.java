package com.fms.app.sidenav;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fms.app.response.input.ScreenAssignInput;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class ScreenController {

	@Autowired
	ScreenServiceImpl screenService;
	private static final Logger logger = LogManager.getLogger(ScreenController.class);

	@RequestMapping(value = "/screen/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getScreens() {
		try {
			List<Screen> records = this.screenService.getScreen();
			return new ResponseEntity<>(records, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ScreenController.getScreens: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/screen/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveScreens(@RequestBody Screen record) {
		try {
			if (record != null) {
				record.setScreenNavUrl(normalizeScreenNavUrl(record.getScreenNavUrl()));
			}
			this.screenService.saveScreen(record);
			return new ResponseEntity<>("Save successfully", HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ScreenController.saveScreens: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}


	private String normalizeScreenNavUrl(String link) {
		if (link == null) {
			return "/welcome";
		}
		String rawLink = link.trim();
		if (rawLink.isEmpty()) {
			return "/welcome";
		}
		String[] parts = rawLink.split("\\?", 2);
		String normalizedPath = parts[0];
		try {
			normalizedPath = java.net.URLDecoder.decode(normalizedPath, java.nio.charset.StandardCharsets.UTF_8.name());
		} catch (Exception ignored) {
			normalizedPath = parts[0];
		}
		normalizedPath = normalizedPath.replace('\\', '/').replaceAll("/+$", "").replaceAll("^/+", "").replaceAll("/+", "/");
		normalizedPath = normalizedPath.replaceAll("\\s+", "-").toLowerCase();
		java.util.Map<String, String> aliasMap = new java.util.HashMap<String, String>();
		aliasMap.put("dashboard", "space-dashboard");
		aliasMap.put("dashboards", "welcome");
		aliasMap.put("index", "welcome");
		aliasMap.put("home", "welcome");
		aliasMap.put("background-loc", "back-loc");
		aliasMap.put("romms", "room-category");
		aliasMap.put("room-cat", "room-category");
		if (aliasMap.containsKey(normalizedPath)) {
			normalizedPath = aliasMap.get(normalizedPath);
		}
		if (normalizedPath.isEmpty()) {
			return "/welcome";
		}
		return "/" + normalizedPath + (parts.length > 1 && !parts[1].isEmpty() ? "?" + parts[1] : "");
	}
	@RequestMapping(value = "/screen/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> DeleteScreens(@PathVariable("id") int id) {
		try {
			if (id > 0) {
				this.screenService.delete(id);
				return new ResponseEntity<>("Record Deleted", HttpStatus.OK);
			}
			return new ResponseEntity<>("Bad Request", HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ScreenController.DeleteScreens: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/screen/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getScreensById(@PathVariable("id") int id) {
		try {
			Screen rec = this.screenService.getScreen(id);

			return new ResponseEntity<>(rec, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ScreenController.getScreensById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/screen/get-unassign", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getUserUnAssignScreens(@RequestBody ScreenAssignInput input) {
		try {
			if (input != null) {

				List<Screen> rec = this.screenService.getUserUnAssignScreens(input.getUserRoleId());
				return new ResponseEntity<>(rec, HttpStatus.OK);
			}
			return new ResponseEntity<>("Unable to process the request.", HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ScreenController.getUserUnAssignScreens: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}


	}
}
