package com.fms.app.helpdesk.services;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.dashboard.services.SearchCriteria;
import com.fms.app.helpdesk.models.RequestParts;
import com.fms.app.helpdesk.models.SlaResponseParameters;
import com.fms.app.helpdesk.models.Wr;
import com.fms.app.helpdesk.models.dto.EscalationDateTimeDTO;
import com.fms.app.helpdesk.models.dto.RequestDateTimeDTO;
import com.fms.app.helpdesk.models.dto.RequestReportEqDto;
import com.fms.app.helpdesk.models.dto.TechnicianTimeUsageDto;
import com.fms.app.helpdesk.models.dto.WrDto;
import com.fms.app.helpdesk.models.dto.WrFilterDTO;
import com.fms.app.helpdesk.repository.IWrRepository;
import com.fms.app.reservation.services.ReservationNativeQueryServices;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.GenericSpesification;
import com.fms.app.utils.SearchOperation;

@Service
public class WrServices {

	@Autowired
	IWrRepository repository;

	@Autowired
	AuthorizeUserInfo userInfo;

	@Autowired
	ReservationNativeQueryServices reservationNativeQueryServices;

	@Autowired
	SlaResponseParametersServices slaResponseService;

	@Autowired
	HolidayService holidayService;

	@Autowired
	ProblemTypeServices problemTypeServices;

	@Autowired
	ModelMapper mapper;

	@PersistenceContext
	private EntityManager entityManager;

	public Wr getWrId(int wrId) {
		return repository.findByWrId(wrId);
	}

	public List<Wr> getAllWr() {
		return repository.findAll();
	}

	public Wr saveOrUpdate(Wr wr) {
		return repository.save(wr);
	}

	public List<Wr> getAllWrBySearchFilter(WrFilterDTO filterData) {
		String query = getWrSearchFilterQuery(filterData,false);
		Query nativeQuery = this.entityManager.createNativeQuery(query, Wr.class);
		@SuppressWarnings("unchecked")
		List<Wr> dataRecords = nativeQuery.getResultList();
		if (!dataRecords.isEmpty()) {
			return dataRecords;
		}
		return null;
	}
	
	public Map<String, Object> getAllWrBySearchFilterPaginated(WrFilterDTO filterData) {
		int pageSize=0;
		int pageNumber =0;
		int totalRecords = 0;
		if(filterData.getFilterDto() !=null && filterData.getFilterDto().getPaginationDTO()!=null) {
			 pageSize=filterData.getFilterDto().getPaginationDTO().getPageSize();
			 pageNumber =filterData.getFilterDto().getPaginationDTO().getPageNo();
		}
		String queryStr = "";
		String initailString = getWrSearchFilterQuery(filterData,false);
		if(filterData.getFilterDto() !=null &&  filterData.getFilterDto().getFilterCriteria()!=null && filterData.getFilterDto().getFilterCriteria().getFieldName()!=null) {
			if(filterData.getFilterDto().getFilterCriteria().getFieldName().equalsIgnoreCase("wr_id")) {
				String filterCondition =CommonUtil.getPaginationFilterQueryWithoutWhere(filterData.getFilterDto().getFilterCriteria());
				initailString += " and "+filterCondition+" )";
			}else if(filterData.getFilterDto().getFilterCriteria().getFieldName().equalsIgnoreCase("eq_code")) {
				String filterCondition = CommonUtil.getPaginationFilterQueryWithoutWhere(filterData.getFilterDto().getFilterCriteria());
				initailString += " and eq_id in (select eq_id from eq where " +filterCondition +" ) ";
			}else {
				String filterCondition = CommonUtil.getPaginationFilterQuery(filterData.getFilterDto().getFilterCriteria());
				initailString += filterCondition;
			}
		}
		if(pageSize>0) {
			queryStr= " "+initailString+" ORDER BY(SELECT NULL) OFFSET "+pageNumber*pageSize+" ROWS FETCH NEXT "+pageSize+" ROWS ONLY" ;
		}else {
			queryStr = initailString;
		}
		Query nativeQuery = this.entityManager.createNativeQuery(queryStr, Wr.class);
		@SuppressWarnings("unchecked")
		List<Wr> dataRecords = nativeQuery.getResultList();
		Query countQuery = this.entityManager.createNativeQuery(initailString,Wr.class);
		@SuppressWarnings("unchecked")
		List<Wr> dataRecordsCountList = countQuery.getResultList();
		totalRecords = dataRecordsCountList.size();
		Map<String, Object> result = new HashMap<>();
		result.put("content", dataRecords);
		result.put("totalElements", totalRecords);
		return result;
	}
	
