package com.fms.app.ppm.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.appParams.models.AppParams;
import com.fms.app.appParams.services.AppParamsService;
import com.fms.app.helpdesk.controllers.WrController;
import com.fms.app.helpdesk.models.Wr;
import com.fms.app.helpdesk.models.dto.WrDto;
import com.fms.app.helpdesk.models.dto.WrFilterDTO;
import com.fms.app.helpdesk.services.WrServices;
import com.fms.app.ppm.models.dto.ForecastDataObjectsDto;
import com.fms.app.ppm.models.dto.GenerateRequestsFilterDto;
import com.fms.app.ppm.models.dto.PMPlannerOutputDTO;
import com.fms.app.ppm.models.dto.PlanRequestDetailsDto;
import com.fms.app.ppm.models.dto.PlanRequestDetailsFilterDto;
import com.fms.app.ppm.models.dto.PlanScheduleDTO;
import com.fms.app.ppm.models.dto.PmPlannerRequestDetailsInputDTO;
import com.fms.app.ppm.models.dto.PmPlannerRequestDetailsOutputDTO;
import com.fms.app.ppm.models.LinkPlanToLocationOrAsset;
import com.fms.app.ppm.models.PlanSchedule;
import com.fms.app.ppm.models.PlanScheduleDate;
import com.fms.app.ppm.models.WrForecast;
import com.fms.app.ppm.services.ForecastDetailsService;
import com.fms.app.ppm.services.PlanScheduleDateServices;
import com.fms.app.ppm.services.PlanScheduleServices;
import com.fms.app.reservation.models.dto.RecurrencePatternDTO;
import com.fms.app.userModels.User;
import com.fms.app.userServices.UserServiceImpl;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class PlanScheduleController {

	@Autowired
	ModelMapper mapper;

	@Autowired
	PlanScheduleServices planScheduleSrv;

	@Autowired
	PlanScheduleDateServices planScheduleDateSrv;

	@Autowired
	WrController wrController;
	
	@Autowired
	ForecastDetailsService forecastDetailsService;
	
	@Autowired
	WrServices wrService;
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	AppParamsService apParamService;
	
	private static final Logger logger = LogManager.getLogger(PlanScheduleController.class);

	@RequestMapping(value = "/planSchedule/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> savePlanSchedule(@RequestBody PlanScheduleDTO planScheduleDto) {

		try {
			PlanSchedule planSchedule = this.mapper.map(planScheduleDto, PlanSchedule.class);
			this.planScheduleSrv.saveOrUpdate(planSchedule);

			// delete previous dates in update case
			if (planScheduleDto.getIsEdit()) {
				this.planScheduleDateSrv.deleteById(planScheduleDto.getPlanScheduleId());
			}

			// save schedule dates in planScheduleDate.
			List<String> scheduleDates = planScheduleDto.getScheduleDates();
			scheduleDates.forEach(strDate -> {
				java.sql.Date sqlDate = CommonUtil.getSqlDate(strDate);
				PlanScheduleDate planScheduleDate = new PlanScheduleDate();
				planScheduleDate.setPlanScheduleId(planSchedule.getPlanScheduleId());
				planScheduleDate.setScheduleDate(sqlDate);

				this.planScheduleDateSrv.save(planScheduleDate);
			});
			return new ResponseEntity<>(planSchedule, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanScheduleController.savePlanSchedule: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/planSchedule/getPlanScheduleById/{planLocEqId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getPlanSchedulesById(@PathVariable("planLocEqId") int planLocEqId) {
		try {
			List<PlanSchedule> planScheduleList = this.planScheduleSrv.getByPlanLocEqId(planLocEqId);
			List<PlanScheduleDTO> planScheduleDtoList = planScheduleList.stream()
					.map(item -> this.mapper.map(item, PlanScheduleDTO.class)).collect(Collectors.toList());
			return new ResponseEntity<>(planScheduleDtoList, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanScheduleController.getPlanSchedulesById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/planSchedule/getListOfOccurances", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getListOfOccurances(@RequestBody RecurrencePatternDTO recurrencePatternDTO) {

		try {
			List<LocalDate> totalDates = this.planScheduleSrv.getScheduleDates(recurrencePatternDTO);
			return new ResponseEntity<>(totalDates, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanScheduleController.getListOfOccurances: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/planSchedule/delete/{planScheduleId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> deletePlanSchedulesById(@PathVariable("planScheduleId") int planScheduleId) {

		try {
			this.planScheduleSrv.delete(planScheduleId);
			return new ResponseEntity<>(new ResponseUtil("Record deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanScheduleController.deletePlanSchedulesById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/planSchedule/scheduleList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getScheduleList(@RequestBody GenerateRequestsFilterDto generateRequestsFilterDto) {
		try {
			List<WrForecast> wrForecastList = new ArrayList<WrForecast>();
			this.planScheduleDateSrv.deleteExistingByUser();
			List<PlanScheduleDate> planScheduleDatesList = this.planScheduleDateSrv
					.getAllPlanScheduleDates(generateRequestsFilterDto);
			if (planScheduleDatesList != null) {
				planScheduleDatesList.forEach(planSchduleDate -> {
					WrForecast wrForecast = this.planScheduleDateSrv.generateRequest(planSchduleDate, "wrForecast",generateRequestsFilterDto.getEmId());
					wrForecastList.add(wrForecast);
				});
			}

			return new ResponseEntity<>(wrForecastList, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanScheduleController.getScheduleList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/planSchedule/generateRequest", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> generateRequest(@RequestBody GenerateRequestsFilterDto generateRequestsFilterDto) {
		
		List<WrDto> wrList = new ArrayList<WrDto>();
		List<Wr> wrDataList = new ArrayList<Wr>();
		// this.planScheduleDateSrv.deleteExistingByUser();
		try {
			List<PlanScheduleDate> planScheduleDatesList = this.planScheduleDateSrv
					.getAllPlanScheduleDates(generateRequestsFilterDto);
			if (planScheduleDatesList != null) {
				planScheduleDatesList.forEach(planSchduleDate -> {
					WrForecast wrForecast = this.planScheduleDateSrv.generateRequest(planSchduleDate, "wr",generateRequestsFilterDto.getEmId());
					WrDto wrDto = this.mapper.map(wrForecast, WrDto.class);
					ResponseEntity<Object> res = wrController.saveWr(wrDto);
					Wr wrData = (Wr) res.getBody();
					wrDataList.add(wrData);
					wrDto.setWrId(wrData.getWrId());
					//wrList.add(wrDto);
					this.planScheduleDateSrv.updatePlanScheduleDate(wrData.getWrId(), planSchduleDate.getPlanScheduleDateId());
					this.planScheduleDateSrv.SetRequestDetailsByPlan(wrData.getPlanId(), wrData.getWrId());
				});
			}
			wrList = this.wrService.convertToDtoWithProblemTypeString(wrDataList);
			return new ResponseEntity<>(wrList, HttpStatus.OK);

		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanScheduleController.generateRequest: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/planSchedule/checkSchedGenerated/{planScheduleId}", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> checkIsScheduleGenerated(@PathVariable("planScheduleId") int planScheduleId) {
		try {
			Object result = this.planScheduleDateSrv.checkIsScheduleCreated(planScheduleId);
			if(result != null) {
				return new ResponseEntity<>(result, HttpStatus.OK);
			}		
			return new ResponseEntity<>(new ResponseUtil("schedules not generated", HttpStatus.OK.value()),
					HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanScheduleController.checkIsScheduleGenerated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/planSchedule/forecast", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getForeCastDetails(@RequestBody GenerateRequestsFilterDto generateRequestsFilterDto) {
		
		try {
			List<WrDto> wrDtoList = new ArrayList<WrDto>();
			ForecastDataObjectsDto.FinalObject forecastDetails = new ForecastDataObjectsDto.FinalObject();
			this.planScheduleDateSrv.deleteExistingByUser();
			
			List<PlanScheduleDate> planScheduleDatesList = this.planScheduleDateSrv
					.getAllPlanScheduleDates(generateRequestsFilterDto);
			forecastDetails = this.forecastDetailsService.getForecastDetailsByDates(planScheduleDatesList,generateRequestsFilterDto);
			if (planScheduleDatesList != null) {
				planScheduleDatesList.forEach(planSchduleDate -> {
					WrForecast wrForecast = this.planScheduleDateSrv.generateRequest(planSchduleDate, "forecast",generateRequestsFilterDto.getEmId());
					Wr wrData = this.mapper.map(wrForecast, Wr.class);
					WrDto wrDto = this.wrService.setLocationDetails(wrData);
					wrDtoList.add(wrDto);
				});
			}
			forecastDetails.requestsList = wrDtoList;
			return new ResponseEntity<>(forecastDetails, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanScheduleController.getForeCastDetails: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/planSchedule/planner", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getPlannerData(@RequestBody WrFilterDTO filterData) {
		try {
			List<PMPlannerOutputDTO> listDateCount =   this.planScheduleDateSrv.getRequestPlannerDetails(filterData);
			return new ResponseEntity<>(listDateCount, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanScheduleController.getPlannerData: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/planSchedule/getPlanRequestDetails", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getPlanRequestDetails(@RequestBody PlanRequestDetailsFilterDto filterData) {
		try {
			List<PlanRequestDetailsDto> data = this.planScheduleDateSrv.getPlanRequestDetails(filterData);
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanScheduleController.getPlanRequestDetails: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/forecast/details", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getForecastPlanDetails(
			@RequestBody GenerateRequestsFilterDto generateRequestsFilterDto) {

		try {
			List<LinkPlanToLocationOrAsset> plansList = new ArrayList<LinkPlanToLocationOrAsset>();
			this.planScheduleDateSrv.deleteExistingByUser();// delete wrForecast data
			List<PlanScheduleDate> planScheduleDatesList = this.planScheduleDateSrv
					.getAllPlanScheduleDates(generateRequestsFilterDto);
			if (planScheduleDatesList != null) {
				plansList = planScheduleDatesList.stream()
						.map(item -> this.mapper.map(item.getPlanSchedule().getPlanLocEq(), LinkPlanToLocationOrAsset.class))
						.collect(Collectors.toList());
			}
			Set<LinkPlanToLocationOrAsset> uniquePlansSet = new HashSet<>();
			uniquePlansSet.addAll(plansList);
			return new ResponseEntity<>(uniquePlansSet, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanScheduleController.getForecastPlanDetails: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/planSchedule/getplannerreqselectioninfo", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getplannerreqselectioninfo(@RequestBody PmPlannerRequestDetailsInputDTO filterData) {
		try {
			PmPlannerRequestDetailsOutputDTO result =   this.planScheduleDateSrv.getPlannerRequestSelectionInfo(filterData);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanScheduleController.getplannerreqselectioninfo: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@Scheduled(cron = "0 0 1 * * ?")//Runs every day at 1am
	public void ppmScheduler() {
		
		try {
			AppParams apParamData = this.apParamService.getAppParamsByParamId("ppm_scheduler_run_days");
			String schedDays = apParamData != null ? apParamData.getParamValue() : null;
			int toDays = Integer.parseInt(schedDays);
			LocalDate fromDate = LocalDate.now();
			LocalDate toDate = fromDate.plusDays(toDays - 1);

			GenerateRequestsFilterDto generateRequestsFilterDto = new GenerateRequestsFilterDto();
			generateRequestsFilterDto.setFromDate(fromDate.toString());
			generateRequestsFilterDto.setToDate(toDate.toString());
			User user = this.userServiceImpl.getUser("FMS@FMS.COM");// System user
			generateRequestsFilterDto.setEmId(user.getEmId());
			this.generateRequest(generateRequestsFilterDto);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in PlanScheduleController.ppmScheduler: " + stacktrace, e);
		}
	}

}
