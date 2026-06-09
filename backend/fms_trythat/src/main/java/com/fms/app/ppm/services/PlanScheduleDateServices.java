package com.fms.app.ppm.services;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.common.services.EnumsService;
import com.fms.app.helpdesk.models.Parts;
import com.fms.app.helpdesk.models.ProblemType;
import com.fms.app.helpdesk.models.RequestParts;
import com.fms.app.helpdesk.models.RequestTechnician;
import com.fms.app.helpdesk.models.RequestTools;
import com.fms.app.helpdesk.models.RequestTrades;
import com.fms.app.helpdesk.models.SlaRequestParameters;
import com.fms.app.helpdesk.models.SlaResponseParameters;
import com.fms.app.helpdesk.models.Wr;
import com.fms.app.helpdesk.models.dto.EscalationDateTimeDTO;
import com.fms.app.helpdesk.models.dto.FilterApplicableSlaDto;
import com.fms.app.helpdesk.models.dto.RequestDateTimeDTO;
import com.fms.app.helpdesk.models.dto.WrFilterDTO;
import com.fms.app.helpdesk.services.PartsService;
import com.fms.app.helpdesk.services.ProblemTypeServices;
import com.fms.app.helpdesk.services.RequestPartsService;
import com.fms.app.helpdesk.services.RequestTechnicianService;
import com.fms.app.helpdesk.services.RequestToolsService;
import com.fms.app.helpdesk.services.RequestTradesServices;
import com.fms.app.helpdesk.services.SlaRequestParametersServices;
import com.fms.app.helpdesk.services.WrServices;
import com.fms.app.ppm.models.dto.GenerateRequestsFilterDto;
import com.fms.app.ppm.models.dto.PMPlannerOutputDTO;
import com.fms.app.ppm.models.dto.PlanRequestDetailsDto;
import com.fms.app.ppm.models.dto.PlanRequestDetailsFilterDto;
import com.fms.app.ppm.models.dto.PmPlannerRequestDetailsInputDTO;
import com.fms.app.ppm.models.dto.PmPlannerRequestDetailsOutputDTO;
import com.fms.app.ppm.models.PlanPart;
import com.fms.app.ppm.models.PlanScheduleDate;
import com.fms.app.ppm.models.PlanStep;
import com.fms.app.ppm.models.PlanTool;
import com.fms.app.ppm.models.PlanTrade;
import com.fms.app.ppm.models.WrForecast;
import com.fms.app.ppm.repository.PlanScheduleDateRepository;
import com.fms.app.reservation.services.ReservationNativeQueryServices;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.userModels.User;
import com.fms.app.userServices.UserServiceImpl;
import com.fms.app.utils.CommonUtil;

@Service
public class PlanScheduleDateServices {

	@Autowired
	PlanScheduleDateRepository planScheduleDateRepo;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	ReservationNativeQueryServices reservationNativeQueryServices;

	@Autowired
	SlaRequestParametersServices slaRequestParamService;

	@Autowired
	EnumsService enumsService;

	@Autowired
	WrServices wrService;

	@Autowired
	AuthorizeUserInfo userInfo;

	@Autowired
	WrForcastServices wrForcastServices;

	@Autowired
	PlanStepServices planStepSrv;

	@Autowired
	ProblemTypeServices problemTypeServices;

	@Autowired
	PlanTradeServices planTradeServices;

	@Autowired
	PlanToolServices planToolServices;

	@Autowired
	PlanPartServices planPartServices;

	@Autowired
	RequestTradesServices requestTradesSrv;

	@Autowired
	RequestToolsService requestToolsService;

	@Autowired
	RequestPartsService requestPartsService;

	@Autowired
	RequestTechnicianService requestTechnicianService;
	
	@Autowired
	PartsService partsService;
	
	@Autowired
	UserServiceImpl userServiceImpl;
	

	public void save(PlanScheduleDate planScheduleDate) {
		this.planScheduleDateRepo.save(planScheduleDate);
	}

	@Transactional
	public void deleteExistingByUser() {
		int userId = userInfo.getUserIfo().getUserId();
		String query = "delete from wr_forecast where requested_by = "+userId+"";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		nativeQuery.executeUpdate();
	}

	public List<PlanScheduleDate> getAllPlanScheduleDates(GenerateRequestsFilterDto generateRequestsFilterDto) {
		java.sql.Date fromDate = CommonUtil.getSqlDate(generateRequestsFilterDto.getFromDate());
		java.sql.Date toDate = CommonUtil.getSqlDate(generateRequestsFilterDto.getToDate());
		String blRestriction = reservationNativeQueryServices.createIdRestriction("plan_loc_eq.bl_id",
				generateRequestsFilterDto.getBlId());
		String flRestriction = reservationNativeQueryServices.createIdRestriction("plan_loc_eq.fl_id",
				generateRequestsFilterDto.getFlId());
		String rmRestriction = reservationNativeQueryServices.createIdRestriction("plan_loc_eq.rm_id",
				generateRequestsFilterDto.getRmId());
		String eqRestriction = reservationNativeQueryServices.createIdRestriction("plan_loc_eq.eq_id",
				generateRequestsFilterDto.getEqId());
		String planIdRestriction = reservationNativeQueryServices.createIdRestriction("plan_loc_eq.plan_id",
				generateRequestsFilterDto.getPlanId());
		String fromDateRestriction = reservationNativeQueryServices.createDateRestriction(fromDate,
				"plan_sched_date.sched_date", ">=");
		String toDateRestriction = reservationNativeQueryServices.createDateRestriction(toDate,
				"plan_sched_date.sched_date", "<=");
		String enumIdIsActive ="Yes";
		String isActiveRestriction = reservationNativeQueryServices.createRestriction("plan_sched.is_active",
				enumIdIsActive);

		// Gives all the dates by suppressing the lower priority
		String query = "WITH psd AS ( SELECT plan_sched_date.*,plan_sched.priority, "
				+ "RANK() OVER ( PARTITION BY sched_date ORDER BY plan_sched.priority ASC ) AS r "
				+ "FROM plan_sched_date INNER JOIN plan_sched  ON plan_sched_date.plan_sched_id = plan_sched.plan_sched_id "
				+ "WHERE plan_sched.plan_loc_eq_id IN (SELECT plan_loc_eq_id FROM plan_loc_eq WHERE 1=1 "
				+ blRestriction + flRestriction + rmRestriction + eqRestriction + planIdRestriction + ") "
				+ fromDateRestriction + toDateRestriction + isActiveRestriction + " )"
				+ "SELECT plan_sched_date_id,plan_sched_id,sched_date,wr_id FROM psd "
				+ "WHERE r = 1 AND wr_id IS NULL ORDER BY priority ASC";
		Query nativeQuery = this.entityManager.createNativeQuery(query, PlanScheduleDate.class);
		@SuppressWarnings("unchecked")
		List<PlanScheduleDate> dataRecords = nativeQuery.getResultList();
		if (!dataRecords.isEmpty()) {
			return dataRecords;
		}
		return null;
	}

