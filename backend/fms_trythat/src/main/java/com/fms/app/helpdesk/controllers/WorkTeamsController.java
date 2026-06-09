package com.fms.app.helpdesk.controllers;

import java.util.List;

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

import com.fms.app.helpdesk.models.WorkTeams;
import com.fms.app.helpdesk.services.WorkTeamsSerrvices;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class WorkTeamsController {
	
	@Autowired
	WorkTeamsSerrvices workTeamsSerrvices;
	
	private static final Logger logger = LogManager.getLogger(WorkTeamsController.class);
	
	@RequestMapping(value = "/wt/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveWorkTeams(@RequestBody List<WorkTeams> dataRecord) {
		
		try {
			this.workTeamsSerrvices.saveOrUpdate(dataRecord);
			return new ResponseEntity<>(new ResponseUtil("Records saved Successfully", 200), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in WorkTeamsController.saveWorkTeams: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/wt/delete", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> deleteWorkTeams(@RequestBody List<WorkTeams> dataRecord) {
		try {
			dataRecord.forEach(workTeam -> {
				this.workTeamsSerrvices.delete(workTeam.getTeamId(),workTeam.getCfId(),workTeam.getEmId());
			});
			
			return new ResponseEntity<>(new ResponseUtil("Records Deleted Successfully", 200), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in WorkTeamsController.deleteWorkTeams: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