	public List<Map<String, Object>> getAllWrStatusCountBySearchFilter(WrFilterDTO filterData){
		List<Map<String, Object>> emData = new ArrayList<Map<String, Object>>();
		String query = "WITH Resultdata as ( "+getWrSearchFilterQuery(filterData,true)+" ) SELECT enum_list.enum_value as status,COALESCE(COUNT(Resultdata.status), 0) AS count FROM enum_list LEFT JOIN Resultdata ON enum_list.enum_key = Resultdata.status where enum_list.table_name='wr' and enum_list.field_name='status' GROUP BY enum_list.enum_value"
				+ " ,enum_list.enum_list_id ORDER BY enum_list.enum_list_id";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			emData.add(converObjectTOWrStatusCount(Arrays.asList(x)));
		});
		return emData;
	}
	
	private String getWrSearchFilterQuery(WrFilterDTO filterData,boolean isStatusCount) {
		String wrId = filterData.getWrId() != null && filterData.getWrId() > 0 ? "wr_id = " + Integer.toString(filterData.getWrId()) : "1=1";
		String siteRestriction = reservationNativeQueryServices.createIdRestriction("site_id", filterData.getSiteId());
		String blRestriction = reservationNativeQueryServices.createIdRestriction("bl_id", filterData.getBlId());
		String flRestriction = reservationNativeQueryServices.createIdRestriction("fl_id", filterData.getFlId());
		String rmRestriction = reservationNativeQueryServices.createIdRestriction("rm_id", filterData.getRmId());
		String eqRestriction = reservationNativeQueryServices.createIdRestriction("eq_id", filterData.getEqId());
		String statusRestriction = reservationNativeQueryServices.createRestriction("status", filterData.getStatus());
		String problemTypeRestriction = reservationNativeQueryServices.createIdRestriction("prob_type_id",
				filterData.getProblemTypeId());
		String requestedForRequestriction = reservationNativeQueryServices.createIdRestriction("requested_for",
				filterData.getRequestedFor());

		String dateRequestedFrom = filterData.getDateRequestedFrom();
		String dateRequestedTo = filterData.getDateRequestedTo();

		String dateRequestedRestriction = dateRequestedFrom != null && dateRequestedTo != null
				? " and date_to_perform BETWEEN '" + dateRequestedFrom + "' AND '" + dateRequestedTo + "' "
				: " and 1=1 ";
		String showRequestTypeRestriction = getShowRequestTypeRestriction(filterData.getShowRequestType());
		String userBasedChecks = this.createQueryToGetUserBasedChecks(isStatusCount);
		String query = userBasedChecks + " and " + wrId + siteRestriction + blRestriction + flRestriction
				+ rmRestriction + eqRestriction + statusRestriction + problemTypeRestriction + showRequestTypeRestriction
				+ requestedForRequestriction + dateRequestedRestriction;
		return query;
	}

	public EscalationDateTimeDTO getEscalatedRespondAndCompleteDateTime(RequestDateTimeDTO requestDateTime) {

		final LocalDate requestDate = requestDateTime.getRequestDate();
		final LocalTime requestTime = requestDateTime.getRequestTime();
		final Integer selectedSlaResponseId = requestDateTime.getSelectedSlaResponseId();
		SlaResponseParameters slaResponseData = this.slaResponseService.getById(selectedSlaResponseId);
		final LocalTime startTime = slaResponseData.getDayStartTime().toLocalTime();
		final LocalTime endTime = slaResponseData.getDayEndTime().toLocalTime();
		int respondDays = slaResponseData.getTimeToResponseDays();
		double respondHours = slaResponseData.getTimeToResponseHours();
		int completeDays = slaResponseData.getTimeToCompleteDays();
		double completeHours = slaResponseData.getTimeToCompleteHours();

//		Working days List.
		ArrayList<String> workingWeekDaysList = this.getWorkingWeekDaysList(slaResponseData.getWorkingDays());

//		Holiday Dates List.
		ArrayList<LocalDate> holidayDatesList = this.getHolidayDatesList(slaResponseData, requestDate);

//		Get the Escalation Minutes for Respond.
		int escalatedRespondMinutes = this.getMinutes(respondDays, respondHours, startTime, endTime);

//		Get Escalation Date and Time to Respond.
		LocalDateTime responseDateTime = this.getEscalatedDateTime(startTime, endTime, holidayDatesList, requestDate,
				requestTime, workingWeekDaysList, escalatedRespondMinutes);

//		Get the Escalation Minutes for Complete.
		int escalatedCompleteMinutes = this.getMinutes(completeDays, completeHours, startTime, endTime);

//		Get Escalation Date and Time to Complete.
		LocalDateTime completeDateTime = this.getEscalatedDateTime(startTime, endTime, holidayDatesList, requestDate,
				requestTime, workingWeekDaysList, escalatedCompleteMinutes);

		EscalationDateTimeDTO escalationDateTimeDto = new EscalationDateTimeDTO();
		escalationDateTimeDto.setResponseDateTime(responseDateTime.toString());
		escalationDateTimeDto.setCompleteDateTime(completeDateTime.toString());

		return escalationDateTimeDto;

	}

	public Integer getMinutes(int days, double hours, LocalTime startTime, LocalTime endTime) {
		Duration duration = Duration.between(startTime, endTime);
		double serviceWindowtimeDurationInHrs = duration.toHours();
		int escalatedMinutes = (int) (((serviceWindowtimeDurationInHrs * days) + hours) * 60);
		return escalatedMinutes;
	}

	public LocalDateTime getEscalatedDateTime(LocalTime startTime, LocalTime endTime, List<LocalDate> holidays,
			LocalDate requestDate, LocalTime requestTime, List<String> weekWorkDaysList, int EscalatedMins) {
		LocalDate escalationDate = requestDate;
		LocalTime escalationTime = requestTime;
		int tempEscalatedMins = EscalatedMins;
		Duration duration = Duration.between(startTime, endTime);
		double serviceWindow = duration.toMinutes(); // in Minutes.
		boolean isSameDay = true;
		if (escalationTime.compareTo(endTime) > 0) {
			escalationDate = escalationDate.plusDays(1);
			escalationTime = startTime;
			isSameDay = false;
		} else if (escalationTime.compareTo(startTime) < 0) {
			escalationTime = startTime;
		}

		while (tempEscalatedMins > 0) {

			LocalDate nextWorkingDate = this.getNextWorkingDay(escalationDate, holidays, weekWorkDaysList);

			if ((escalationDate == nextWorkingDate) && isSameDay) {
				Duration tempMinsDuration = Duration.between(escalationTime, endTime);
				double tempMins = tempMinsDuration.toMinutes();
				tempEscalatedMins = (int) (tempEscalatedMins - tempMins);
				isSameDay = false;
			} else {
				escalationDate = nextWorkingDate;
				double tempMins = serviceWindow;
				tempEscalatedMins = (int) (tempEscalatedMins - tempMins);
				escalationTime = startTime;
			}
			if (tempEscalatedMins == 0) {
				escalationTime = endTime;
			} else if (tempEscalatedMins < 0) {
				escalationTime = endTime.plusMinutes(tempEscalatedMins);
			} else {
				escalationDate = escalationDate.plusDays(1);
				escalationTime = startTime;
			}
		}
		return LocalDateTime.of(escalationDate, escalationTime);

	}

	@SuppressWarnings("unlikely-arg-type")
	public LocalDate getNextWorkingDay(LocalDate escalationDate, List<LocalDate> holidays,
			List<String> weekWorkDaysList) {
		LocalDate temp = escalationDate;
		boolean flag = true;
		while (flag) {
			if (holidays.contains(temp) || (!weekWorkDaysList.contains(temp.getDayOfWeek().toString()))) {
				temp = temp.plusDays(1);
			} else {
				flag = false;
			}
		}
		return temp;
	}

	public ArrayList<String> getWorkingWeekDaysList(String workingDays) {
		List<String> workingDaysList = new ArrayList<String>(Arrays.asList(workingDays.split(",")));
		String[] weekDays = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
		List<String> weekDaysList = Arrays.asList(weekDays);
		ArrayList<String> weekWorkDaysList = new ArrayList<String>();
		int i = 0;
		for (String day : workingDaysList) {
			if (day.equals("1")) {
				weekWorkDaysList.add(weekDaysList.get(i));
			}
			i = i + 1;
		}
		return weekWorkDaysList;
	}

	public ArrayList<LocalDate> getHolidayDatesList(SlaResponseParameters slaResponseData, LocalDate requestDate) {
		ArrayList<LocalDate> holidays = new ArrayList<LocalDate>();
		if (slaResponseData.getCanWorkOnHoliday() != 1) { // Check Can Work On Holidays
			holidays.addAll(this.holidayService.getHolidayDateList(requestDate));
		}
		return holidays;
	}

	public String createQueryToGetUserBasedChecks(boolean isStatusCount) {

		int loggedInEm = this.userInfo.getUserIfo().getEmId();
		int loggedInUser = this.userInfo.getUserIfo().getUserId();

		String query = "select case when (requested_by = " + loggedInEm + ") " + "or (requested_for = " + loggedInEm
				+ " ) then '1' else '0' end as is_requestor, " // to check isRequestor
				+ "case when exists (select * from request_steps_log " + "where step_type = 'Approval' "
				+ "and step_status = 'Pending'" + "and user_id = " + loggedInUser + " "
				+ "and request_steps_log.request_id = wr.wr_id) then '1' else '0' end as is_approver,"// to check
																										// isApprover

				+ "case when exists(select * from request_technician where request_technician.request_id=wr.wr_id "
				+ "and technician_id = (select technician_id from fm_users where user_id = " + loggedInUser
				+ ")) then 1 else 0 end as is_technician, " // To check isTechnician

				+ " case when exists(select fm_users.user_id from fm_users inner join  user_security_group on fm_users.user_id  = user_security_group.user_id "
				+ "where fm_users.user_id = " + loggedInUser
				+ "  and user_security_group.security_group_id=(select security_group_id from security_group where group_name ='Facility Supervisor')"
				+ " union select fm_users.user_role_id from fm_users "
				+ " inner join  user_security_group on fm_users.user_role_id  = user_security_group.user_role_id "
				+ " where fm_users.user_id = " + loggedInUser
				+ " and user_security_group.security_group_id=(select security_group_id from security_group where group_name ='Facility Supervisor')" + ") "
				+ "  then 1 else 0 end as is_supervisor"; // To check isSuperVisor
				// Below restrictions were either or based restrictions.
		if(isStatusCount) {
			query += ",wr_id,status";
		}else {
			query += ",*";
		}
		query += " from wr where (requested_by =  " + loggedInEm + " or requested_for = " + loggedInEm // add logged in user restriction.
				+ "or exists (select * from request_steps_log " + "where step_type = 'Approval'"
				+ "and step_status = 'Pending'" + " and user_id = " + loggedInUser
				+ "and request_steps_log.request_id = wr.wr_id)" // add isApprover restriction.
				+ "or exists(select * from request_technician " + "where request_technician.request_id=wr.wr_id "
				+ "and wr.status not in ('Requested','Approved','Rejected')"
				+ "and technician_id = (select technician_id from fm_users " + "where user_id = " + loggedInUser
				+ ")) " // add isTechnician restriction.
				+ " or (exists(select fm_users.user_id from fm_users inner join  user_security_group on fm_users.user_id  = user_security_group.user_id "
				+ "where fm_users.user_id = " + loggedInUser
				+ " and user_security_group.security_group_id=(select security_group_id from security_group where group_name ='Facility Supervisor')"
				+ " union select fm_users.user_role_id from fm_users "
				+ " inner join  user_security_group on fm_users.user_role_id  = user_security_group.user_role_id "
				+ " where fm_users.user_id = " + loggedInUser
				+ " and user_security_group.security_group_id=(select security_group_id from security_group where group_name ='Facility Supervisor')" + ")))"; // add isSupervisor restriction
		return query;
	}

	public List<Wr> getAll(Map<String, Object> filter) {
		GenericSpesification<Wr> specification = new GenericSpesification<Wr>();

		if (filter.containsKey("from")) {
			specification
					.add(new SearchCriteria("dateRequested", filter.get("from"), SearchOperation.GREATER_THAN_EQUAL));
		}

		if (filter.containsKey("to")) {
			specification.add(new SearchCriteria("dateRequested", filter.get("to"), SearchOperation.LESS_THAN_EQUAL));
		}

		if (filter.containsKey("eqId")) {
			specification.add(new SearchCriteria("eqId", filter.get("eqId"), SearchOperation.EQUAL));
		}
		return this.repository.findAll(specification);
	}

	public List<Map<String, Object>> getRequestCountByEquipmentId(RequestReportEqDto filterData) {
		List<Map<String, Object>> reportData = new ArrayList<Map<String, Object>>();

		String blRestriction = reservationNativeQueryServices.createIdRestriction("bl_id", filterData.getBlId());
		String flRestriction = reservationNativeQueryServices.createIdRestriction("fl_id", filterData.getFlId());
		String fromDateRestriction = reservationNativeQueryServices.createDateRestriction(filterData.getFromDate(),
				"date_requested", ">=");
		String toDateRestriction = reservationNativeQueryServices.createDateRestriction(filterData.getToDate(),
				"date_requested", "<=");

		String query = "select eq_id ,COUNT(wr_id) as RequestCount from wr " + "where (1=1) " + blRestriction
				+ flRestriction + fromDateRestriction + toDateRestriction + "and eq_id is not null group by eq_id";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> dataRecords = nativeQuery.getResultList();
		dataRecords.forEach(x -> {
			reportData.add(convertObjectTOReportData(Arrays.asList(x)));
		});

		return reportData;
	}

	private Map<String, Object> convertObjectTOReportData(List<Object> data) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();

		IntStream.range(0, len).forEach(index -> {
			if (index == 1) {
				convertDTO.put("requestCount", data.get(index));
			} else {
				convertDTO.put("eqId", data.get(index));

			}

		});
		return convertDTO;
	}

	public List<Wr> getRequestByEquipmentId(RequestReportEqDto filterData) {

		String blRestriction = reservationNativeQueryServices.createIdRestriction("bl_id", filterData.getBlId());
		String flRestriction = reservationNativeQueryServices.createIdRestriction("fl_id", filterData.getFlId());
		String fromDateRestriction = reservationNativeQueryServices.createDateRestriction(filterData.getFromDate(),
				"date_requested", ">=");
		String toDateRestriction = reservationNativeQueryServices.createDateRestriction(filterData.getToDate(),
				"date_requested", "<=");
		String eqIdRestriction = reservationNativeQueryServices.createIdRestriction("eq_id", filterData.getEqId());

		String query = "select * from wr " + "where (1=1) " + blRestriction + flRestriction + fromDateRestriction
				+ toDateRestriction + eqIdRestriction;

		Query nativeQuery = this.entityManager.createNativeQuery(query, Wr.class);
		@SuppressWarnings("unchecked")
		List<Wr> dataRecords = nativeQuery.getResultList();

		return dataRecords;
	}

	public Map<String, Object> getRequestByPartCode(RequestReportEqDto filterData) {
		int pageSize=0;
		int pageNumber =0;
		int totalRecords = 0;
		if(filterData.getFilterDto() !=null && filterData.getFilterDto().getPaginationDTO()!=null) {
			 pageSize=filterData.getFilterDto().getPaginationDTO().getPageSize();
			 pageNumber =filterData.getFilterDto().getPaginationDTO().getPageNo();
		}
		String queryStr = "";
		String initailString = "";
		String fromDateRestriction = reservationNativeQueryServices.createDateRestriction(filterData.getFromDate(),
				"date_assigned", ">=");
		String toDateRestriction = reservationNativeQueryServices.createDateRestriction(filterData.getToDate(),
				"date_assigned", "<=");
		String partCodeRestriction = reservationNativeQueryServices.createIdWithoutAndRestriction("part_id",
				filterData.getPartId());
		String showRequestTypeRestriction = getShowRequestTypeRestriction(filterData.getShowRequestType());
		initailString = "select request_parts.* from request_parts " + " where " +partCodeRestriction+ fromDateRestriction + toDateRestriction + " "
				+ " and request_id in  (select wr_id from wr where 1=1 " + showRequestTypeRestriction + " )";
		if(filterData.getFilterDto() !=null &&  filterData.getFilterDto().getFilterCriteria()!=null && filterData.getFilterDto().getFilterCriteria().getFieldName()!=null) {
			if(filterData.getFilterDto().getFilterCriteria().getFieldName().equalsIgnoreCase("part_code")) {
				String filterCondition =CommonUtil.getPaginationFilterQueryWithoutWhere(filterData.getFilterDto().getFilterCriteria());
				initailString += " and part_id in ( select part_id from parts where "+filterCondition+" )";
			}else if(filterData.getFilterDto().getFilterCriteria().getFieldName().equalsIgnoreCase("request_id")) {
				String filterCondition = CommonUtil.getPaginationFilterQueryWithoutWhere(filterData.getFilterDto().getFilterCriteria());
				initailString += " and " +filterCondition;
			}else {
				String filterCondition = CommonUtil.getPaginationFilterQuery(filterData.getFilterDto().getFilterCriteria());
				initailString += filterCondition;
			}
		}
		if(pageSize>0) {
			queryStr= " "+initailString+" ORDER BY(SELECT NULL) OFFSET "+pageNumber*pageSize+" ROWS FETCH NEXT "+pageSize+" ROWS ONLY" ;
		}else {
			queryStr = initailString;
		}
		Query nativeQuery = this.entityManager.createNativeQuery(queryStr, RequestParts.class);
		@SuppressWarnings("unchecked")
		List<RequestParts> dataRecords = nativeQuery.getResultList();
		Query countQuery = this.entityManager.createNativeQuery("SELECT COUNT(*) FROM (" + initailString + ") AS totalCount");
		Object totalRecordsObject = countQuery.getSingleResult();
		if (totalRecordsObject != null) {
		    totalRecords = ((Number) totalRecordsObject).intValue();
		}
		Map<String, Object> result = new HashMap<>();
		result.put("content", dataRecords);
		result.put("totalElements", totalRecords);
		return result;
	}
	

	public List<TechnicianTimeUsageDto> getRequestByTechnicianTimeUsageAnalysis(RequestReportEqDto filterData) {
		List<TechnicianTimeUsageDto> dtoList = new ArrayList<TechnicianTimeUsageDto>();
		String fromDateRestriction = reservationNativeQueryServices.createDateRestriction(filterData.getFromDate(),
				"date_assign", ">=");
		String toDateRestriction = reservationNativeQueryServices.createDateRestriction(filterData.getToDate(),
				"date_assign", "<=");
		String technicianTimeRestriction = filterData.getTechnicianId() != null && filterData.getTechnicianId() > 0 ? " request_technician.technician_id = " + Integer.toString(filterData.getTechnicianId()) : " 1=1 ";
		String showRequestTypeRestriction = getShowRequestTypeRestriction(filterData.getShowRequestType());
		String query = "select	request_technician.request_id," + " request_technician.technician_id,"
				+ " request_technician.date_assign," + " request_technician.time_assign,"
				+ " request_technician.hours_required,"
				// " request_technician_log.work_type,"
				+ " SUM(request_technician_log.actual_hours_std) AS total_hours_std,"
				+ " SUM(request_technician_log.actual_hours_double) AS total_hours_double,"
				+ " SUM(request_technician_log.actual_hours_overtime) AS total_hours_overtime, request_technician_log.work_type"
				+ " from request_technician inner join " + " request_technician_log on "
				+ " request_technician.technician_id = request_technician_log.technician_id"
				+ " and request_technician.request_id = request_technician_log.request_id  "
				+ " where  " + technicianTimeRestriction + fromDateRestriction
				+ toDateRestriction + " and request_technician.request_id in (select wr_id from wr where 1=1 "+ showRequestTypeRestriction + " ) "
				+ " group by request_technician.request_id," + " request_technician.technician_id,"
				+ "	request_technician.date_assign," + "	request_technician.time_assign,"
				+ "	request_technician.hours_required,request_technician_log.work_type";
		// + " request_technician_log.work_type";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> DataRecords = nativeQuery.getResultList();
		DataRecords.forEach(x -> {
			dtoList.add(convertObjectTOReportTechnicianTimeData(Arrays.asList(x)));
		});
		return dtoList;
	}

	private TechnicianTimeUsageDto convertObjectTOReportTechnicianTimeData(List<Object> data) {
		TechnicianTimeUsageDto convertDTO = new TechnicianTimeUsageDto();
		final int len = data.size();

		IntStream.range(0, len).forEach(index -> {
			if (index == 0) {
				int requestId = (int) data.get(index);
				convertDTO.setRequestId(requestId);
			}
			if (index == 1) {

				int technicianId = (int) data.get(index);
				convertDTO.setTechnicianId(technicianId);
			}
			if (index == 2) {
				String dateAssign = data.get(index).toString();
				convertDTO.setDateAssign(dateAssign);
			}
			if (index == 3) {
				String timeAssign = data.get(index).toString();
				convertDTO.setTimeAssign(timeAssign);
			}
			if (index == 4) {
				double hourRequired = Double.valueOf(data.get(index).toString());
				convertDTO.setHoursRequired(hourRequired);
			}
			if (index == 5) {
				double actualHoursStd = Double.valueOf(data.get(index).toString());
				convertDTO.setActualHoursStd(actualHoursStd);
			}
			if (index == 6) {
				double actualHoursDouble = Double.valueOf(data.get(index).toString());
				convertDTO.setActualHoursDouble(actualHoursDouble);
			}
			if (index == 7) {
				double actualHoursOvertime = Double.valueOf(data.get(index).toString());
				convertDTO.setActualHoursOvertime(actualHoursOvertime);
			}
			if (index == 8) {
				String workType = data.get(index).toString();
				convertDTO.setWorkType(workType);
			}
		});
		return convertDTO;
	}

	public List<TechnicianTimeUsageDto> getWorkingHoursByTechnicianIdAndRequestId(RequestReportEqDto filterData) {
		List<TechnicianTimeUsageDto> dtoList = new ArrayList<TechnicianTimeUsageDto>();
		String technicianIdRestriction = reservationNativeQueryServices.createIdRestriction("technician_id",
				filterData.getTechnicianId());
		String query = "select work_type,sum(actual_hours_std) as sum_of_standard_hours,"
				+ "sum(actual_hours_double) as sum_of_double_hours,"
				+ "sum(actual_hours_overtime) as sum_of_overtime_hours "
				+ "  from request_technician_log where request_id = " + filterData.getRequestId()
				+ technicianIdRestriction + " group by work_type ";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> DataRecords = nativeQuery.getResultList();
		DataRecords.forEach(x -> {
			dtoList.add(convertObjectTOReportTechnicianIdAndRequestIdData(Arrays.asList(x)));
		});
		return dtoList;
	}

	private TechnicianTimeUsageDto convertObjectTOReportTechnicianIdAndRequestIdData(List<Object> data) {
		TechnicianTimeUsageDto convertDTO = new TechnicianTimeUsageDto();
		final int len = data.size();

		IntStream.range(0, len).forEach(index -> {

			if (index == 0) {
				// Integer grandChildCount = ((BigInteger) result[1]).intValue();
				//int workType = (int) data.get(index);
				convertDTO.setWorkType(data.get(index).toString());
			}
			if (index == 1) {
				double actualHoursStd = Double.valueOf(data.get(index).toString());
				convertDTO.setActualHoursStd(actualHoursStd);
			}
			if (index == 2) {
				double actualHoursDouble = Double.valueOf(data.get(index).toString());
				convertDTO.setActualHoursDouble(actualHoursDouble);
			}
			if (index == 3) {
				double actualHoursOvertime = Double.valueOf(data.get(index).toString());
				convertDTO.setActualHoursOvertime(actualHoursOvertime);
			}
		});
		return convertDTO;
	}

	public List<WrDto> convertToDtoWithProblemTypeString(List<Wr> wrData) {
		String problemTypeString = "";
		List<WrDto> wrDtoData = new ArrayList<WrDto>();
		for (Wr record : wrData) {
			int problemTypeId = record.getProbTypeId();

//		Get Problem Type String
			problemTypeString = this.problemTypeServices.getProblemTypeString(problemTypeId);

			WrDto wrDto = this.setLocationDetails(record);
			wrDto.setProblemTypeString(problemTypeString);			
			wrDtoData.add(wrDto);
		}
		return wrDtoData;
	}