	@Transactional
	public void deleteById(int planScheduleId) {
		this.planScheduleDateRepo.deleteByPlanScheduleIdAndWrIdIsNull(planScheduleId);
	}

	public WrForecast generateRequest(PlanScheduleDate planScheduleDate, String type, Integer emId) {
		return findApplicableSla(planScheduleDate, type,emId);
	}

	public WrForecast findApplicableSla(PlanScheduleDate planScheduleDate, String type,Integer emId) {
		FilterApplicableSlaDto slaDto = new FilterApplicableSlaDto();
		ProblemType problemType = this.problemTypeServices.getPreventiveManitenanceProbType();
		slaDto.setBlId(planScheduleDate.getPlanSchedule().getPlanLocEq().getBlId());
		slaDto.setFlId(planScheduleDate.getPlanSchedule().getPlanLocEq().getFlId());
		slaDto.setRmId(planScheduleDate.getPlanSchedule().getPlanLocEq().getRmId());
		slaDto.setEqId(planScheduleDate.getPlanSchedule().getPlanLocEq().getEqId());

		slaDto.setProbType(problemType.getProblemTypeId());// need add problemtype for ppm

		List<SlaRequestParameters> slaRequests = this.slaRequestParamService.getApplicableSLA(slaDto);
		Set<SlaResponseParameters> slaData = slaRequests.get(0).getSlaResponseParameters();
		SlaResponseParameters applicableSla = new SlaResponseParameters();

		for (SlaResponseParameters sla : slaData) {
			if (sla.getIsDefault().equals("Yes")) {
				applicableSla = sla;
			}
		}

		WrForecast wrForecast = this.getWrForecastBySla(planScheduleDate, applicableSla,
				problemType.getProblemTypeId(), type,emId);
		return wrForecast;
	}

	public WrForecast getWrForecastBySla(PlanScheduleDate planScheduleDate, SlaResponseParameters sla,
			Integer probTypeId, String type,Integer emId) {
		// get EscalationDateTime
		java.util.Date currentDate = new java.util.Date();
        Date today = new Date(currentDate.getTime());
        Time now = new Time(currentDate.getTime());
		EscalationDateTimeDTO escalationDateTimeDTO = getEscalationDates(planScheduleDate.getScheduleDate(), now, sla.getSlaResponseParametersId());

		Map.Entry<Date, Time> respondDateTime = getSqlDateTime(escalationDateTimeDTO.getResponseDateTime());
		Map.Entry<Date, Time> completeDateTime = getSqlDateTime(escalationDateTimeDTO.getCompleteDateTime());

		// create wrForecast
		WrForecast wrForecast = new WrForecast();
		wrForecast.setBlId(planScheduleDate.getPlanSchedule().getPlanLocEq().getBlId());
		wrForecast.setDateRequested(today);
		wrForecast.setDateToPerform(planScheduleDate.getScheduleDate());
		wrForecast.setEqId(planScheduleDate.getPlanSchedule().getPlanLocEq().getEqId());
		wrForecast.setEscDateCompleted(completeDateTime.getKey());
		wrForecast.setEscDateResponded(respondDateTime.getKey());
		wrForecast.setEscTimeCompleted(completeDateTime.getValue());
		wrForecast.setEscTimeResponded(respondDateTime.getValue());
		wrForecast.setFlId(planScheduleDate.getPlanSchedule().getPlanLocEq().getFlId());
		wrForecast.setPlanId(planScheduleDate.getPlanSchedule().getPlanLocEq().getPlanId());
		wrForecast.setProbTypeId(probTypeId);
		if(emId == null || emId == 0) {
			wrForecast.setRequestedBy(userInfo.getUserIfo().getEmId());
			wrForecast.setRequestedFor(userInfo.getUserIfo().getEmId());
		} else {
			wrForecast.setRequestedBy(emId);
			wrForecast.setRequestedFor(emId);
		}
		
		wrForecast.setRmId(planScheduleDate.getPlanSchedule().getPlanLocEq().getRmId());
		wrForecast.setScheduleId(planScheduleDate.getPlanScheduleId());
		wrForecast.setSlaRequestParametersId(sla.getSlaRequestParametersId());
		wrForecast.setSlaResponseParametersId(sla.getSlaResponseParametersId());
		wrForecast.setStatus("Requested");
		wrForecast.setTimeRequested(now);
		wrForecast.setSiteId(planScheduleDate.getPlanSchedule().getPlanLocEq().getBl().getSiteId());
		wrForecast.setBl(planScheduleDate.getPlanSchedule().getPlanLocEq().getBl());
		wrForecast.setFl(planScheduleDate.getPlanSchedule().getPlanLocEq().getFl());
		wrForecast.setRm(planScheduleDate.getPlanSchedule().getPlanLocEq().getRm());
		wrForecast.setEq(planScheduleDate.getPlanSchedule().getPlanLocEq().getEq());

		// save wrForecast
		if (type.equalsIgnoreCase("wrForecast")) {
			wrForecast = this.wrForcastServices.saveWrForecast(wrForecast);
		}

		return wrForecast;
	}

	public Map.Entry<Date, Time> getSqlDateTime(String dateTimeString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		// Parse the string into a LocalDateTime object
		LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);

