package com.fms.app.spaceManagement.controllers;

import java.util.List;
import java.util.Map;

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

import com.fms.app.helpdesk.models.WorkTeams;
import com.fms.app.spaceManagement.models.Rm;
import com.fms.app.spaceManagement.models.RmTeams;
import com.fms.app.spaceManagement.models.dto.RmTeamsRoomInfoDTO;
import com.fms.app.spaceManagement.services.RmTeamsServices;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class RmTeamsController {
	@Autowired
	RmTeamsServices rmteamservice;
	private static final Logger logger = LogManager.getLogger(RmTeamsController.class);
	
	@RequestMapping(value = "/roomteam/getunassignedrooms/{teamId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getUnAssignedRooms(@PathVariable("teamId") int teamId) {
		try {
			List<RmTeamsRoomInfoDTO> listUnAssignedRooms = this.rmteamservice.getUnAssignedRooms(teamId);
			return new ResponseEntity<>(listUnAssignedRooms, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmTeamsController.getUnAssignedRooms: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/roomteam/getassignedrooms/{teamId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAssignedRooms(@PathVariable("teamId") int teamId) {
		try {
			List<RmTeams> listAssignedRooms = this.rmteamservice.getAssignedRooms(teamId);
			return new ResponseEntity<>(listAssignedRooms, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmTeamsController.getAssignedRooms: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/roomteam/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllRoomTeams() {
		try {
			List<RmTeams> ListTools = this.rmteamservice.getAll();
			return new ResponseEntity<>(ListTools, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmTeamsController.getAllRoomTeams: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/roomteam/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveTeam(@RequestBody RmTeams rmteam) {
		try {
			List<RmTeams> existingData = this.rmteamservice.findRmteam(rmteam);
			if(existingData.size()>0) {
				RmTeams existingRmTeamData = existingData.get(0);
				if(existingRmTeamData != null) {
					existingRmTeamData.setAllocation(rmteam.getAllocation() + existingRmTeamData.getAllocation());
					this.rmteamservice.saveOrUpdate(existingRmTeamData);
				}
			}
			else {
				this.rmteamservice.saveOrUpdate(rmteam);
			}
			return new ResponseEntity<>(rmteam, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmTeamsController.saveTeam: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/roomteam/delete", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> deleteWorkTeams(@RequestBody RmTeams dataRecord) {
		try {
			this.rmteamservice.delete(dataRecord.getBlId(),dataRecord.getFlId(),dataRecord.getRmId(),dataRecord.getTeamId());
			return new ResponseEntity<>(new ResponseUtil("Records Deleted Successfully", 200), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmTeamsController.deleteWorkTeams: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/roomteam/getunassignedrmteams/{teamId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getUnAssignedRmTeams(@PathVariable("teamId") int teamId) {
		try {
			List<RmTeamsRoomInfoDTO> listUnAssignedRooms = this.rmteamservice.getUnAssignedRmteams(teamId);
			return new ResponseEntity<>(listUnAssignedRooms, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmTeamsController.getUnAssignedRmTeams: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/roomteam/update", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> updatermTeam(@RequestBody RmTeams rmteam) {
		try {
			this.rmteamservice.updateRmteam(rmteam);
			return new ResponseEntity<>(rmteam, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmTeamsController.updatermTeam: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
