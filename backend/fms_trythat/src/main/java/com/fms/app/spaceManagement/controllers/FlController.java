package com.fms.app.spaceManagement.controllers;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.Fl;
import com.fms.app.spaceManagement.models.dto.FLDTO;
import com.fms.app.spaceManagement.models.dto.FLFilterInputDTO;
import com.fms.app.spaceManagement.services.BlService;
import com.fms.app.spaceManagement.services.FlService;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterCriteria;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;


@Controller 
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class FlController {
	
	@Autowired  
	FlService flService;
	@Autowired  
	BlService blService;
	@Autowired
	ModelMapper mapper;
	
	
	private static final Logger logger = LogManager.getLogger(FlController.class);
	
   //save floor
	@RequestMapping(value = "/fl/saveFL", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> saveFl(@RequestBody Fl fl)   
	{ 
		try {
	     Fl savedRecord = flService.saveOrUpdate(fl); 
	     
	     Bl bl = this.blService.getBlById(savedRecord.getBlId());
	     savedRecord.setBl(bl);
	     return new ResponseEntity<>(new ResponseUtil<>(savedRecord, CommonConstant.SUCCESS_MSG,HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in FlController.saveFl: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
			
	@RequestMapping(value = "/fl/getFlList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getFLList(@RequestBody FLFilterInputDTO flFilterDto) {
		try {
			List<Fl> flData = this.flService.getFilteredFloors(flFilterDto);
			List<FLDTO> flOutPut = flData.stream().map(element -> this.mapper.map(element, FLDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(flOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in FlController.getFLList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/fl/getFlListPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getFLListPaginated(@RequestBody FLFilterInputDTO flFilterDto) {
		try {
			PagedResponse<Fl> flData = this.flService.getFilteredFloorsPaginated(flFilterDto);
			List<FLDTO> flOutPut = ((Collection<Fl>) flData.getContent()).stream().map(element -> this.mapper.map(element, FLDTO.class))
					.collect(Collectors.toList());
			GenericPagedResponse<FLDTO> response = new GenericPagedResponse<FLDTO>(flOutPut, flData.getCurrentPage(),
					flData.getTotalPages(), flData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in FlController.getFLListPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	//get all buildings
	@RequestMapping(value = "/fl/getAllFl", method = RequestMethod.GET, produces = "application/json")  
	private ResponseEntity<Object> getAllFl()   
	{  
		try {
			List<Fl> flData = this.flService.getAllFl();
			List<FLFilterInputDTO> flOutput = flData.stream().map(this::convertFLTODto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(flOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in FlController.getAllFl: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	private FLFilterInputDTO convertFLTODto(Fl fl) {
		FLFilterInputDTO dto = new FLFilterInputDTO();
		dto.setFlId(fl.getFlId());
		dto.setName(fl.getFlName());
		dto.setBlId(fl.getBlId());
		dto.setFlInfo(fl.getFlInfo());
		dto.setSvgName(fl.getSvgName());
		dto.setSiteId(fl.getBl().getSiteId());
		dto.setUnits(fl.getUnits());
		dto.setExternalArea(fl.getExternalArea());
		dto.setInternalArea(fl.getInternalArea());
		
		String flNameString = "";
		
		if(fl.getFlName() != null && !fl.getFlName().equals("")) {
			flNameString = fl.getBl().getBlCode() + " - "+ fl.getFlCode() + " - " + fl.getFlName();
		}else {
			flNameString = fl.getBl().getBlCode()  + " - "+ fl.getFlCode();
		}
		
		dto.setFlNameString(flNameString);
		if(fl.getBl().getBlName()!=null) {
			dto.setBlNameString(fl.getBl().getBlCode()+ " - " + fl.getBl().getBlName());
		}else {
			dto.setBlNameString(fl.getBl().getBlCode());
		}
		dto.setSiteNameString(fl.getBl().getSite().getSiteName() != null?fl.getBl().getSite().getSiteCode()+ " - "+fl.getBl().getSite().getSiteName():fl.getBl().getSite().getSiteCode());

		return dto;
	}
				
	//delete building
	@DeleteMapping("/api/test/deleteFl")  
	private void deleteFl(@RequestBody Fl fl)   
	{  
		try {
		flService.deleteFl(fl);
		}
		catch (Exception e) {
			System.out.println("Failed");
		}
	} 
			
     //update floor
	@PutMapping("/api/test/updateFl")  
	private Fl update(@RequestBody Fl fl)   
	{ 
		try {
	    flService.updateFl(fl);  
	  
		}
		catch (Exception e) {
			System.out.println("Fail");
		}
		 return fl;
	}
			
	@RequestMapping(value = "/fl/getFlById/{flId}", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> getFlById(@PathVariable("flId") Integer flId)    
	{  
		try {
			Fl fl = this.flService.getFlByFlId(flId);

			return new ResponseEntity<>(fl, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in FlController.getFlById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/fl/searchFl/{id}", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> searchFloor(@PathVariable("id") String str)   
	{  
		
		try {
			List<Fl> flData = this.flService.getFlByFlCodeOrName(str,str);
			List<FLFilterInputDTO> flOutput = flData.stream().map(this::convertFLTODto)
					.collect(Collectors.toList());

			return new ResponseEntity<>(flOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in FlController.searchFloor: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}

	@RequestMapping(value = "/fl/delete/{flId}", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> deleteFloor(@PathVariable("flId") Integer flId) {
		
		try {
			this.flService.deleteByFlId(flId);
			return new ResponseEntity<>(new ResponseUtil("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in FlController.deleteFloor: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<Object, Boolean>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
	
	@RequestMapping(value = "/fl/floorexistssvg/{svgName}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkFloorExistsforSvg(@PathVariable("svgName") String svgName) {
		try {
			boolean isExist = false;
			if (svgName != null && svgName.length() > 0) {
				isExist = this.flService.checkFlExistForSvgName(svgName);
			}
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExist), 200), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in FlController.checkFloorExistsforSvg: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/fl/checkflidexists", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> checkFlIdExists(@RequestBody FLFilterInputDTO flData) {
		try {
			boolean isExist = false;
			if (flData.getFlId()!=null && flData.getFlId()>0 && flData.getBlId()!=null && flData.getBlId()>0) {
				isExist = this.flService.checkFlExistForFlIdAndBlId(flData.getFlId(),flData.getBlId());
			}else if(flData.getFlCode()!=null && flData.getFlCode().length()>0 && flData.getBlId()!=null && flData.getBlId()>0) {
				isExist = this.flService.checkFlExistForFlCodeAndBlId(flData.getFlCode(),flData.getBlId());
			}
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExist), 200), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in FlController.checkFlIdExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/fl/getareabyfloor", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getareabyfloor() {
		try {
			List<Map<String, Object>> output = this.flService.getareabyfloor();
			return new ResponseEntity<>(output, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in FlController.getareabyfloor: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/fl/unlinkFloorPlan/{flId}", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> unlinkFloorPlan(@PathVariable("flId") Integer flId) {
		
		try {
			this.flService.unLinkFloorPlan(flId);
			return new ResponseEntity<>(new ResponseUtil("Floor unlinked successfully", HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in FlController.unlinkFloorPlan: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/fl/getAllFlByScroll", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getAllFlByScroll(@RequestBody FilterCriteria filter)   
	{  
		try {
			List<Fl> flData = this.flService.getFlOnScroll(filter);
			List<FLFilterInputDTO> flOutput = flData.stream().map(this::convertFLTODto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(flOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in BlController.getAllFlByScroll: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
