package com.fms.app.appParams.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.appParams.models.Messages;
import com.fms.app.appParams.models.dto.MessagesDTO;
import com.fms.app.appParams.models.dto.MessagesOutPutDTO;
import com.fms.app.appParams.services.MessagesService;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.sidenav.FMProcesses;
import com.fms.app.sidenav.FMProcessesServiceImpl;
import com.fms.app.userModels.User;
import com.fms.app.userModels.dto.UserFilterInputDTO;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class MessagesController {
	@Autowired
	MessagesService msgService;
	@Autowired
	ModelMapper mapper;
	@Autowired
	FMProcessesServiceImpl processService;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	private static final Logger logger = LogManager.getLogger(MessagesController.class);
	
	@RequestMapping(value = "/msg/allMsgs", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> getAllMessages(){
		try {
			List<MessagesDTO> msgRecords = new ArrayList<MessagesDTO>();
			List<Messages> msgData = this.msgService.getAllMessages();
			if (!msgData.isEmpty()) {
				msgRecords = msgData.stream().map(element -> this.mapper.map(element, MessagesDTO.class))
						.collect(Collectors.toList());
			}
			return new ResponseEntity<>(msgRecords, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in MessagesController.getAllMessages: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/msg/allMsgsPaginated", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getAllMessagesPaginated(@RequestBody FilterDTOCopy filterDto){
		try {
			PagedResponse<Messages> msgData = this.msgService.getAllMessagesPaginated(filterDto);
			List<MessagesDTO> finalResult = ((Collection<Messages>) msgData.getContent()).stream().map(element -> this.mapper.map(element, MessagesDTO.class))
					.collect(Collectors.toList());
			GenericPagedResponse<MessagesDTO> response = new GenericPagedResponse<MessagesDTO>(finalResult, msgData.getCurrentPage(),
					msgData.getTotalPages(), msgData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in MessagesController.getAllMessagesPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/msg/getMsgsById", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getMsgsById(@RequestBody MessagesDTO msgInput){
		try {
			Messages msg = this.msgService.getMessagesById(msgInput.getMsgId());
			MessagesOutPutDTO msgRecord = new MessagesOutPutDTO();
			msgRecord = this.mapper.map(msg, MessagesOutPutDTO.class);
			return new ResponseEntity<>(msgRecord, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in MessagesController.getMsgsById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/msg/deleteMsg", method = RequestMethod.DELETE, produces = "application/json")
	private ResponseEntity<Object> deleteMessageById(@RequestBody MessagesDTO msgInput) {
		try {
			this.msgService.deleteMessageById(msgInput.getMsgId());
			return new ResponseEntity<>(new ResponseUtil("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in MessagesController.deleteMessageById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/msg/saveMsg", method = RequestMethod.POST,produces = "application/json")
	private ResponseEntity<Object> updateMessages(@RequestBody MessagesDTO dataRecord) {
		try {
			Messages msgRecord = this.mapper.map(dataRecord,Messages.class);
			this.msgService.updateMessages(msgRecord);
			return new ResponseEntity<>(new ResponseUtil(CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in MessagesController.updateMessages: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/msg/allProcesses", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> getAllProcesses(){
		try {
			List<FMProcesses> processData = this.processService.getFMProcesses();
			return new ResponseEntity<>(processData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in MessagesController.getAllProcesses: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/msg/checkMsgId/{msgId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkMsgIdExists(@PathVariable("msgId") int msgId) {
		boolean isExist = false;
		try {
			if (msgId > 0) {
				isExist = this.msgService.checkMsgIdExistForCode(msgId);
			}
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExist), 200), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in MessagesController.checkMsgIdExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