//Get budget calculations as estimation and actual cost for a request by technician(s).
	public List<Map<String, Object>> getBudgetForTechnician(int requestId) {
		List<Map<String, Object>> reportData = new ArrayList<Map<String, Object>>();
		// get estimated value
		String estimatedCostQuery = "SELECT isnull(SUM(request_technician.hours_required * craftsperson.rate_hourly), 0)  AS estimationCost "
				+ "FROM request_technician "
				+ "INNER JOIN  craftsperson ON request_technician.technician_id = craftsperson.cf_id " + "WHERE "
				+ " request_technician.request_id = " + requestId;

		Query nativeQuery1 = this.entityManager.createNativeQuery(estimatedCostQuery);
		@SuppressWarnings("unchecked")
		Object estimateResult = nativeQuery1.getSingleResult();
		double estimatedCost = estimateResult != null ? ((Number) estimateResult).doubleValue() : 0.0;

		// get actual value
		String actualCostQuery = "SELECT "
				+ "isnull(SUM(request_technician_log.actual_hours_std * craftsperson.rate_hourly), 0) "
				+ "+ isnull(SUM(request_technician_log.actual_hours_double * craftsperson.rate_double), 0) "
				+ "+ isnull(SUM(request_technician_log.actual_hours_overtime * craftsperson.rate_over), 0) "
				+ "AS actual_cost " + "FROM request_technician_log "
				+ "INNER JOIN craftsperson ON request_technician_log.technician_id = craftsperson.cf_id "
				+ "WHERE request_technician_log.request_id = " + requestId;
		Query nativeQuery2 = this.entityManager.createNativeQuery(actualCostQuery);
		@SuppressWarnings("unchecked")
		Object actualCostResult = nativeQuery2.getSingleResult();
		double actualCost = actualCostResult != null ? ((Number) actualCostResult).doubleValue() : 0.0;
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		convertDTO.put("EstimateCost", estimatedCost);
		convertDTO.put("ActualCost", actualCost);
		reportData.add(convertDTO);

		return reportData;
	}

	// Get budget calculations as estimation and actual cost for a request by
	// part(s).
	public List<Map<String, Object>> getBudgetForParts(int RequestId) {

		String query = "SELECT" + " isnull(SUM(parts.rate_per_unit*request_parts.req_quantity), 0)  AS PartQuantity,"
				+ " isnull(SUM(parts.rate_per_unit*request_parts.actual_quantity_used), 0) AS PartQuantityUsed "
				+ " FROM parts inner Join request_parts ON parts.part_id=request_parts.part_id "
				+ " where request_parts.request_id= " + RequestId;

		return this.executeBudgetQuery(query);
	}

	// Get budget calculations as estimation and actual cost for a request by
	// tool(s).
	public List<Map<String, Object>> getBudgetForTools(int RequestId) {
		String query = "SELECT "
				+ " isnull(SUM(request_tools.hours_required*tools.rate_hourly), 0) AS RequiredToosRate,"
				+ " isnull(SUM(request_tools.actual_hours_std*tools.rate_hourly), 0) AS ToolsStdHours,"
				+ " isnull(SUM(request_tools.actual_hours_double*tools.rate_double), 0)  AS ToolsdoubleHours,"
				+ " isnull(SUM(request_tools.actual_hours_overtime*tools.rate_over), 0) AS ToolsOverHours "
				+ " from tools inner join request_tools ON tools.tools_id=request_tools.tool_id "
				+ " where request_tools.request_id = " + RequestId;

		return this.executeBudgetQuery(query);
	}

	// Get budget calculations as estimation and actual cost for a request from
	// Other Costs(s).
	public List<Map<String, Object>> getBudgetForOtherCost(int RequestId) {

		String query = "SELECT " + " isnull(SUM(estimated_cost), 0) AS OtherEstCost ,"
				+ " isnull(SUM(actual_cost), 0) AS OtherActualCost " + " FROM request_other_cost where request_id = "
				+ RequestId;

		return this.executeBudgetQuery(query);
	}

	// Convert Object into Budget data
	private Map<String, Object> convertObjectTOBudgetData(List<Object> data) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		double estimatedCost = 0;
		double actualCost = 0;
		for (int index = 0; index < len; index++) {
			if (index == 0) {
				estimatedCost += Double.valueOf(data.get(index).toString());
				convertDTO.put("EstimateCost", estimatedCost);
			} else {
				actualCost += Double.valueOf(data.get(index).toString());
				convertDTO.put("ActualCost", actualCost);
			}
		}
		return convertDTO;
	}

	// Get Budget for Trade
	public List<Map<String, Object>> getBudgetForTrade(int requestId) {

		String query = "SELECT "
				+ "isnull(SUM(request_trade.hours_required*trades.rate_hourly), 0) AS RequiredTradeRate,"
				+ " isnull(SUM(request_trade.actual_hours_std*trades.rate_hourly), 0) AS TradeStdHours,"
				+ " isnull(SUM(request_trade.actual_hours_double*trades.rate_double), 0)  AS TradedoubleHours, "
				+ " isnull(SUM(request_trade.actual_hours_overtime*trades.rate_over), 0) AS TradeOverHours  "
				+ " from trades inner join request_trade ON trades.trade_id=request_trade.trade_id  "
				+ " where request_trade.request_id = " + requestId;

		return this.executeBudgetQuery(query);
	}

	public List<Map<String, Object>> executeBudgetQuery(String query) {

		List<Map<String, Object>> reportData = new ArrayList<Map<String, Object>>();
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> dataRecords = nativeQuery.getResultList();
		if (!dataRecords.isEmpty()) {
			dataRecords.forEach(x -> {
				reportData.add(convertObjectTOBudgetData(Arrays.asList(x)));
			});
		} else {
			Map<String, Object> convertDTO = new HashMap<String, Object>();
			convertDTO.put("EstimateCost", 0.0);
			convertDTO.put("ActualCost", 0.0);
			reportData.add(convertDTO);
		}
		return reportData;
	}
	
	
	public String getShowRequestTypeRestriction(String showType) {
		String restriction = " and 1=1 ";
		if(showType != null) {
			if(showType.equalsIgnoreCase("facilities")) {
				restriction = " and prob_type_id != (select problem_type_id from problem_type where prob_type = 'Preventive Maintenance') ";
			} else if(showType.equalsIgnoreCase("ppm")) {
				restriction = " and prob_type_id = (select problem_type_id from problem_type where prob_type = 'Preventive Maintenance') ";
			} else if (showType.equalsIgnoreCase("pmPlanner")) {
				restriction = " and status not ('Rejected','Completed','Close','Cancelled')";
			}
		}
		
		return restriction;
	}
	
	
	public WrDto setLocationDetails(Wr record) {
		WrDto wrDto = this.mapper.map(record, WrDto.class);
		//WrDto wrDto = new WrDto();
		wrDto.setBuilding(record.getBl().getBlCode() + (record.getBl().getBlName() == null ? "":" - "+ record.getBl().getBlName()));
		if(wrDto.getFlId() != null && wrDto.getFlId() != 0) {
			wrDto.setFloor(record.getFl().getFlCode() + (record.getFl().getFlName() == null ? "":" - "+ record.getFl().getFlName()));
		}
		if(wrDto.getRmId() != null && wrDto.getRmId() != 0 ) {
			wrDto.setRoom(record.getRm().getRmCode() +(record.getRm().getRmName() == null ? "": " - "+record.getRm().getRmName()));
		}
		if(wrDto.getEqId() != null && wrDto.getEqId() != 0 ) {
			wrDto.setEqCode(record.getEq().getEqCode());
		}
		
		return wrDto;
	}
	
	private Map<String, Object> converObjectTOWrStatusCount(List<Object> data){
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		IntStream.range(0, len).forEach(index -> {
			if (index == 0) {
				convertDTO.put("status", data.get(index));
			}if (index == 1) {
				convertDTO.put("count", data.get(index));
			}
		});
		return convertDTO;
	}
}