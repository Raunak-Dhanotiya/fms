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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.divisionDepartment.models.dto.DepartmentAllocationDTO;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.Fl;
import com.fms.app.spaceManagement.models.Rm;
import com.fms.app.spaceManagement.models.RmTeams;
import com.fms.app.spaceManagement.models.dto.RMDTO;
import com.fms.app.spaceManagement.models.dto.RMDTOforsvgcolor;
import com.fms.app.spaceManagement.models.dto.RMFilterInputDTO;
import com.fms.app.spaceManagement.models.dto.RMOutputDTO;
import com.fms.app.spaceManagement.models.dto.SpaceAllocationFilterInputDTO;
import com.fms.app.spaceManagement.models.dto.SpaceFilterReportDTO;
import com.fms.app.spaceManagement.models.dto.SpaceReportInputDTO;
import com.fms.app.spaceManagement.models.dto.SpaceUtilizationStatisticsRoomInputDTO;
import com.fms.app.spaceManagement.repository.RmTeamsRepository;
import com.fms.app.spaceManagement.services.BlService;
import com.fms.app.spaceManagement.services.FlService;
import com.fms.app.spaceManagement.services.RmService;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterCriteria;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@Controller 
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class RmController {
	
	@Autowired  
	RmService rmService;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	RmTeamsRepository rmteamsrepo;
	
	@Autowired  
	BlService blService;
	
	@Autowired  
	FlService flService;
	
	private static final Logger logger = LogManager.getLogger(RmController.class);
	
	@RequestMapping(value = "/rm/saveRM", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> saveRm(@RequestBody Rm rm)   
	{ 
		try {
	    Rm savedRecord = rmService.saveOrUpdate(rm); 
	    Bl bl = this.blService.getBlById(savedRecord.getBlId());
	    Fl fl = this.flService.getFlByFlId(savedRecord.getFlId());
	    savedRecord.setBl(bl);
	    savedRecord.setFl(fl);
	    return new ResponseEntity<>(new ResponseUtil<>(savedRecord, CommonConstant.SUCCESS_MSG, HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.saveRm: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
			
	@RequestMapping(value = "/rm/getRmList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getRMList(@RequestBody RMFilterInputDTO rmFilterDto) {
		
		try {
			List<Rm> rmData = this.rmService.getFilteredRooms(rmFilterDto);
			
			List<RMDTO> rmOutPut = rmData.stream()
					.map(element -> this.mapper.map(element, RMDTO.class)).collect(Collectors.toList());
			return new ResponseEntity<>(rmOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getRMList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/getAllRm", method = RequestMethod.GET, produces = "application/json")  
	private ResponseEntity<Object> getAllRm()   
	{  
		try {
			List<Rm> rmData = this.rmService.getAllRm();
			List<RMFilterInputDTO> rmOutput = rmData.stream().map(this::convertRMTODto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(rmOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getAllRm: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	private RMFilterInputDTO convertRMTODto(Rm rm) {
		RMFilterInputDTO dto = new RMFilterInputDTO();
		dto.setRmId(rm.getRmId());
		dto.setRmCode(rm.getRmCode());
		dto.setName(rm.getRmName());
		dto.setBlId(rm.getBlId());
		dto.setFlId(rm.getFlId());
		dto.setSvgElementId(rm.getSvgElementId());
		dto.setSiteId(rm.getBl().getSiteId());
		
		String rmNameString = "";
		if(rm.getRmName() != null && !rm.getRmName().equals("") ) {
			rmNameString = rm.getBl().getBlCode() + " - " + rm.getFl().getFlCode() + " - " + rm.getRmCode() + " - " + rm.getRmName();
		}else {
			rmNameString = rm.getBlId() + " - " + rm.getFlId() + " - " + rm.getRmId();
		}
		
		dto.setRmNameString(rmNameString);
		dto.setFlNameString(rm.getFl().getFlName()!=null ?rm.getFl().getFlCode()+" - " +rm.getFl().getFlName():rm.getFl().getFlCode());
		dto.setBlNameString(rm.getBl().getBlName()!=null ? rm.getBl().getBlCode() + " - "+rm.getBl().getBlName():rm.getBl().getBlCode());
		dto.setSiteNameString(rm.getBl().getSite().getSiteName() != null?rm.getBl().getSite().getSiteCode()+ " - "+rm.getBl().getSite().getSiteName():rm.getBl().getSite().getSiteCode());
		dto.setCommonAreaType(rm.getCommonAreaType());
		dto.setBlCode(rm.getBl().getBlCode());
		dto.setFlCode(rm.getFl().getFlCode());
		return dto;
	}
	
	@RequestMapping(value = "/rm/getRmById/{rmId}", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> getRmById(@PathVariable("rmId") int rmId)   
	{  
		try {
			Rm rm = this.rmService.getRmById(rmId);
			RMOutputDTO rmOutput = new RMOutputDTO();
			rmOutput.setRm(rm);
			if (rm.getDoc() != null && rm.getRmPhoto1() > 0) {
				rmOutput.setRmPhoto(CommonUtil.decompressBytes(rm.getDoc().getFileContent()));
			}
			return new ResponseEntity<>(rmOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getRmById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/searchRM/{id}", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> searchRoom(@PathVariable("id") String str)   
	{  
		
		try {
			List<Rm> rmData = this.rmService.getAllRmByRmCodeAndRmName(str,str);
			List<RMFilterInputDTO> rmOutput = rmData.stream().map(this::convertRMTODto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(rmOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.searchRoom: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<Object, Boolean>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	@RequestMapping(value = "/rm/delete/{rmId}", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> deleteRoom(@PathVariable("rmId") int rmId) {
		try {
			this.rmService.deleteById(rmId);
			return new ResponseEntity<>(new ResponseUtil<Object>("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
		}
		catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.deleteRoom: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/checkrmidexists", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> checkRmIdExists(@RequestBody RMFilterInputDTO rmInput) {
		try {
			boolean isExist = false;
			if (rmInput.getRmId() > 0 && rmInput.getBlId() > 0 && rmInput.getFlId() > 0) {
				isExist = this.rmService.checkRmIdExistForCode(rmInput.getRmId(),rmInput.getBlId(),
						rmInput.getFlId());
			}
			return new ResponseEntity<>(new ResponseUtil<Object>(String.valueOf(isExist), 200), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.checkRmIdExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/updatermprop", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> customupdateRoom(@RequestBody RMDTOforsvgcolor dataRecord) {
		try {
			this.rmService.customUpdate(dataRecord);
			return new ResponseEntity<>(new ResponseUtil<Object>("Record updated", HttpStatus.OK.value()), HttpStatus.OK);
		}
		catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.customupdateRoom: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/getrmwithdivordeptcolor", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getrmlistwithdivordeptcolor(@RequestBody RMFilterInputDTO rmFilterDto) {
		try {
			List<RMDTOforsvgcolor> rmOutPut = this.rmService.getrmwithdivdepcolor(
					rmFilterDto.getBlId(),rmFilterDto.getFlId());
			return new ResponseEntity<>(rmOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getrmlistwithdivordeptcolor: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/getrmwithcatortypecolor", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getrmlistwithcatortypecolor(@RequestBody RMFilterInputDTO rmFilterDto) {
		try {
			List<RMDTOforsvgcolor> rmOutPut = this.rmService.getrmwithcattypecolor(
					rmFilterDto.getBlId(),rmFilterDto.getFlId());
			return new ResponseEntity<>(rmOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getrmlistwithcatortypecolor: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/getrmwithparametercolor", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getrmlistwithparamcolor(@RequestBody RMFilterInputDTO rmFilterDto) {
		try {
			List<RMDTOforsvgcolor> rmOutPut = null;
			if(rmFilterDto.getParameterName().equalsIgnoreCase("division")){
				rmOutPut = this.rmService.getrmwithdivisioncolor(
						rmFilterDto.getBlId(),rmFilterDto.getFlId());
			}
			else if(rmFilterDto.getParameterName().equalsIgnoreCase("department")) {
				rmOutPut = this.rmService.getrmwithdepartmentcolor(
						rmFilterDto.getBlId(),rmFilterDto.getFlId());
			}
			else if (rmFilterDto.getParameterName().equalsIgnoreCase("rmcat")) {
				rmOutPut = this.rmService.getrmwithrmcatcolor(
						rmFilterDto.getBlId(),rmFilterDto.getFlId());
			}
			else if (rmFilterDto.getParameterName().equalsIgnoreCase("rmtype")) {
				rmOutPut = this.rmService.getrmwithrmtypecolor(
						rmFilterDto.getBlId(),rmFilterDto.getFlId());
			}
			return new ResponseEntity<>(rmOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getrmlistwithparamcolor: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/resportsByGroup", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getReports(@RequestBody SpaceReportInputDTO filter) {
		try {
			List<Map<String, Object>> data = this.rmService.getReportsByGroup(filter);
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getReports: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/rm/resportsByGroupPaginated", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getReportsPginated(@RequestBody SpaceReportInputDTO filter) {
		try {
			Map<String, Object> data = this.rmService.getReportsByGroupPaginated(filter);
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getReports: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/rm/reportfilter", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getRoomforreport(@RequestBody SpaceFilterReportDTO filter) {
		try {
			List<Rm> rmData = this.rmService.getAllRm();
			if(filter.getBlId()!=null && filter.getBlId()>0) {
				rmData = rmData.stream().filter(rm -> filter.getBlId() == rm.getBlId())
						.collect(Collectors.toList());
			}
			if(filter.getFlId()!=null && filter.getFlId()>0) {
				rmData = rmData.stream().filter(rm -> filter.getFlId() == rm.getFlId())
						.collect(Collectors.toList());
			}
			if(filter.getDivId()!=null && filter.getDivId()>0) {
				rmData = rmData.stream().filter(rm -> filter.getDivId() == rm.getDivId())
						.collect(Collectors.toList());
			}
			if(filter.getDepId()!=null && filter.getDepId()>0) {
				rmData = rmData.stream().filter(rm -> filter.getDepId() == rm.getDepId())
						.collect(Collectors.toList());
			}
			if(filter.getRmcatId()!=null && filter.getRmcatId()>0) {
				rmData = rmData.stream().filter(rm -> filter.getRmcatId() == rm.getRmcatId())
						.collect(Collectors.toList());
			}
			if(filter.getRmtypeId()!=null && filter.getRmtypeId()>0) {
				rmData = rmData.stream().filter(rm -> filter.getRmtypeId() == rm.getRmtypeId())
						.collect(Collectors.toList());
			}
			if(filter.getTeamId()!=null && filter.getTeamId()>0) {
				List<RmTeams> rmteams = this.rmteamsrepo.findAllByTeamId(filter.getTeamId());
				if (!rmteams.isEmpty()) {
					rmData = rmData.stream()
					        .filter(rm -> rmteams.stream()
					                .anyMatch(rmTeam ->
					                        rm.getBlId() == rmTeam.getBlId() &&
					                        rm.getFlId() == rmTeam.getFlId() &&
					                        rm.getRmId() == rmTeam.getRmId())
					                )
					        .collect(Collectors.toList());
				}
			}
			if(filter.getRmUse()!=null && filter.getRmUse().length()>0) {
				rmData = rmData.stream().filter(rm -> filter.getRmUse().equalsIgnoreCase(rm.getRmUse()))
						.collect(Collectors.toList());
			}
			if(filter.getSpaceStandard()!=null && filter.getSpaceStandard().length()>0) {
				rmData = rmData.stream().filter(rm -> filter.getSpaceStandard().equalsIgnoreCase(rm.getSpaceStandard()))
						.collect(Collectors.toList());
			}
			if(filter.getSubDepId()!=null && filter.getSubDepId()>0) {
				rmData = rmData.stream().filter(rm -> filter.getSubDepId() == rm.getSubDepId())
						.collect(Collectors.toList());
			}
			List<RMDTO> rmOutPut = rmData.stream().map(element -> this.mapper.map(element, RMDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(rmOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getRoomforreport: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/getareabycommonarea", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getareabycommonarea(@RequestBody RMFilterInputDTO rmFilterDto) {
		try {
			List<Map<String, Object>> rmOutPut = this.rmService.getareabycommonarea(rmFilterDto);
			return new ResponseEntity<>(rmOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getareabycommonarea: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/spaceutilizationrmdata", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getspaceutilizationrmdata(@RequestBody SpaceUtilizationStatisticsRoomInputDTO filter) {
		try {
			List<Map<String, Object>> rmOutPut = this.rmService.getrmdataforspaceutilization(filter);
			return new ResponseEntity<>(rmOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getspaceutilizationrmdata: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/spacedivdepallocation", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getdivdepallocation(@RequestBody DepartmentAllocationDTO filter) {
		try {
			Map<String, Object>rmOutPut = this.rmService.getdivisiondepartmentallocation(filter);
			return new ResponseEntity<>(rmOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getdivdepallocation: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/spaceallocationbystatistics", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getspaceallocationbystatistics(@RequestBody SpaceAllocationFilterInputDTO filter) {
		try {
			List<Map<String, Object>> rmOutPut = this.rmService.getspaceallocationbystatistics(filter);
			return new ResponseEntity<>(rmOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getspaceallocationbystatistics: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/spaceallocationbystatisticspaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getspaceallocationbystatisticspaginated(@RequestBody SpaceAllocationFilterInputDTO filter) {
		try {
			Map<String, Object> rmOutPut = this.rmService.getspaceallocationbystatisticspaginated(filter);
			return new ResponseEntity<>(rmOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getspaceallocationbystatistics: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/spaceallocationbystatisticsrmdata", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getspaceallocationbystatisticsrmdata(@RequestBody SpaceAllocationFilterInputDTO filter) {
		try {
			List<Map<String, Object>> rmOutPut = this.rmService.getspaceallocationbystatisticsrmdata(filter);
			return new ResponseEntity<>(rmOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getspaceallocationbystatisticsrmdata: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/spaceallocationreportbyblfldivdep", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getspaceallocationreportbyblfl(@RequestBody SpaceAllocationFilterInputDTO filter) {
		try {
			List<Map<String, Object>> rmOutPut = this.rmService.getspaceallocationreportbyblfldivdep(filter);
			return new ResponseEntity<>(rmOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getspaceallocationreportbyblfl: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/spaceunallocatedroomdetails", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getspaceunallocatedroomdetails(@RequestBody SpaceAllocationFilterInputDTO filter) {
		try {
			List<Map<String, Object>> rmOutPut = this.rmService.getspaceunallocatedroomdetails(filter);
			return new ResponseEntity<>(rmOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getspaceunallocatedroomdetails: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/getRmListByPagination", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getRmListByPagination(@RequestBody RMFilterInputDTO rmFilterDto) {
		
		try {
			PagedResponse<Rm> rmData = this.rmService.getFilteredRoomsByPagination(rmFilterDto);
			List<RMDTO> rmOutPut = ((Collection<Rm>) rmData.getContent()).stream()
					.map(element -> this.mapper.map(element, RMDTO.class)).collect(Collectors.toList());
			GenericPagedResponse<RMDTO> response = new GenericPagedResponse<RMDTO>(rmOutPut, rmData.getCurrentPage(),
					rmData.getTotalPages(), rmData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getRMList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	

	@RequestMapping(value = "/rm/getPaginatedResevRm", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getPaginatedResevRoom(@RequestBody RMFilterInputDTO rmFilterDto) {
		try {
			PagedResponse<Rm> rmData = this.rmService.getPaginatedResevRoom(rmFilterDto);
			List<RMDTO> rmOutPut = ((Collection<Rm>) rmData.getContent()).stream()
					.map(element -> this.mapper.map(element, RMDTO.class)).collect(Collectors.toList());
			GenericPagedResponse<RMDTO> response = new GenericPagedResponse<RMDTO>(rmOutPut, rmData.getCurrentPage(),
					rmData.getTotalPages(), rmData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getPaginatedResevRoom: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
			}
	}

	@RequestMapping(value = "/rm/getAllRmByScroll", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getAllRmByScroll(@RequestBody FilterCriteria filter)     
	{  
		try {
			List<Rm> rmData = this.rmService.getRmOnScroll(filter);
			List<RMFilterInputDTO> rmOutput = rmData.stream().map(this::convertRMTODto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(rmOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getAllRmByScroll: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rm/getPaginatedHotelRm", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getPaginatedHotelRoom(@RequestBody RMFilterInputDTO rmFilterDto) {
		try {
			PagedResponse<Rm> rmData = this.rmService.getPaginatedHotelRoom(rmFilterDto);
			List<RMDTO> rmOutPut = ((Collection<Rm>) rmData.getContent()).stream()
					.map(element -> this.mapper.map(element, RMDTO.class)).collect(Collectors.toList());
			GenericPagedResponse<RMDTO> response = new GenericPagedResponse<RMDTO>(rmOutPut, rmData.getCurrentPage(),
					rmData.getTotalPages(), rmData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getPaginatedHotelRoom: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/rm/getAllRmByCommonArea", method = RequestMethod.POST, produces = "application/json")  
	private ResponseEntity<Object> getAllRmByCommonArea(@RequestBody FilterCriteria filter)     
	{  
		try {
			List<Rm> rmData = this.rmService.getAllRmByCommonArea(filter);
			List<RMFilterInputDTO> rmOutput = rmData.stream().map(this::convertRMTODto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(rmOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmController.getAllRmByCommonArea: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}