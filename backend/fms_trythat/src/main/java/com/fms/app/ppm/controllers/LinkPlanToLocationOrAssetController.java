package com.fms.app.ppm.controllers;


import java.util.ArrayList;
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

import com.fms.app.ppm.models.dto.LinkPlanToLocationOrAssetDto;
import com.fms.app.ppm.models.dto.UnLinkPlanToLocationOrAssetDTO;
import com.fms.app.ppm.models.LinkPlanToLocationOrAsset;
import com.fms.app.ppm.models.Plan;
import com.fms.app.ppm.models.PlanSchedule;
import com.fms.app.ppm.services.LinkPlanToLocationOrAssetServices;
import com.fms.app.ppm.services.PlanScheduleServices;
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
public class LinkPlanToLocationOrAssetController {

	@Autowired
	LinkPlanToLocationOrAssetServices linkPlanToLocationOrAssetSrv;
	
	@Autowired
	PlanScheduleServices planScheduleServices;
	private static final Logger logger = LogManager.getLogger(LinkPlanToLocationOrAssetController.class);

	@RequestMapping(value = "/linkPlanToLocationOrAsset/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> linkPlanToLocationOrAsset(@RequestBody List<LinkPlanToLocationOrAsset> dataRecords) {
		try {
			dataRecords.forEach(dataRecord -> {
				boolean isExists = this.linkPlanToLocationOrAssetSrv.checkLinkedPlansExists(dataRecord);
				if(!isExists) {
					this.linkPlanToLocationOrAssetSrv.saveOrUpdate(dataRecord);
				}
				
			});
			
			return new ResponseEntity<>(new ResponseUtil(CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in LinkPlanToLocationOrAssetController.saveClient: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/linkPlanToLocationOrAsset/delete", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> unLinkPlanToLocationOrAsset(@RequestBody List<UnLinkPlanToLocationOrAssetDTO> records) {
			if (records != null) {
			     
				try {
					List<UnLinkPlanToLocationOrAssetDTO> existsRecords = new ArrayList<UnLinkPlanToLocationOrAssetDTO>();
					records.forEach(record -> {
						LinkPlanToLocationOrAsset dataRecord = this.linkPlanToLocationOrAssetSrv.getLinkedPlanToLocationOrAsset(
								record.getPlanId(), record.getBlId(), record.getFlId(), record.getRmId(),record.getEqId());
						List<PlanSchedule> schedList = this.planScheduleServices.getByPlanLocEqId(dataRecord.getPlanLocEqId());
						if(schedList != null  && schedList.size() > 0) {
							existsRecords.add(record);
						} else {
							this.linkPlanToLocationOrAssetSrv.deleteLinkedPlanToLocationOrAsset(dataRecord);
						}
						
					});
					return new ResponseEntity<>(existsRecords, HttpStatus.OK);
				} catch (Exception e) {
					String exceptionCause = CommonUtil.getExceptionCause(e);
					String stacktrace = CommonUtil.getStakeTrace(e);
					logger.error("Exception in LinkPlanToLocationOrAssetController.unLinkPlanToLocationOrAsset: "+stacktrace,e);
					return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
				}
			}

			return new ResponseEntity<>(
					new ResponseUtil(CommonConstant.UNABLE_TO_PROCESS_MSG, HttpStatus.INTERNAL_SERVER_ERROR.value()),
					HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/linkPlanToLocation/getUnLinked", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getUnLinkedPlans(@RequestBody UnLinkPlanToLocationOrAssetDTO record) {
		try {
			List<Plan> planList = this.linkPlanToLocationOrAssetSrv.getUnLinkedPlans(
					record.getBlId(), record.getFlId(), record.getRmId(),record.getEqId(),record.getPlanType());
			return new ResponseEntity<>(planList, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in LinkPlanToLocationOrAssetController.getUnLinkedPlans: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/linkPlanToLocation/getLinked", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getLinkedPlans(@RequestBody UnLinkPlanToLocationOrAssetDTO record) {
		try {
			List<Plan> planList = this.linkPlanToLocationOrAssetSrv.getLinkedPlans(
					record.getBlId(), record.getFlId(), record.getRmId(),record.getEqId());
			return new ResponseEntity<>(planList, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in LinkPlanToLocationOrAssetController.getLinkedPlans: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}

	}
	
	@RequestMapping(value = "/linkPlanToLocation/getAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAll() {
		try {
			List<LinkPlanToLocationOrAsset> linkPlanToLocationOrAssetList = this.linkPlanToLocationOrAssetSrv.getAll();
			List<LinkPlanToLocationOrAssetDto> outPut = linkPlanToLocationOrAssetList.stream().map(this::convertToDto)
					.collect(Collectors.toList());	
			return new ResponseEntity<>(outPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in LinkPlanToLocationOrAssetController.getAll: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}

	}
	
	@RequestMapping(value = "/linkPlanToLocation/getAllPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllPaginated(@RequestBody FilterDTOCopy filterDto) {
		try {
			PagedResponse<LinkPlanToLocationOrAsset> linkPlanToLocationOrAssetList = this.linkPlanToLocationOrAssetSrv.getAllPaginated(filterDto);
			List<LinkPlanToLocationOrAssetDto> finalResult = ((Collection<LinkPlanToLocationOrAsset>) linkPlanToLocationOrAssetList.getContent()).stream().map(this::convertToDto)
					.collect(Collectors.toList());
			GenericPagedResponse<LinkPlanToLocationOrAssetDto> response = new GenericPagedResponse<LinkPlanToLocationOrAssetDto>(finalResult, linkPlanToLocationOrAssetList.getCurrentPage(),
					linkPlanToLocationOrAssetList.getTotalPages(), linkPlanToLocationOrAssetList.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in LinkPlanToLocationOrAssetController.getAllPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}

	}
	
	@RequestMapping(value = "/linkPlanToLocation/getAllByPlanId/{planId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getLocPlansByPlanId(@PathVariable("planId") int planId) {
		try {
			List<LinkPlanToLocationOrAsset> data = this.linkPlanToLocationOrAssetSrv.getLocPlansByPlanId(planId);
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in LinkPlanToLocationOrAssetController.getLocPlansByPlanId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/linkPlanToLocation/getAllByPlanIdPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getLocPlansByPlanIdPaginated(@RequestBody UnLinkPlanToLocationOrAssetDTO record) {
		try {
			PagedResponse<LinkPlanToLocationOrAsset> data = this.linkPlanToLocationOrAssetSrv.getLocPlansByPlanIdPaginated(record);
			List<LinkPlanToLocationOrAsset> finalResult = ((Collection<LinkPlanToLocationOrAsset>) data.getContent()).stream().collect(Collectors.toList());
			GenericPagedResponse<LinkPlanToLocationOrAsset> response = new GenericPagedResponse<LinkPlanToLocationOrAsset>(finalResult, data.getCurrentPage(),
					data.getTotalPages(), data.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in LinkPlanToLocationOrAssetController.getLocPlansByPlanIdPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/linkPlanToLocation/daleteById/{planLocEqId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> deleteplanLocEq(@PathVariable("planLocEqId") int planLocEqId) {
		
		try {
			this.linkPlanToLocationOrAssetSrv.deletePlanLoCation(planLocEqId);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in LinkPlanToLocationOrAssetController.deleteplanLocEq: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/linkPlanToLocation/checkExists", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> checkLinkedPlansExists(@RequestBody LinkPlanToLocationOrAsset record) {
		try {
			boolean isExists = this.linkPlanToLocationOrAssetSrv.checkLinkedPlansExists(record);
			return new ResponseEntity<>(isExists, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in LinkPlanToLocationOrAssetController.checkLinkedPlansExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}

	}
	
	private LinkPlanToLocationOrAssetDto convertToDto(LinkPlanToLocationOrAsset rec) {
		LinkPlanToLocationOrAssetDto dto = new LinkPlanToLocationOrAssetDto();
		dto.setPlanLocEqId(rec.getPlanLocEqId());
		dto.setPlanId(rec.getPlanId());
		dto.setPlanName(rec.getPlan().getPlanName());
		dto.setPlanType(rec.getPlan().getPlanType());
		dto.setPlanDescription(rec.getPlan().getDescription());
		dto.setEqId(rec.getEqId());
		
		dto.setBlId(rec.getBlId());
		dto.setFlId(rec.getFlId());
		dto.setRmId(rec.getRmId());
		if(rec.getBl() != null) {
			dto.setBlName(rec.getBl().getBlCode() + " - " + (rec.getBl().getBlName() == null ? "":rec.getBl().getBlName()));
		}
		
		if(rec.getEq() != null) {
			dto.setEqCode( rec.getEq().getEqCode() );
		}
		if(rec.getFl() != null) {
			dto.setFlName(rec.getFl().getFlCode() + " - " + (rec.getFl().getFlName() == null ? "":rec.getFl().getFlName()));
		}
		if(rec.getRm() != null) {
			dto.setRmName(rec.getRm().getRmCode() + " - " + (rec.getRm().getRmName() == null ? "":rec.getRm().getRmName()));
		}
		
		return dto;
	}
}
