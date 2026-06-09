package com.fms.app.spaceManagement.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.Site;
import com.fms.app.spaceManagement.models.dto.BLFilterInputDTO;
import com.fms.app.spaceManagement.models.dto.SiteDTO;
import com.fms.app.spaceManagement.models.dto.SiteFilterInputDTO;
import com.fms.app.spaceManagement.services.SiteService;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterCriteria;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;


@RestController 
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class SiteController {

	@Autowired  
	SiteService siteService;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(SiteController.class);

	@RequestMapping(value = "/site/saveSite", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveSite(@RequestBody Site site) {

		try {
			this.siteService.saveOrUpdate(site);
			
			return new ResponseEntity<>(new ResponseUtil(CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SiteController.saveSite: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}

	
	@RequestMapping(value = "/site/getList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getSitesList() {
		try {
			List<Site> siteDatas = this.siteService.getAllSite();
			List<SiteFilterInputDTO> siteOutput = siteDatas.stream().map(this::convertSiteTODto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(siteOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SiteController.getSitesList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
    
	  //get all site
	   @RequestMapping(value = "/site/list", method = RequestMethod.POST, produces = "application/json")  
		private ResponseEntity<Object> getAllSite(@RequestBody SiteFilterInputDTO siteFilterDto)   
		{  
		   	try {
		   		PagedResponse<Site> siteDatas = this.siteService.getAllSitesByFilter(siteFilterDto);
				   
				List<SiteDTO> siteOutPut = ((Collection<Site>) siteDatas.getContent()).stream().map(element -> this.mapper.map(element, SiteDTO.class))
						.collect(Collectors.toList());
				
				GenericPagedResponse<SiteDTO> response = new GenericPagedResponse<SiteDTO>(siteOutPut, siteDatas.getCurrentPage(),
						siteDatas.getTotalPages(), siteDatas.getTotalElements());
				return new ResponseEntity<>(response, HttpStatus.OK);
		   	}catch (Exception e) {
				String exceptionCause = CommonUtil.getExceptionCause(e);
				String stacktrace = CommonUtil.getStakeTrace(e);
				logger.error("Exception in SiteController.getAllSite: "+stacktrace,e);
				return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
			}
		}
		
	//delete site
	@DeleteMapping("/site/delete/{siteId}")
	private ResponseEntity<Object> deleteSite(@PathVariable("siteId") int siteId)
	{  
		try {
			this.siteService.deleteSiteById(siteId);
			return new ResponseEntity<>(new ResponseUtil("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
		}
		catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SiteController.deleteSite: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	} 
	
	//GET site by id
	@GetMapping("/site/get/{siteId}")
	private ResponseEntity<Object> getSiteById(@PathVariable("siteId") Integer siteId)   
	{
		try {
			Site siteData = siteService.getSiteById(siteId);
			
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("site", siteData);
			if(siteData != null) {
				if ( siteData.getDoc() != null && siteData.getSitePhoto() > 0 ) {
					map.put("sitePhoto", CommonUtil.decompressBytes(siteData.getDoc().getFileContent()));
				}
			}
			return new ResponseEntity<>(map, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SiteController.getSiteById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	} 
	
	private SiteFilterInputDTO convertSiteTODto(Site site) {
		SiteFilterInputDTO dto = new SiteFilterInputDTO();
		dto.setSiteId(site.getSiteId());
		dto.setSiteName(site.getSiteName());
		
		String siteNameString = "";
		if(site.getSiteName() != null && !site.getSiteName().equals("")) {
			siteNameString = site.getSiteCode() + " - " + site.getSiteName();
		}else {
			siteNameString = site.getSiteCode();
		}
		dto.setSiteNameString(siteNameString);
		
		return dto;
	}
	
	@RequestMapping(value = "/site/checksiteidexists", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> checkSiteIdExists(@RequestBody SiteFilterInputDTO siteInput) {
		try {
			boolean isExist = false;
			if (siteInput.getSiteId()> 0) {
				isExist = this.siteService.checkSiteIdExistForCode(siteInput.getSiteId());
			}
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExist), 200), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SiteController.checkSiteIdExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/site/searchSite/{searchTearm}", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> searchBuilding(@PathVariable("searchTearm") String searchTearm)   
	{  
		
		try {
			System.out.println("Building code Search" + searchTearm);
			List<Site> siteDatas = this.siteService.getAllSiteByCodeOrName(searchTearm,searchTearm);
			List<SiteDTO> siteOutPut = siteDatas.stream().map(element -> this.mapper.map(element, SiteDTO.class))
					.collect(Collectors.toList());
			
			return new ResponseEntity<>(siteOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SiteController.searchBuilding: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/site/getAllSiteByScroll", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getAllSiteByScroll(@RequestBody FilterCriteria filter)   
	{  
		try {
			List<Site> siteDatas = this.siteService.getAllSiteByScroll(filter);
			List<SiteFilterInputDTO> siteOutput = siteDatas.stream().map(this::convertSiteTODto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(siteOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SiteController.getAllSiteByScroll: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
		
}
