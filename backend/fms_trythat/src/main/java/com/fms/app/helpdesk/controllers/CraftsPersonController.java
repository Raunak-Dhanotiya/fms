package com.fms.app.helpdesk.controllers;

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

import com.fms.app.helpdesk.models.CraftsPerson;
import com.fms.app.helpdesk.services.CraftsPersonService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class CraftsPersonController {

	@Autowired
	CraftsPersonService craftsPersonService;
	private static final Logger logger = LogManager.getLogger(CraftsPersonController.class);

	@RequestMapping(value = "/craftsperson/saveCraftsperson", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveCraftsperson(@RequestBody CraftsPerson cf) {
		
		try {
			CraftsPerson dataRecord = this.craftsPersonService.saveOrUpdate(cf);
			return new ResponseEntity<>(dataRecord, HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CraftsPersonController.saveCraftsperson: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/craftsperson/getAllCraftsperson", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllCraftsperson() {
		try {
			List<CraftsPerson> cf = this.craftsPersonService.getAllCraftsperson();
			return new ResponseEntity<>(cf, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CraftsPersonController.getAllCraftsperson: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/craftsperson/getAllCraftspersonPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAllCraftspersonPaginated(@RequestBody FilterDTOCopy filterDto) {
		try {
			PagedResponse<CraftsPerson> cf = this.craftsPersonService.getAllCraftspersonPaginated(filterDto);
			List<CraftsPerson> finalResult = ((Collection<CraftsPerson>) cf.getContent()).stream().collect(Collectors.toList());
			GenericPagedResponse<CraftsPerson> response = new GenericPagedResponse<CraftsPerson>(finalResult, cf.getCurrentPage(),
					cf.getTotalPages(), cf.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CraftsPersonController.getAllCraftspersonPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/craftsperson/getCraftspersonByCfId/{cfId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getCraftspersonByCfId(@PathVariable("cfId") int cfId) {
		try {
			CraftsPerson cf = this.craftsPersonService.getCraftspersonById(cfId);
			return new ResponseEntity<>(cf, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CraftsPersonController.getCraftspersonByCfId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/craftsperson/deleteByCfId/{cfId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> deleteByCfId(@PathVariable("cfId") int cfId) {
		try {
			CraftsPerson cfData = this.craftsPersonService.getCraftspersonById(cfId);
			this.craftsPersonService.deleteCraftsperosn(cfData);;
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CraftsPersonController.deleteByCfId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/craftsperson/checkByName/{name}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkIsExistCfByName(@PathVariable("name") String name) {
		try {
			final boolean isExists = this.craftsPersonService.getCraftspersonByCfNameAndComp(name);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExists), HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CraftsPersonController.checkIsExistCfByName: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/craftsperson/checkByEmail/{email}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkIsExistCfByEmail(@PathVariable("email") String email) {
		try {
			final boolean isExists = this.craftsPersonService.getCraftspersonByEmail(email);
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExists), HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CraftsPersonController.checkIsExistCfByEmail: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/craftsperson/getAllUnAssign/{technicianId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllUnAssignTechnician(@PathVariable("technicianId") Integer technicianId) {
	
		 try {
			 List<CraftsPerson> unAssignedData =  this.craftsPersonService.getAllUnAssignTechnician(technicianId);
				
				return new ResponseEntity<>(unAssignedData, HttpStatus.OK);
		 }catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CraftsPersonController.getAllUnAssignTechnician: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/craftsperson/getAllTechnicians/{tradeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllTechniciansByTradeId(@PathVariable("tradeId") Integer tradeId) {
		try {
			List<CraftsPerson> listTechnicians = this.craftsPersonService.getAllTechniciansByTradeId(tradeId);
			return new ResponseEntity<>(listTechnicians, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CraftsPersonController.getAllTechniciansByTradeId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
