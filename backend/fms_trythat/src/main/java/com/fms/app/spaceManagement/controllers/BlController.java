package com.fms.app.spaceManagement.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.dto.BLDTO;
import com.fms.app.spaceManagement.models.dto.BLFilterInputDTO;
import com.fms.app.spaceManagement.models.dto.BLOutputDTO;
import com.fms.app.spaceManagement.models.dto.SpaceAllocationFilterInputDTO;
import com.fms.app.spaceManagement.services.BlService;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.app.utils.TimezoneConverter;
import com.fms.app.utils.ZoneIdsDTO;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;
import com.fms.paginator.models.FilterCriteria;


@Controller 
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1") 
public class BlController {

	@Autowired  
	BlService blService;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired  
	TimezoneConverter timezoneConverterSrv;
	
	private static final Logger logger = LogManager.getLogger(BlController.class);
	
	//save building
	@RequestMapping(value = "/bl/saveBL", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveBl(@RequestBody Bl bl)   
	{ 
		try {
			Bl savedRecord = blService.saveOrUpdate(bl);
			return new ResponseEntity<>(new ResponseUtil<>(savedRecord, CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()) ,HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in BlController.saveBl: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/bl/getBlList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getBLList(@RequestBody BLFilterInputDTO blFilterDto) {

		try {
			List<Bl> blData = this.blService.getFilteredBuildings(blFilterDto);
			List<BLDTO> blOutPut = blData.stream().map(element -> this.mapper.map(element, BLDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(blOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in BlController.getBLList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/bl/getBlListPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getBLListPaginated(@RequestBody BLFilterInputDTO blFilterDto) {

		try {
			PagedResponse<Bl> blData = this.blService.getFilteredBuildingsPaginated(blFilterDto);
			List<BLDTO> blOutPut = ((Collection<Bl>) blData.getContent()).stream().map(element -> this.mapper.map(element, BLDTO.class))
					.collect(Collectors.toList());
			GenericPagedResponse<BLDTO> response = new GenericPagedResponse<BLDTO>(blOutPut, blData.getCurrentPage(),
					blData.getTotalPages(), blData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in BlController.getBLListPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
		
	//get all buildings
	@RequestMapping(value = "/bl/getAllBl", method = RequestMethod.GET, produces = "application/json")  
	private ResponseEntity<Object> getAllBl()   
	{  
		try {
			List<Bl> blData = this.blService.getAllBl();
			List<BLFilterInputDTO> blOutput = new ArrayList<BLFilterInputDTO>();
			if (blData != null) {
				blOutput = blData.stream().map(this::convertBLTODto).collect(Collectors.toList());
			}
			return new ResponseEntity<>(blOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in BlController.getAllBl: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	private BLFilterInputDTO convertBLTODto(Bl bl) {
		BLFilterInputDTO dto = new BLFilterInputDTO();
		dto.setBlId(bl.getBlId());
		dto.setName(bl.getBlName());
		dto.setSiteId(bl.getSiteId());
		String blNameString = "";
		if(bl.getBlName() != null && !bl.getBlName().equals("")) {
			blNameString = bl.getBlCode() + " - " + bl.getBlName();
		}else {
			blNameString = bl.getBlCode();
		}
		dto.setBlNameString(blNameString);
		dto.setSiteNameString(!(bl.getSite().getSiteName()== null) ?bl.getSite().getSiteCode()  + " - "+bl.getSite().getSiteName(): bl.getSite().getSiteCode());
		return dto;
	}
			
	//delete building
	@DeleteMapping("/api/test/deleteBl")  
	private void deleteBl(@RequestBody Bl bl)   
	{  
		try {
			blService.deleteById(bl.getBlId());
		}catch(Exception ec) {
			String stacktrace = CommonUtil.getStakeTrace(ec);
			logger.error("Exception in BlController.deleteBl: "+stacktrace,ec);
		}
	} 
			
	//GET building by id
	@RequestMapping(value = "/bl/getBlById/{id}", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> getBlById(@PathVariable("id") int id)   
	{  
		try {
			Bl bl = this.blService.getBlById(id);
			BLOutputDTO blOutput = new BLOutputDTO();
			if(bl != null) {
				blOutput.setBl(bl);
				if (bl.getDoc() != null && bl.getBlPhoto() > 0) {
					blOutput.setBlPhoto(CommonUtil.decompressBytes(bl.getDoc().getFileContent()));
				}
			}

			return new ResponseEntity<>(blOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in BlController.getBlById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/bl/searchBl/{searchTearm}", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> searchBuilding(@PathVariable("searchTearm") String searchTearm)   
	{  
		
		try {
			List<Bl> blData = this.blService.getAllBl(searchTearm);
			List<BLFilterInputDTO> blOutput = new ArrayList<BLFilterInputDTO>();
			if (blData != null) {
				blOutput = blData.stream().map(this::convertBLTODto).collect(Collectors.toList());
			}
			return new ResponseEntity<>(blOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in BlController.searchBuilding: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}
		
	@RequestMapping(value = "/bl/delete/{blId}", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> deleteSite(@PathVariable("blId") Integer blId) {
		try {
			this.blService.deleteById(blId);
			return new ResponseEntity<>(new ResponseUtil("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
		}
		catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in BlController.deleteSite: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/bl/checkblidexists", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> checkBlIdExists(@RequestBody BLFilterInputDTO blData) {
		try {
			boolean isExist = false;
			if (blData.getBlId()!=null && blData.getBlId()>0 && blData.getSiteId()!=null && blData.getSiteId()>0 ) {
				isExist = this.blService.checkBlExistForBlIdAndSiteId(blData.getBlId(),blData.getSiteId());
			}else if(blData.getSiteCode()!=null && blData.getSiteCode().length()>0 && blData.getBlCode()!=null && blData.getBlCode().length()>0) {
				isExist = this.blService.checkBlExistForBlCodeAndSiteCode(blData.getBlCode(),blData.getSiteCode());
			}
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExist), 200), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in BlController.checkBlIdExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/bl/getAllTimeZones", method = RequestMethod.GET, produces = "application/json")  
	private ResponseEntity<Object> getAllTimeZones()   
	{  
		try {
			List<ZoneIdsDTO> zoneIdDTOList = TimezoneConverter.getAllTimezones();
			return new ResponseEntity<>(zoneIdDTOList, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in BlController.getAllTimeZones: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/bl/getbuildingwitharea", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getbuildingwitharea() {
		try {
			List<Map<String, Object>> output = this.blService.getbuildingwitharea();
			return new ResponseEntity<>(output, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in BlController.getbuildingwitharea: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/bl/buildingwiseallocation", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getbuildingwiseallocation( @RequestBody SpaceAllocationFilterInputDTO filter) {
		try {
			List<Map<String, Object>> output = this.blService.buildingwiseallocation(filter);
			return new ResponseEntity<>(output, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in BlController.getbuildingwiseallocation: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/bl/getAllBlByScroll", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getAllBlByScroll(@RequestBody FilterCriteria filter)   
	{  
		try {
			List<Bl> blData = this.blService.getBlOnScroll(filter);
			List<BLFilterInputDTO> blOutput = new ArrayList<BLFilterInputDTO>();
			if (blData != null) {
				blOutput = blData.stream().map(this::convertBLTODto).collect(Collectors.toList());
			}
			return new ResponseEntity<>(blOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in BlController.getAllBlByScroll: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
