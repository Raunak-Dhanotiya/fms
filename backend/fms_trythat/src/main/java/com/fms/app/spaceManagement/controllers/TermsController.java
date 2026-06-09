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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.spaceManagement.models.RmTrans;
import com.fms.app.spaceManagement.models.Terms;
import com.fms.app.spaceManagement.models.dto.TermsInputDTO;
import com.fms.app.spaceManagement.services.RmTransService;
import com.fms.app.spaceManagement.services.TermsService;
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
public class TermsController {
	
	@Autowired  
	TermsService termsservice;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	RmTransService rmtransservice;
	
	private static final Logger logger = LogManager.getLogger(TermsController.class);

	@RequestMapping(value = "/terms/all", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> getAllTerms(){
		try {
			List<Terms> tData = this.termsservice.getAllTerms();
			List<TermsInputDTO> result = tData.stream().map(each -> this.mapper.map(each, TermsInputDTO.class)).collect(Collectors.toList());
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TermsController.getAllTerms: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/terms/allPaginated", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getAllTermsPaginated(@RequestBody FilterDTOCopy filterDto){
		try {
			PagedResponse<Terms> tData = this.termsservice.getAllTermsPaginated(filterDto);
			List<TermsInputDTO> result = ((Collection<Terms>) tData.getContent()).stream().map(each -> this.mapper.map(each, TermsInputDTO.class)).collect(Collectors.toList());
			GenericPagedResponse<TermsInputDTO> response = new GenericPagedResponse<TermsInputDTO>(result, tData.getCurrentPage(),
					tData.getTotalPages(), tData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TermsController.getAllTermsPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/terms/getbyid/{id}", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> getTermById(@PathVariable("id") int id){
		try {
			Terms t = this.termsservice.getTermById(id);
			TermsInputDTO result = this.mapper.map(t, TermsInputDTO.class);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TermsController.getTermById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/terms/delete", method = RequestMethod.PUT, produces = "application/json")
	private ResponseEntity<Object> deleteTermById(@RequestBody TermsInputDTO data) {
		
		try{
			List<RmTrans> rmtrans = this.rmtransservice.getRmTransByTermId(data.getTermId());
			if(rmtrans.size()==0) {
				Terms t = this.mapper.map(data, Terms.class);
				this.termsservice.deleteTerm(t);
				return new ResponseEntity<>(new ResponseUtil("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(
						new ResponseUtil("Unable to delete the record", HttpStatus.INTERNAL_SERVER_ERROR.value()),
						HttpStatus.OK);
			}
		}
		catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TermsController.deleteTermById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/terms/save", method = RequestMethod.POST,produces = "application/json")
	private ResponseEntity<Object> saveTerm(@RequestBody TermsInputDTO data) {
		try {
				Terms t = this.mapper.map(data, Terms.class);
				this.termsservice.updateTerms(t);
				return new ResponseEntity<>(new ResponseUtil(CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()),
						HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TermsController.saveTerm: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	
	@RequestMapping(value = "/terms/check/{term}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkTermExists(@PathVariable("term") String term) {
		try {
			final boolean isExists = this.termsservice.checkByTerm(term);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExists), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in TermsController.checkTermExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
}