		// Extract date and time components
		java.sql.Date sqlDate = java.sql.Date.valueOf(localDateTime.toLocalDate());
		java.sql.Time sqlTime = java.sql.Time.valueOf(localDateTime.toLocalTime());
		return new AbstractMap.SimpleEntry<>(sqlDate, sqlTime);
	}

	public EscalationDateTimeDTO getEscalationDates(Date scheduleDate, Time now, int slaResponseParametersId) {
		RequestDateTimeDTO requestDateTimeDTO = new RequestDateTimeDTO();

		requestDateTimeDTO.setRequestDate(scheduleDate.toLocalDate());
		requestDateTimeDTO.setRequestTime(now.toLocalTime());
		requestDateTimeDTO.setSelectedSlaResponseId(slaResponseParametersId);
		EscalationDateTimeDTO escalationDateTimeDTO = this.wrService
				.getEscalatedRespondAndCompleteDateTime(requestDateTimeDTO);
		return escalationDateTimeDTO;
	}

	@Transactional
	public void updatePlanScheduleDate(Integer wrId, Integer PlanScheduleDateId) {
		String query = "update plan_sched_date set wr_id = " + wrId + " where plan_sched_date_id = "
				+ PlanScheduleDateId;
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		nativeQuery.executeUpdate();
	}

	public Object checkIsScheduleCreated(Integer planScheduleId) {
		String query = "SELECT MAX(sched_date) AS maximunDate FROM plan_sched_date WHERE plan_sched_id = "
				+ planScheduleId + " AND wr_id IS NOT NULL";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		Object result = nativeQuery.getSingleResult();
		return result;
	}

	public void SetRequestDetailsByPlan(int planId, int requestId) {
		List<PlanStep> planStepsList = this.planStepSrv.getAllByPlanId(planId);
		int planStepId = planStepsList.size() > 0 ? planStepsList.get(0).getPlanStepId() : 0;
		List<PlanTrade> planTradesList = this.planTradeServices.getAllByStepId(planStepId);
		List<PlanTool> planToolsList = this.planToolServices.getAllByPlanStepId(planStepId);
		List<PlanPart> planPartsList = this.planPartServices.getAllByPlanStepId(planStepId);
		User user  = this.userServiceImpl.getUser("FMS@FMS.COM");//System user
		if (planTradesList.size() > 0) {
			this.setRequestTradeDetails(planTradesList, requestId,user.getUserId());
		}
		if (planToolsList.size() > 0) {
			this.setRequestToolDetails(planToolsList, requestId,user.getUserId());
		}
		if (planPartsList.size() > 0) {
			this.setRequestParts(planPartsList, requestId,user.getUserId());
		}

	}

	public void setRequestTradeDetails(List<PlanTrade> planTradesList, int requestId,int userId) {
		java.sql.Date assignDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		java.sql.Time assignTime = new java.sql.Time(Calendar.getInstance().getTime().getTime());
		// String userName = this.userInfo.getUserIfo().getUsername();
		planTradesList.forEach(planTrade -> {
			RequestTrades requestTrade = new RequestTrades();
			requestTrade.setDateAssign(assignDate);
			requestTrade.setTimeAssign(assignTime);
			requestTrade.setTradeId(planTrade.getTradeId());
			requestTrade.setRequestId(requestId);
			requestTrade.setHoursRequired(planTrade.getHoursRequired());
			requestTrade.setAddedBy(userId);

			this.requestTradesSrv.saveOrUpdate(requestTrade);
		});
	}

	public void setRequestToolDetails(List<PlanTool> planToolsList, int requestId,int userId) {
		java.sql.Date assignDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		java.sql.Time assignTime = new java.sql.Time(Calendar.getInstance().getTime().getTime());
		planToolsList.forEach(planTool -> {
			RequestTools requestTool = new RequestTools();
			requestTool.setDateAssign(assignDate);
			requestTool.setTimeAssign(assignTime);
			requestTool.setToolId(planTool.getToolId());
			requestTool.setRequestId(requestId);
			requestTool.setHoursRequired(planTool.getHoursRequired());
			requestTool.setAddedBy(userId);

			this.requestToolsService.saveOrUpdate(requestTool);
		});
	}

	public void setRequestParts(List<PlanPart> planPartsList, int requestId,int userId) {
		java.sql.Date assignDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		java.sql.Time assignTime = new java.sql.Time(Calendar.getInstance().getTime().getTime());

		planPartsList.forEach(planPart -> {
			RequestParts requestPart = new RequestParts();
			requestPart.setDateAssigned(assignDate);
			requestPart.setTimeAssigned(assignTime);
			requestPart.setPartId(planPart.getPartId());
			requestPart.setRequestId(requestId);
			requestPart.setReqQuantity(planPart.getQunatityRequired());
			requestPart.setAddedBy(userId);

			this.requestPartsService.saveOrUpdate(requestPart);
		});

	}

	public List<PMPlannerOutputDTO> getRequestPlannerDetails(WrFilterDTO filterData) {

		String blRestriction = reservationNativeQueryServices.createIdRestriction("bl_id", filterData.getBlId());
		String flRestriction = reservationNativeQueryServices.createIdRestriction("fl_id", filterData.getFlId());
		String fromDateRestriction = filterData.getDateRequestedFrom();
		String toDateRestriction = filterData.getDateRequestedTo();

		Map<String, List<Map<String, String>>> plannerDetails = new HashMap<>();
		List<Map<String, String>> requestDetails = getRequestCount(blRestriction, flRestriction, fromDateRestriction,
				toDateRestriction);
		List<Map<String, String>> requestTradeDetails = getRequestTradeCountByDate(blRestriction, flRestriction,
				fromDateRestriction, toDateRestriction);
		List<Map<String, String>> requestToolDetails = getRequestToolCountByDate(blRestriction, flRestriction,
				fromDateRestriction, toDateRestriction);
		List<Map<String, String>> requestPartDetails = getRequestPartsCountByDate(blRestriction, flRestriction,
				fromDateRestriction, toDateRestriction);
		List<Map<String, String>> requestTechnicianDetails = getRequestTechnicianByDate(blRestriction, flRestriction,
				fromDateRestriction, toDateRestriction);

		plannerDetails.put("Request", requestDetails);
		plannerDetails.put("Trade", requestTradeDetails);
		plannerDetails.put("Tool", requestToolDetails);
		plannerDetails.put("Part", requestPartDetails);
		plannerDetails.put("Technician", requestTechnicianDetails);
		List<PMPlannerOutputDTO> data = getFormattedPlannerDetails(plannerDetails,filterData.getViewByValue(),filterData.getDateRequestedFrom(),filterData.getDateRequestedTo());
		return data;

	}
	public  List<PMPlannerOutputDTO> getFormattedPlannerDetails(Map<String, List<Map<String, String>>> data,String viewByValue,
			String fromDate,String toDate){
		List<PMPlannerOutputDTO> resultData = new ArrayList<PMPlannerOutputDTO>();
		List<Map<String, Date>> dateObjectList= new ArrayList<Map<String, Date>>();
		if(viewByValue.equalsIgnoreCase("daily")) {
			Date dateFrom = setOriginofDate(fromDate);
			Date dateTo = setOriginofDate(toDate);
		    Date currentDate = dateFrom;
	        while (!currentDate.after(dateTo)) {
	        	Map<String, Date> dateObject = new HashMap<>();
	        	dateObject.put("date1", currentDate);
	        	dateObject.put("date2", null);
	        	dateObjectList.add(dateObject);
	            currentDate = new Date(currentDate.getTime() + 24 * 60 * 60 * 1000); // Add one day
	        }
		}
		else if(viewByValue.equalsIgnoreCase("weekly")) {
			LocalDate fromLocalDate = setOriginofDate(fromDate).toLocalDate();
	        LocalDate toLocalDate = setEndofDate(toDate).toLocalDate();
	        while (!fromLocalDate.isAfter(toLocalDate)) {
	            LocalDate weekStart = fromLocalDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
	            LocalDate weekEnd = weekStart.plusDays(6);
	            Map<String, Date> dateObject = new HashMap<>();
	            dateObject.put("date1", Date.valueOf(weekStart));
	            dateObject.put("date2", Date.valueOf(weekEnd));
	            dateObjectList.add(dateObject);
	            fromLocalDate = weekEnd.plusDays(1);
	        }
		}else if(viewByValue.equalsIgnoreCase("monthly")) {
			LocalDate fromLocalDate = setOriginofDate(fromDate).toLocalDate();
	        LocalDate toLocalDate = setEndofDate(toDate).toLocalDate();
	        while (!fromLocalDate.isAfter(toLocalDate)) {
	            YearMonth yearMonth = YearMonth.from(fromLocalDate);
	            LocalDate monthStart = yearMonth.atDay(1);
	            LocalDate monthEnd = yearMonth.atEndOfMonth();
	            Map<String, Date> dateObject = new HashMap<>();
	            dateObject.put("date1", Date.valueOf(monthStart));
	            dateObject.put("date2", Date.valueOf(monthEnd));
	            dateObjectList.add(dateObject);
	            fromLocalDate = monthEnd.plusDays(1);
	        }
		}
		for(Map<String, Date>dateObject:dateObjectList) {
			resultData.add(geteachDateRangePlannerDetails(data,viewByValue,dateObject.get("date1"),dateObject.get("date2")));
		}
		return resultData;
	}
	
	public PMPlannerOutputDTO geteachDateRangePlannerDetails(Map<String, List<Map<String, String>>> data,String viewByValue,
			Date date1,Date date2){
		PMPlannerOutputDTO resultData = new PMPlannerOutputDTO();
		PMPlannerOutputDTO.Request requestCountMap = new PMPlannerOutputDTO.Request();
		List<PMPlannerOutputDTO.Trade> tradeHoursMap = new ArrayList<>();
        List<PMPlannerOutputDTO.Tool> toolHoursMap = new ArrayList<>();
        List<PMPlannerOutputDTO.Part> partQuantityMap = new ArrayList<>();
        List<PMPlannerOutputDTO.Technician> technicianMap = new ArrayList<>();
        List<Map<String, String>> requestList = data.get("Request");
        List<Map<String, String>> tradeList = data.get("Trade");
        List<Map<String, String>> toolList = data.get("Tool");
        List<Map<String, String>> partList = data.get("Part");
        List<Map<String, String>> technicianList = data.get("Technician");
        requestCountMap.setCount(0);
        int requestCount = 0;
		if(date1!=null && date2==null) {
			for (Map<String, String> req : requestList) {
		        String dateValue = req.get("date");
		        Date dateForComparison = setOriginofDate(dateValue);
		        if(date1.compareTo(dateForComparison)==0) {
		        	int count = Integer.parseInt(req.get("requestCount"));
		        	requestCount+=count;
		        }
		    }
			for (Map<String, String> trade : tradeList) {
		        String dateValue = trade.get("date");
		        Date dateForComparison = setOriginofDate(dateValue);
		        if(date1.compareTo(dateForComparison)==0) {
		        	updateTradeHoursMap(tradeHoursMap,trade);
		        }
		    }
			for (Map<String, String> tool : toolList) {
		        String dateValue = tool.get("date");
		        Date dateForComparison = setOriginofDate(dateValue);
		        if(date1.compareTo(dateForComparison)==0) {
		        	updateToolHoursMap(toolHoursMap,tool);
		        }
		    }
			for (Map<String, String> part : partList) {
		        String dateValue = part.get("date");
		        Date dateForComparison = setOriginofDate(dateValue);
		        if(date1.compareTo(dateForComparison)==0) {
		        	updatePartQuantityMap(partQuantityMap,part);
		        }
		    }
			for (Map<String, String> technician : technicianList) {
		        String dateValue = technician.get("date");
		        Date dateForComparison = setOriginofDate(dateValue);
		        if(date1.compareTo(dateForComparison)==0) {
		        	updateTechnicianMap(technicianMap,technician);
		        }
		    }
		}else if(date1!=null && date2!=null){
			for (Map<String, String> req : requestList) {
		        String dateValue = req.get("date");
		        Date dateForComparison = setOriginofDate(dateValue);
		        if(date1.compareTo(dateForComparison)<=0 && date2.compareTo(dateForComparison)>=0) {
		        	int count = Integer.parseInt(req.get("requestCount"));
		        	requestCount+=count;
		        }
		    }
			for (Map<String, String> trade : tradeList) {
		        String dateValue = trade.get("date");
		        Date dateForComparison = setOriginofDate(dateValue);
		        if(date1.compareTo(dateForComparison)<=0 && date2.compareTo(dateForComparison)>=0) {
		        	updateTradeHoursMap(tradeHoursMap,trade);
		        }
		    }
			for (Map<String, String> tool : toolList) {
		        String dateValue = tool.get("date");
		        Date dateForComparison = setOriginofDate(dateValue);
		        if(date1.compareTo(dateForComparison)<=0 && date2.compareTo(dateForComparison)>=0) {
		        	updateToolHoursMap(toolHoursMap,tool);
		        }
		    }
			for (Map<String, String> part : partList) {
		        String dateValue = part.get("date");
		        Date dateForComparison = setOriginofDate(dateValue);
		        if(date1.compareTo(dateForComparison)<=0 && date2.compareTo(dateForComparison)>=0) {
		        	updatePartQuantityMap(partQuantityMap,part);
		        }
		    }
			for (Map<String, String> technician : technicianList) {
		        String dateValue = technician.get("date");
		        Date dateForComparison = setOriginofDate(dateValue);
		        if(date1.compareTo(dateForComparison)<=0 && date2.compareTo(dateForComparison)>=0) {
		        	updateTechnicianMap(technicianMap,technician);
		        }
		    }
		}
		requestCountMap.setCount(requestCount);
		resultData.setRequest(requestCountMap);
		resultData.setTrade(tradeHoursMap);
		resultData.setTool(toolHoursMap);
		resultData.setPart(partQuantityMap);
		resultData.setTechnician(technicianMap);
		return resultData;
	}
	private Date setOriginofDate(String  date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        LocalDate midnight = localDate.atStartOfDay().toLocalDate();
        return Date.valueOf(midnight);
    }
	
	private Date setEndofDate(String  date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        LocalTime endTime = LocalTime.of(23, 59, 59, 999);
        java.time.LocalDateTime localDateTime = localDate.atTime(endTime);
        Date sqlDate = Date.valueOf(localDateTime.toLocalDate());
        return sqlDate;
    }
	
	public static PMPlannerOutputDTO.Trade findTradeById(List<PMPlannerOutputDTO.Trade> tradeList, String id) {
        for (PMPlannerOutputDTO.Trade trade : tradeList) {
            if (trade.getId().equals(id)) {
                return trade;
            }
        }
        return null;
    }
	
	public static PMPlannerOutputDTO.Tool findToolById(List<PMPlannerOutputDTO.Tool> toolList, String id) {
        for (PMPlannerOutputDTO.Tool tool : toolList) {
            if (tool.getId().equals(id)) {
                return tool;
            }
        }
        return null;
    }
	public static PMPlannerOutputDTO.Technician findTechnicianById(List<PMPlannerOutputDTO.Technician> technicianList, String id) {
        for (PMPlannerOutputDTO.Technician technician : technicianList) {
            if (technician.getId().equals(id)) {
                return technician;
            }
        }
        return null;
    }
	
	public static PMPlannerOutputDTO.Part findPartById(List<PMPlannerOutputDTO.Part> partList, String id) {
        for (PMPlannerOutputDTO.Part part : partList) {
            if (part.getId().equals(id)) {
                return part;
            }
        }
        return null;
    }
	private void updateTradeHoursMap(List<PMPlannerOutputDTO.Trade> tradeHoursMap, Map<String, String> trade) {
		PMPlannerOutputDTO.Trade existingTrade = findTradeById(tradeHoursMap, trade.get("id"));
    	if(existingTrade != null) {
    		existingTrade.setCount(existingTrade.getCount()+Float.parseFloat(trade.get("count")));
    		existingTrade.setAvailableCount(existingTrade.getAvailableCount()+Float.parseFloat(trade.get("availableCount")));
    	}else {
    		tradeHoursMap.add(new PMPlannerOutputDTO.Trade(trade.get("name"), Float.parseFloat(trade.get("count")),trade.get("id"), Float.parseFloat(trade.get("availableCount"))));
    	}
	}
	
	private void updateToolHoursMap(List<PMPlannerOutputDTO.Tool> toolHoursMap, Map<String, String> tool) {
		PMPlannerOutputDTO.Tool existingTrade = findToolById(toolHoursMap, tool.get("id"));
    	if(existingTrade != null) {
    		existingTrade.setCount(existingTrade.getCount()+Float.parseFloat(tool.get("count")));
    		existingTrade.setAvailableCount(existingTrade.getAvailableCount()+Float.parseFloat(tool.get("availableCount")));
    	}else {
    		toolHoursMap.add(new PMPlannerOutputDTO.Tool(tool.get("name"), Float.parseFloat(tool.get("count")),tool.get("id"), Float.parseFloat(tool.get("availableCount"))));
    	}
	}

	private void updateTechnicianMap(List<PMPlannerOutputDTO.Technician> technicianMap, Map<String, String> technician) {
		PMPlannerOutputDTO.Technician existingTrade = findTechnicianById(technicianMap, technician.get("id"));
    	if(existingTrade != null) {
    		existingTrade.setCount(existingTrade.getCount()+Float.parseFloat(technician.get("count")));
    		existingTrade.setAvailableCount(existingTrade.getAvailableCount()+Float.parseFloat(technician.get("availableCount")));
    	}else {
    		technicianMap.add(new PMPlannerOutputDTO.Technician(technician.get("name"), Float.parseFloat(technician.get("count")),technician.get("id"), Float.parseFloat(technician.get("availableCount"))));
    	}
	}

	private void updatePartQuantityMap(List<PMPlannerOutputDTO.Part> partQuantityMap, Map<String, String> part) {
		PMPlannerOutputDTO.Part existingTrade = findPartById(partQuantityMap, part.get("id"));
    	if(existingTrade != null) {
    		existingTrade.setCount(existingTrade.getCount()+Integer.parseInt(part.get("count")));
    		existingTrade.setAvailableCount(existingTrade.getAvailableCount()+Integer.parseInt(part.get("availableCount")));
    	}else {
    		partQuantityMap.add(new PMPlannerOutputDTO.Part(part.get("name"), Integer.parseInt(part.get("count")),part.get("id"),part.get("units"),
    				Integer.parseInt(part.get("availableCount"))));
    	}
	}

	public List<Map<String, String>> getRequestCount(String blRestriction, String flRestriction,
			String fromDateRestriction, String toDateRestriction) {
		List<Map<String, String>> requestData = new ArrayList<Map<String, String>>();
		String query = "SELECT CAST(date_to_perform AS DATE) AS Date,COUNT(*) AS Requests_Count "
				+ " FROM wr WHERE wr.status not in (select enum_key from enum_list where table_name ='wr' "
				+ " and field_name = 'status' and enum_value in ('Rejected','Completed','Close','Cancelled')) and "
				+ "date_to_perform BETWEEN '" + fromDateRestriction + "' AND '" + toDateRestriction 
				+ "' "  + blRestriction + flRestriction + " GROUP BY CAST(date_to_perform AS DATE)";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> dataRecords = nativeQuery.getResultList();
		if (!dataRecords.isEmpty()) {
			dataRecords.forEach(x -> {
				requestData.add(createRequestCountDto(Arrays.asList(x)));
			});
		}
		return requestData;
	}

	public List<Map<String, String>> getRequestTradeCountByDate(String blRestriction, String flRestriction,
			String fromDateRestriction, String toDateRestriction) {
		List<Map<String, String>> requestData = new ArrayList<Map<String, String>>();
		String query = "SELECT date_to_perform, trade_id, required_hours,name,(SELECT"
				+ " COALESCE(SUM(std_hours_avail),0) as Available_Hours from craftsperson "
				+ "WHERE primary_trade = temp.trade_id and cf_id NOT IN ( SELECT technician_id "
				+ "from request_technician INNER JOIN wr on wr.wr_id = request_technician.request_id "
				+ "WHERE wr.date_to_perform = temp.date_to_perform )) AS available_hours FROM"
				+ "( SELECT CAST(wr.date_to_perform AS DATE) AS date_to_perform, rt.trade_id AS trade_id, "
				+ "SUM(rt.hours_required) AS Required_Hours, trd.trade_code as name "
				+ " FROM request_trade rt INNER JOIN wr ON rt.request_id = wr.wr_id"
				+ " INNER JOIN trades trd ON trd.trade_id = rt.trade_id  WHERE  wr.status not in (select enum_key from enum_list where table_name ='wr' "
				+ " and field_name = 'status' and enum_value in ('Rejected','Completed','Close','Cancelled')) and "
				+ " wr.date_to_perform BETWEEN '"	+ fromDateRestriction
				+ "' AND '" + toDateRestriction + "' " + blRestriction + flRestriction
				+ " GROUP BY CAST(wr.date_to_perform AS DATE), rt.trade_id,trd.trade_id,trd.trade_code ) temp "
				+ "ORDER BY temp.date_to_perform"; //trade id need to change as trade name 
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> dataRecords = nativeQuery.getResultList();
		if (!dataRecords.isEmpty()) {
			dataRecords.forEach(x -> {
				requestData.add(createPlannerDetailsDto(Arrays.asList(x),false));
			});
		}
		return requestData;
	}

	public List<Map<String, String>> getRequestToolCountByDate(String blRestriction, String flRestriction,
			String fromDateRestriction, String toDateRestriction) {
		List<Map<String, String>> requestData = new ArrayList<Map<String, String>>();
		String query = "SELECT CAST(wr.date_to_perform AS DATE) AS Date, rtl.tool_id AS tool_id, "
				+ " SUM(rtl.hours_required) AS Required_Hours, tl.tool as name,"
				+ " (tl.std_hours_avail) as Available_Hours FROM "
				+ " request_tools rtl INNER JOIN  wr ON rtl.request_id = wr.wr_id "
				+ " INNER JOIN tools tl ON tl.tools_id = rtl.tool_id  WHERE  wr.status not in (select enum_key from enum_list where table_name ='wr' "
				+ " and field_name = 'status' and enum_value in ('Rejected','Completed','Close','Cancelled') ) and "
				+ "wr.date_to_perform BETWEEN '" + fromDateRestriction + "' AND '" + toDateRestriction + "' " + blRestriction + flRestriction
				+ " GROUP BY CAST(wr.date_to_perform AS DATE), rtl.tool_id ,tl.tool,tl.std_hours_avail";// need to change as tool name
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> dataRecords = nativeQuery.getResultList();
		if (!dataRecords.isEmpty()) {
			dataRecords.forEach(x -> {
				requestData.add(createPlannerDetailsDto(Arrays.asList(x),false));
			});
		}
		return requestData;
	}

	public List<Map<String, String>> getRequestPartsCountByDate(String blRestriction, String flRestriction,
			String fromDateRestriction, String toDateRestriction) {
		List<Map<String, String>> requestData = new ArrayList<Map<String, String>>();
		String query = "SELECT CAST(wr.date_to_perform AS DATE) AS Date, rp.part_id AS part_id, "
				+ " SUM(rp.req_quantity) AS Required_Quantity, pt.part_code as name , pt.qty_on_hand as Available_Quantity "
				+", (SELECT enum_value from enum_list where table_name='parts'  and field_name='unit_of_measurement' and enum_key=pt.unit_of_measurement) as units "
				+ " FROM request_parts rp INNER JOIN wr ON rp.request_id = wr.wr_id  "
				+ " INNER JOIN parts  pt ON pt.part_id = rp.part_id "
				+ " WHERE wr.status not in (select enum_key from enum_list where table_name ='wr' "
				+ " and field_name = 'status' and enum_value in ('Rejected','Completed','Close','Cancelled')) and "
				+ "wr.date_to_perform BETWEEN '"+ fromDateRestriction + "' AND '" + toDateRestriction + "' " + blRestriction + flRestriction
				+ " GROUP BY rp.part_id, CAST(wr.date_to_perform AS DATE) , pt.part_code ,pt.qty_on_hand,pt.unit_of_measurement";//need to change as part name
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> dataRecords = nativeQuery.getResultList();
		if (!dataRecords.isEmpty()) {
			dataRecords.forEach(x -> {
				Map<String, String> partDetails = createPlannerDetailsDto(Arrays.asList(x),true);
				Parts part = this.partsService.getByPartId(Integer.parseInt(partDetails.get("id")));
				partDetails.put("availableCount", Integer.toString(part.getQutOnHand()));
				requestData.add(partDetails);
			});
		}
		return requestData;
	}

	public List<Map<String, String>> getRequestTechnicianByDate(String blRestriction, String flRestriction,
			String fromDateRestriction, String toDateRestriction) {
		List<Map<String, String>> requestData = new ArrayList<Map<String, String>>();
		String query = "SELECT CAST(wr.date_to_perform AS DATE) AS Date, rtc.technician_id AS technician_id, "
				+ "SUM(rtc.hours_required) AS Required_Hours , tech.name as technician_name, (tech.std_hours_avail) as Available_Hours"
				+ " FROM request_technician rtc INNER JOIN wr ON rtc.request_id = wr.wr_id "
				+ " INNER JOIN craftsperson tech ON RTC.technician_id = tech.cf_id WHERE  wr.status not in (select enum_key from enum_list where table_name ='wr' "
				+ " and field_name = 'status' and enum_value in ('Rejected','Completed','Close','Cancelled')) and "
				+ "wr.date_to_perform BETWEEN '"+ fromDateRestriction + "' AND '" + toDateRestriction + "' " + blRestriction + flRestriction
				+ " GROUP BY rtc.technician_id, CAST(wr.date_to_perform AS DATE), tech.name,tech.std_hours_avail";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> dataRecords = nativeQuery.getResultList();
		if (!dataRecords.isEmpty()) {
			dataRecords.forEach(x -> {
				requestData.add(createPlannerDetailsDto(Arrays.asList(x),false));
			});
		}
		return requestData;
	}

	public Map<String, String> createRequestCountDto(List<Object> data) {
		int len = data.size();
		Map<String, String> convertDTO = new HashMap<String, String>();
		for (int index = 0; index < len; index++) {
			if (index == 0) {

				convertDTO.put("date", data.get(0).toString());
			} else {
				convertDTO.put("requestCount", data.get(1).toString());
			}
		}
		return convertDTO;
	}
	
	public Map<String, String> createPlannerDetailsDto(List<Object> data, Boolean isPartsMap) { 
		int len = data.size();
		Map<String, String> convertDTO = new HashMap<String, String>();
		for (int index = 0; index < len; index++) {
			if(index == 0) {
				convertDTO.put("date", data.get(0).toString());
			}else if (index == 1) {
				convertDTO.put("id", data.get(1).toString());
			}else if(index == 2) {
				convertDTO.put("count", data.get(2).toString());
			}else if(index == 3){
				convertDTO.put("name", data.get(3).toString());
			}else if(index==4) {
				convertDTO.put("availableCount", data.get(4).toString());	
			}else if(index==5) {
				convertDTO.put("units", data.get(5).toString());
			}
		}
		return convertDTO;
	}
	
	public List<PlanRequestDetailsDto> getPlanRequestDetails(PlanRequestDetailsFilterDto planRequestDetailsFilterDto) {
		String blRestriction = reservationNativeQueryServices.createIdRestriction("bl_id", planRequestDetailsFilterDto.getBlId());
		String flRestriction = reservationNativeQueryServices.createIdRestriction("fl_id", planRequestDetailsFilterDto.getFlId());
		String fromDateRestriction = planRequestDetailsFilterDto.getFromDate();
		String toDateRestriction = planRequestDetailsFilterDto.getToDate();
		String fieldName = planRequestDetailsFilterDto.getFieldName();
		if(planRequestDetailsFilterDto.getTableName().equals("request_trade")) {
			return getPlanRequestDetailsForTrade(fromDateRestriction,toDateRestriction,blRestriction,flRestriction,fieldName);
		}
		
		if(planRequestDetailsFilterDto.getTableName().equals("request_tools")) {
			return getPlanRequestDetailsForTool(fromDateRestriction,toDateRestriction,blRestriction,flRestriction,fieldName);
		}
		
		if(planRequestDetailsFilterDto.getTableName().equals("request_parts")) {
			return getPlanRequestDetailsForPart(fromDateRestriction,toDateRestriction,blRestriction,flRestriction,fieldName);
		}
		
		if(planRequestDetailsFilterDto.getTableName().equals("request_technician")) {
			return getPlanRequestDetailsForTechnician(fromDateRestriction,toDateRestriction,blRestriction,flRestriction,fieldName);
		}
		
		return null;
	}
	
	public List<PlanRequestDetailsDto> getPlanRequestDetailsForTrade(String fromDateRestriction,String toDateRestriction,String blRestriction
			,String flRestriction,String fieldName) {
		List<PlanRequestDetailsDto> planRequestDetailsList = new ArrayList<PlanRequestDetailsDto>();
		String query = "select request_trade.* from request_trade inner join wr on wr.wr_id = request_trade.request_id "
				+ "inner join trades on request_trade.trade_id = trades.trade_id where wr.date_to_perform between '"
				+ fromDateRestriction + "' AND '" + toDateRestriction + "'  AND request_trade.trade_id = '" + fieldName+ "' " + blRestriction + flRestriction;
		Query nativeQuery = this.entityManager.createNativeQuery(query,RequestTrades.class);
		@SuppressWarnings("unchecked")
		List<RequestTrades> dataRecords = nativeQuery.getResultList();
		if(dataRecords.size() >0) {
			dataRecords.forEach(requestTrade -> {
				PlanRequestDetailsDto planRequestDetailsDto = this.convertToPlanRequestDetailsDto(requestTrade.getTradeId(),
						requestTrade.getTrade().getTradeCode(),requestTrade.getHoursRequired(), requestTrade.getRequestId(), 
						requestTrade.getRequest().getStatus(), requestTrade.getRequest().getDateToPerform().toString());
				
				planRequestDetailsList.add(planRequestDetailsDto);
				
				
			});
		}
		return planRequestDetailsList;
	}
	
	public List<PlanRequestDetailsDto> getPlanRequestDetailsForTool(String fromDateRestriction,String toDateRestriction,String blRestriction
			,String flRestriction,String fieldName) {
		List<PlanRequestDetailsDto> planRequestDetailsList = new ArrayList<PlanRequestDetailsDto>();
		String query = "select request_tools.* from request_tools inner join wr on wr.wr_id = request_tools.request_id "
				+ "inner join tools on request_tools.tool_id = tools.tools_id where wr.date_to_perform between '"
				+ fromDateRestriction + "' AND '" + toDateRestriction + "'  AND request_tools.tool_id = '" + fieldName+ "' "  + blRestriction + flRestriction;
		Query nativeQuery = this.entityManager.createNativeQuery(query,RequestTools.class);
		@SuppressWarnings("unchecked")
		List<RequestTools> dataRecords = nativeQuery.getResultList();
		if(dataRecords.size() >0) {
			dataRecords.forEach(requestTool -> {
				PlanRequestDetailsDto planRequestDetailsDto = this.convertToPlanRequestDetailsDto(requestTool.getToolId(),
						requestTool.getTool().getTool(),requestTool.getHoursRequired(), requestTool.getRequestId(), 
						requestTool.getRequest().getStatus(), requestTool.getRequest().getDateToPerform().toString());
				
				planRequestDetailsList.add(planRequestDetailsDto);
				
				
			});
		}
		return planRequestDetailsList;
	}
	
	public List<PlanRequestDetailsDto> getPlanRequestDetailsForPart(String fromDateRestriction,String toDateRestriction,String blRestriction
			,String flRestriction,String fieldName) {
		List<PlanRequestDetailsDto> planRequestDetailsList = new ArrayList<PlanRequestDetailsDto>();
		String query = "select request_parts.* from request_parts inner join wr on wr.wr_id = request_parts.request_id "
				+ "inner join parts on request_parts.part_id = parts.part_id where wr.date_to_perform between '"
				+ fromDateRestriction + "' AND '" + toDateRestriction  + "'  AND request_parts.part_id = '" + fieldName+ "' "   + blRestriction + flRestriction;
		Query nativeQuery = this.entityManager.createNativeQuery(query,RequestParts.class);
		@SuppressWarnings("unchecked")
		List<RequestParts> dataRecords = nativeQuery.getResultList();
		if(dataRecords.size() >0) {
			dataRecords.forEach(requestPart -> {
				PlanRequestDetailsDto planRequestDetailsDto = this.convertToPlanRequestDetailsDto(requestPart.getPartId(),
						requestPart.getPart().getPartCode(),requestPart.getReqQuantity(), requestPart.getRequestId(), 
						requestPart.getRequest().getStatus(), requestPart.getRequest().getDateToPerform().toString());
				
				planRequestDetailsList.add(planRequestDetailsDto);
				
				
			});
		}
		return planRequestDetailsList;
	}
	
	public List<PlanRequestDetailsDto> getPlanRequestDetailsForTechnician(String fromDateRestriction,String toDateRestriction,String blRestriction
			,String flRestriction,String fieldName) {
		List<PlanRequestDetailsDto> planRequestDetailsList = new ArrayList<PlanRequestDetailsDto>();
		String query = "select request_technician.* from request_technician inner join wr on wr.wr_id = request_technician.request_id "
				+ "inner join craftsperson on request_technician.technician_id = craftsperson.cf_id where wr.date_to_perform between '"
				+ fromDateRestriction + "' AND '" + toDateRestriction  + "'  AND request_technician.technician_id = '" + fieldName+ "' "  + blRestriction + flRestriction;
		Query nativeQuery = this.entityManager.createNativeQuery(query,RequestTechnician.class);
		@SuppressWarnings("unchecked")
		List<RequestTechnician> dataRecords = nativeQuery.getResultList();
		if(dataRecords.size() >0) {
			dataRecords.forEach(requestTechnician -> {
				PlanRequestDetailsDto planRequestDetailsDto = this.convertToPlanRequestDetailsDto(requestTechnician.getTechnicianId(),
						requestTechnician.getTechnician().getName(),requestTechnician.getHoursRequired(), requestTechnician.getRequestId(), 
						requestTechnician.getRequest().getStatus(), requestTechnician.getRequest().getDateToPerform().toString());
				
				planRequestDetailsList.add(planRequestDetailsDto);
				
				
			});
		}
		return planRequestDetailsList;
	}
	
	public PlanRequestDetailsDto convertToPlanRequestDetailsDto(Integer id,String name,double count, Integer requestId, String status, String dateToPerform) {
		PlanRequestDetailsDto planRequestDetailsDto = new PlanRequestDetailsDto();
		planRequestDetailsDto.setId(id); // need to be chnage as int
		planRequestDetailsDto.setName(name);// need to be trade/tool/part name
		planRequestDetailsDto.setCount(count);
		planRequestDetailsDto.setRequestId(requestId);
		planRequestDetailsDto.setStatus(status);
		planRequestDetailsDto.setDateToPerform(dateToPerform);
		
		return planRequestDetailsDto;
	}
	
	public PmPlannerRequestDetailsOutputDTO getPlannerRequestSelectionInfo(PmPlannerRequestDetailsInputDTO filter) {
		PmPlannerRequestDetailsOutputDTO result = new PmPlannerRequestDetailsOutputDTO();
		WrFilterDTO filterData = new WrFilterDTO();
		filterData.setWrId(filter.getWrId());
		List<Wr> wrData = this.wrService.getAllWrBySearchFilter(filterData);
		result.setRequest(wrData.get(0));
		if(filter.getPartId() != null && filter.getPartId() >0) {
			RequestParts part = this.requestPartsService.getByRequestIdAndPartId(filter.getWrId(), filter.getPartId());
			result.setPart(part);
		}
		if(filter.getToolId() != null && filter.getToolId()>0) {
			RequestTools tool = this.requestToolsService.getByRequestAndToolId(filter.getWrId(), filter.getToolId());
			result.setTool(tool);
		}
		if(filter.getTradeId() != null && filter.getTradeId()>0) {
			RequestTrades trade = this.requestTradesSrv.getByRequestIdAndTradeId(filter.getWrId(), filter.getTradeId());
			result.setTrade(trade);
		}
		if(filter.getTechnicianId() != null && filter.getTechnicianId()>0) {
			RequestTechnician technician = this.requestTechnicianService.getByRequestIdandTechnicianId(filter.getWrId(),filter.getTechnicianId());
			result.setTechnician(technician);
		}
		return result;
	}

}
