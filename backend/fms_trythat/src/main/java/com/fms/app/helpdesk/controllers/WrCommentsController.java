package com.fms.app.helpdesk.controllers;

import java.util.Calendar;
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

import com.fms.app.helpdesk.models.WrComments;
import com.fms.app.helpdesk.models.dto.wrCommentsOutPutDTO;
import com.fms.app.helpdesk.services.WrCommentsService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class WrCommentsController {

	@Autowired
	WrCommentsService wrCommentsService;
	
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(WrCommentsController.class);


	@RequestMapping(value = "/wrComments/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveWrComments(@RequestBody WrComments dataRecord) {
		try {
			java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			java.sql.Time currentTime = new java.sql.Time(Calendar.getInstance().getTime().getTime());
			if(dataRecord.getCommentId() == 0) {
				dataRecord.setCommentDate(currentDate);
				dataRecord.setCommentTime(currentTime);
			}
			
			WrComments data = wrCommentsService.saveOrUpdate(dataRecord);
			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in WrCommentsController.saveWrComments: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/wrComments/getById/{wrId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getWrCommentsByWrId(@PathVariable("wrId") int wrId) {
		try {
			List<WrComments> data = this.wrCommentsService.getWrCommentsByWrId(wrId);
		List<wrCommentsOutPutDTO> listWrComments = 	data.stream().map(each -> this.mapper.map(each, wrCommentsOutPutDTO.class)).collect(Collectors.toList());

			return new ResponseEntity<>(listWrComments, HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in WrCommentsController.getWrCommentsByWrId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/wrComments/getByCommentId/{commentId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getWrCommentById(@PathVariable("commentId") int commentId) {
		try {
			WrComments data = this.wrCommentsService.getById(commentId);
			wrCommentsOutPutDTO dto = this.mapper.map(data, wrCommentsOutPutDTO.class);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in WrCommentsController.getWrCommentById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
