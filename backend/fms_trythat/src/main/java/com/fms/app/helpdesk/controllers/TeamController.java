package com.fms.app.helpdesk.controllers;

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

import com.fms.app.helpdesk.models.Team;
import com.fms.app.helpdesk.models.dto.AssignUnAssignWorkTeamsDto;
import com.fms.app.helpdesk.services.TeamServices;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.dto.BLDTO;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class TeamController {
	@Autowired
	TeamServices teamServices;
	
	private static final Logger logger = LogManager.getLogger(TeamController.class);
	
	@RequestMapping(value = "/team/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveTeam(@RequestBody Team team) {
		try {
			teamServices.saveOrUpdate(team);
			return new ResponseEntity<>(team, HttpStatus.OK);	
		}
		catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TeamController.saveTeam: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/team/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllTeams() {
		try {
			List<Team> ListTools = this.teamServices.getAll();
			return new ResponseEntity<>(ListTools, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TeamController.getAllTeams: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/team/getAllPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllTeamsPaginated(@RequestBody  FilterDTOCopy filterDto) {
		try {
			PagedResponse<Team> teamData = this.teamServices.getAllPaginated(filterDto);
			List<Team> teamOutPut = ((Collection<Team>) teamData.getContent()).stream().collect(Collectors.toList());
			GenericPagedResponse<Team> response = new GenericPagedResponse<Team>(teamOutPut, teamData.getCurrentPage(),
					teamData.getTotalPages(), teamData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TeamController.getAllTeamsPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/team/getById/{teamId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getTeamById(@PathVariable("teamId") int teamId) {

		try {
			Team record = this.teamServices.getById(teamId);
			return new ResponseEntity<>(record, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TeamController.getTeamById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/team/delete/{teamId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> deleteTeamById(@PathVariable("teamId") int teamId) {
		try {
			this.teamServices.deleteById(teamId);
			return new ResponseEntity<>(new ResponseUtil("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TeamController.deleteTeamById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/team/check/{teamId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkTeamExists(@PathVariable("teamId") int teamId) {
		try {
			final boolean isExist = this.teamServices.checkToolExists(teamId);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TeamController.checkTeamExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/team/getUnAssignedTeams", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getUnAssignedTeams(@RequestBody AssignUnAssignWorkTeamsDto dataRecord) {
		try {
			List<Team> listUnAssignedTeams = this.teamServices.getUnAssignedTeams(dataRecord.getCfId(),
					dataRecord.getEmId(), dataRecord.getEnumValue());
			return new ResponseEntity<>(listUnAssignedTeams, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TeamController.getUnAssignedTeams: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/team/getAssignedTeams", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAssignedTeams(@RequestBody AssignUnAssignWorkTeamsDto dataRecord) {
		try {
			List<Team> listAssignedTeams = this.teamServices.getAssignedTeams(dataRecord.getCfId(),
					dataRecord.getEmId());
			return new ResponseEntity<>(listAssignedTeams, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TeamController.getAssignedTeams: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/team/getTeamsHavingtechnician", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getTeamsHavingtechnician(){
		try {
			List<Team> teamsList =  this.teamServices.getTeamsHavingTechnicians();
			return new ResponseEntity<>(teamsList, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TeamController.getTeamsHavingtechnician: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/team/getTeamsHavingemployee", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getTeamsHavingemployes(){
		try {
			List<Team> teamsList =  this.teamServices.getTeamsHavingEmployes();
			return new ResponseEntity<>(teamsList, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TeamController.getTeamsHavingemployes: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
}
