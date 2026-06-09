package com.fms.app.reservation.controllers;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fms.app.reservation.models.Visitors;
import com.fms.app.reservation.models.dto.VisitorOutPutDTO;
import com.fms.app.reservation.models.dto.VisitorsDTO;
import com.fms.app.reservation.services.VisitorsService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@RestController 
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class VisitorsController {
	
	@Autowired  
	VisitorsService service;
	
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(VisitorsController.class);
	
	@RequestMapping(value = "/visitors/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveCost(@RequestBody Visitors dataRecord) {
		try {
			this.service.saveOrUpdate(dataRecord); 
			return new ResponseEntity<>(dataRecord, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ClientController.saveCost: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}	
	
	@RequestMapping(value = "/visitors/getVisitorsList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getVisitorsList() {
		
		try {
			List<Visitors> visitorsData = this.service.getAllVisitors();
			List<VisitorsDTO> finalResult = new ArrayList<VisitorsDTO>();
			visitorsData.forEach(visitor -> {
				if(visitor.getPicture()!=null && visitor.getPicture() > 0  && visitor.getDoc() != null) {
					VisitorsDTO visitorsDTO = this.mapper.map(visitor, VisitorsDTO.class);
					visitorsDTO.setVisitorPhoto(CommonUtil.decompressBytes(visitor.getDoc().getFileContent()));
			        finalResult.add(visitorsDTO);
				}else {
					VisitorsDTO visitorsDTO = this.mapper.map(visitor, VisitorsDTO.class);
					 finalResult.add(visitorsDTO);
				}
			});
			return new ResponseEntity<>(finalResult, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ClientController.getVisitorsList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/visitors/getVisitorsListPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getVisitorsListPaginated(@RequestBody FilterDTOCopy filterDto) {
		try {
			PagedResponse<Visitors> visitorsData = this.service.getAllVisitorsPaginated(filterDto);
			List<VisitorsDTO> finalResult = ((Collection<Visitors>) visitorsData.getContent()).stream()
					.map(each ->{
						VisitorsDTO visitorsDTO = this.mapper.map(each, VisitorsDTO.class);
						if(each.getPicture()!=null && each.getPicture() > 0  && each.getDoc() != null) {
							visitorsDTO.setVisitorPhoto(CommonUtil.decompressBytes(each.getDoc().getFileContent()));
						}
						return visitorsDTO;
					})
					.collect(Collectors.toList());
			GenericPagedResponse<VisitorsDTO> response = new GenericPagedResponse<VisitorsDTO>(finalResult, visitorsData.getCurrentPage(),
					visitorsData.getTotalPages(), visitorsData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ClientController.getVisitorsListPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/visitors/getVisitorById/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getVisitorsById(@PathVariable("id") int id) {
		try {
			Visitors visitor = this.service.getVisitorsById(id);
			VisitorsDTO finalResult = this.mapper.map(visitor, VisitorsDTO.class);
			VisitorOutPutDTO visitorOutPut = new VisitorOutPutDTO();
			if(visitor != null) {
				visitorOutPut.setVisitor(finalResult);
				if(visitor.getPicture()!=null && visitor.getPicture() > 0  && visitor.getDoc() != null) {
					visitorOutPut.setPicture(CommonUtil.decompressBytes(visitor.getDoc().getFileContent()));
				}
			}
			///VisitorsDTO finalResult = this.mapper.map(visitor, VisitorsDTO.class);
			
			return new ResponseEntity<>(visitorOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ClientController.getVisitorsById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/visitors/check/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkVisitorExists(@PathVariable("id") String id) {
		try {
			final boolean isUserExist = this.service.visitorExists(id);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isUserExist), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ClientController.checkVisitorExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	

}
