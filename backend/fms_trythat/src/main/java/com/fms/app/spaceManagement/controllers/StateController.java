package com.fms.app.spaceManagement.controllers;

import java.util.Collection;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.spaceManagement.models.State;
import com.fms.app.spaceManagement.models.dto.StateDTO;
import com.fms.app.spaceManagement.models.dto.StateFilterInputDTO;
import com.fms.app.spaceManagement.models.dto.StateOutputDto;
import com.fms.app.spaceManagement.services.StateService;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class StateController {

	@Autowired  
	StateService stateService;
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(StateController.class);
	
	//save state
	@RequestMapping(value = "/state/saveState", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveState(@RequestBody State state)   
	{ 
		try {
	    stateService.saveOrUpdate(state);  
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in StateController.saveState: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ResponseUtil<>(state, CommonConstant.SUCCESS_MSG,HttpStatus.OK.value()), HttpStatus.OK);
	}
		
	//delete city
	@DeleteMapping("/api/v1/deletestate")  
	private void deleteCtry(@RequestBody State state)   
	{  
		try {
		stateService.delete(state);  
		}
		catch (Exception e) {
			System.out.println("Failed");
		}
	} 
	
	//GET by id
	@RequestMapping(value = "/state/getState/{id}", method = RequestMethod.GET, produces = "application/json")  
	public ResponseEntity<Object> getStateById(@PathVariable("id") Integer id)   
	{  
		try {
			State state = this.stateService.getStateById(id);
			StateOutputDto stateOutput = new StateOutputDto();
			stateOutput.setState(state);
			return new ResponseEntity<>(stateOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in StateController.getStateById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}  

	@RequestMapping(value = "/state/search", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getStates(@RequestBody StateFilterInputDTO stateFilterDto) {

		try {
			PagedResponse<State> stateData = this.stateService.getFilteredStates(stateFilterDto);
			List<StateDTO> stateOutPut = ((Collection<State>) stateData.getContent()).stream().map(element -> this.mapper.map(element, StateDTO.class))
					.collect(Collectors.toList());
			GenericPagedResponse<StateDTO> response = new GenericPagedResponse<StateDTO>(stateOutPut, stateData.getCurrentPage(),
					stateData.getTotalPages(), stateData.getTotalElements());
			
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in StateController.getStates: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/state/list", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getStateList() {
		try {
			List<State> stateData = this.stateService.getAllState();
			List<StateFilterInputDTO> stateOutput = stateData.stream().map(this::convertStateTODto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(stateOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in StateController.getStateList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	private StateFilterInputDTO convertStateTODto(State state) {
		StateFilterInputDTO dto = new StateFilterInputDTO();
		dto.setStateId(state.getStateId());
		dto.setName(state.getStateName());
		dto.setStateCode(state.getStateCode());
		dto.setCtryId(state.getCtryId());
		dto.setRegnId(state.getRegnId());

		return dto;
	}
	
	@RequestMapping(value = "/state/check", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> checkStateCodeExists(@RequestBody StateFilterInputDTO stateFilterDto) {
		try {
			final boolean isStateCodeExist = this.stateService.checkStateCodeExists(stateFilterDto.getStateCode(),stateFilterDto.getRegnId(),stateFilterDto.getCtryId());
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isStateCodeExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in StateController.checkStateCodeExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
