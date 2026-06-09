package com.fms.app.spaceManagement.controllers;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fms.app.spaceManagement.models.Rmcat;
import com.fms.app.spaceManagement.models.dto.RmCatTreeDTO;
import com.fms.app.spaceManagement.models.dto.RmcatDTO;
import com.fms.app.spaceManagement.models.dto.RmcatFilterInputDTO;
import com.fms.app.spaceManagement.services.RmcatService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@RestController 
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class RmcatController {

	@Autowired  
	RmcatService rmcatService;
	
	@Autowired
	ModelMapper mapper;
	private static final Logger logger = LogManager.getLogger(RmcatController.class);
	
    //save room category 
	@RequestMapping(value = "/rmcat/saveRmcat", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveRmcat(@RequestBody Rmcat rmcat) {
		try {
			this.rmcatService.saveOrUpdate(rmcat); 
			return new ResponseEntity<>(rmcat, HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmcatController.saveRmcat: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
			
   //get all room catagory
	@GetMapping("/api/test/GetAllRmcat")  
	private List<Rmcat> getAllRmcat()   
	{  
	return rmcatService.getAllRmcat();  
	}
	
	//get rmcat
	@RequestMapping(value = "/rmcat/getList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getList() {
		try {
			List<Rmcat> rmcatDatas = this.rmcatService.getAllRmcat();
			List<RmcatFilterInputDTO> rmcatOutput = rmcatDatas.stream().map(this::convertRmcatTODto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(rmcatOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmcatController.getList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rmcat/getListPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getListPaginated(@RequestBody FilterDTOCopy filterDto) {
		try {
			PagedResponse<Rmcat> rmcatDatas = this.rmcatService.getAllRmcatPaginated(filterDto);
			List<RmcatFilterInputDTO> rmcatOutput = ((Collection<Rmcat>) rmcatDatas.getContent()).stream().map(this::convertRmcatTODto)
					.collect(Collectors.toList());
			GenericPagedResponse<RmcatFilterInputDTO> response = new GenericPagedResponse<RmcatFilterInputDTO>(rmcatOutput, rmcatDatas.getCurrentPage(),
					rmcatDatas.getTotalPages(), rmcatDatas.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmcatController.getListPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	//get all rmcat
	   @RequestMapping(value = "/rmcat/list", method = RequestMethod.POST, produces = "application/json")  
		private ResponseEntity<Object> getAll(@RequestBody RmcatFilterInputDTO rmcatFilterDto)   
		{  
		   try {
			   List<Rmcat> rmcatDatas = this.rmcatService.getAllRmcat();
				if (rmcatFilterDto.getRmcatId()!=null && rmcatFilterDto.getRmcatId()>0) {
					rmcatDatas = rmcatDatas.stream().filter(rmcat -> rmcat.getRmcatId() == rmcatFilterDto.getRmcatId())
							.collect(Collectors.toList());
				}
				List<RmcatDTO> rmcatOutPut = rmcatDatas.stream().map(element -> this.mapper.map(element, RmcatDTO.class))
						.collect(Collectors.toList());
				return new ResponseEntity<>(rmcatOutPut, HttpStatus.OK);
		   }catch (Exception e) {
				String exceptionCause = CommonUtil.getExceptionCause(e);
				String stacktrace = CommonUtil.getStakeTrace(e);
				logger.error("Exception in RmcatController.getAll: "+stacktrace,e);
				return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
			}
		}
	
	//delete room catagory
	@DeleteMapping("/api/test/deleteRmcat")  
	private void deleteRmcat(@RequestBody Rmcat rmcat)   
	{  
		try {
			rmcatService.deleteRmcat(rmcat);
		}
		catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmcatController.deleteRmcat: "+stacktrace,e);
		}
	} 
			
     //update room catagory
	@PutMapping("/api/test/updateRmcat")  
	private Rmcat update(@RequestBody Rmcat rmcat)   
	{ 
		try {
			rmcatService.updateRmcat(rmcat);  
	  
		}catch (Exception e) {
				String exceptionCause = CommonUtil.getExceptionCause(e);
				String stacktrace = CommonUtil.getStakeTrace(e);
				logger.error("Exception in RmcatController.update: "+stacktrace,e);
		}
		 return rmcat;
	}
	
   //GET room catagory by id
	@GetMapping("/rmcat/get/{rmCat}")  
	private Rmcat getRmcatById(@PathVariable("rmCat") String rmCat)   
	{  
		try {
			return rmcatService.getRmcatById(rmCat);
		}catch (Exception e) {
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmcatController.getRmcatById: "+stacktrace,e);
			return null;
		}
		
		
	} 
	
	private RmcatFilterInputDTO convertRmcatTODto(Rmcat rmcat) {
		RmcatFilterInputDTO dto = new RmcatFilterInputDTO();
		dto.setRmcatId(rmcat.getRmcatId());
		dto.setRmCatDesc(rmcat.getRmCat()+ " - "+rmcat.getRmCatDesc());
		dto.setHighlightColor(rmcat.getHighlightColor());
		dto.setRmCat(rmcat.getRmCat());
		return dto;
	}
	
	@DeleteMapping("/rmcat/delete/{rm_cat}")
	private ResponseEntity<Object> deleteRmcat(@PathVariable("rm_cat") String rmCat) {
		try {
			this.rmcatService.deleteRmcatByID(rmCat);
			return new ResponseEntity<>(new ResponseUtil("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmcatController.deleteRmcat: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rmcat/gettreelist", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllRmcatAsTree() {
		try {
			List<RmCatTreeDTO> data = this.rmcatService.getAllRmCatHierarchy();
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmcatController.getAllRmcatAsTree: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rmcat/check/{rmCat}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkRmCatExists(@PathVariable("rmCat") String rmCat) {
		try {
			final boolean isRmTypeExists = this.rmcatService.checkByRmCat(rmCat);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isRmTypeExists), HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmcatController.checkRmCatExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rmcat/getareabyfloor", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getrmcatAreabyFloor(@RequestBody RmcatFilterInputDTO rmcatFilterDto)   
	{  
		try {
			List<Map<String, Object>> catData = this.rmcatService.getrmcatareabyfloor(rmcatFilterDto);
			return new ResponseEntity<>(catData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmcatController.getrmcatAreabyFloor: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
