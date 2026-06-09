package com.fms.app.reservation.services;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.appParams.models.AppParams;
import com.fms.app.appParams.services.AppParamsService;
import com.fms.app.common.models.Enums;
import com.fms.app.reservation.models.Reservation;
import com.fms.app.reservation.models.dto.RecurrencePatternDTO;
import com.fms.app.reservation.models.dto.ReservationSearchFilterDTO;
import com.fms.app.reservation.models.dto.ReservationsIdsDTO;
import com.fms.app.reservation.repository.ReservationRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.spaceManagement.services.BlService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.TimezoneConverter;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class ReservationService {

	@Autowired
	ReservationRepository reservationRepo;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	AppParamsService apParamService;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	@Autowired
	BlService blService;
	
	public Reservation saveOrUpdate(Reservation data) {
		Reservation record = reservationRepo.save(data);
		return record;
	}
	
	//Get all dates for recurrence booking
	public List<LocalDate> getBookingDates(RecurrencePatternDTO recurrencePatternDTO) {
		final String recType = recurrencePatternDTO.getType();
		final LocalDate dateStart = recurrencePatternDTO.getDateStart();
		final LocalDate dateEnd = recurrencePatternDTO.getDateEnd();
		final int frequency = recurrencePatternDTO.getFrequency();
		final int[] weekDays = parseIntArray(
				recurrencePatternDTO.getWeekDays().length() > 0 ? recurrencePatternDTO.getWeekDays().split(",") : null);
		final int[] monthDays = parseIntArray(
				recurrencePatternDTO.getMonthDays().length() > 0 ? recurrencePatternDTO.getMonthDays().split(",")
						: null);
		final int[] weeks = parseIntArray(
				recurrencePatternDTO.getWeeks().length() > 0 ? recurrencePatternDTO.getWeeks().split(",") : null);

		List<LocalDate> bookingDates = new ArrayList<LocalDate>();
		switch (recType) {
		case "daily":
			bookingDates = getDailyBookingDates(dateStart, dateEnd, frequency);
			break;

		case "weekly":
			bookingDates = getWeeklyBookingDates(dateStart, dateEnd, weekDays, frequency);
			break;

		case "annually":
			bookingDates = getAnnualyBookingDates(dateStart, dateEnd, frequency);
			break;

		case "monthly":
			bookingDates = getMonthlyBookingDates(dateStart, dateEnd, monthDays, weekDays, weeks, frequency);
			break;
		}

		return bookingDates;
	}

	private static List<LocalDate> getMonthlyBookingDates(final LocalDate dateStart, final LocalDate dateEnd,
			final int monthDays[], final int weekDays[], final int weeks[], final int frequency) {
		final List<LocalDate> bookingDates = new ArrayList<LocalDate>();
        final LocalDate tempDate = dateStart;
        LocalDate monthStartDate = tempDate.withDayOfMonth(1);
        LocalDate monthEndDate =
                tempDate.withDayOfMonth(tempDate.getMonth().length(tempDate.isLeapYear()));

        if (monthDays != null) {
            while (monthStartDate.compareTo(dateEnd) <= 0) {
                for (final int monthDay : monthDays) {
                    monthStartDate = monthStartDate.plusDays(monthDay - 1);
                    if (monthStartDate.compareTo(dateStart) >= 0
                            && monthStartDate.compareTo(dateEnd) <= 0
                            && monthEndDate.compareTo(monthStartDate) >= 0) {
                        bookingDates.add(monthStartDate);
                    } else {
                        monthStartDate = monthStartDate.plusDays(-(monthDay - 1));
                    }
                    monthStartDate = monthStartDate.withDayOfMonth(1);
                }
                monthStartDate = monthStartDate.plusMonths(frequency);
                monthEndDate = monthStartDate
                    .withDayOfMonth(monthStartDate.getMonth().length(monthStartDate.isLeapYear()));
            }
        } else {
            while (monthStartDate.compareTo(dateEnd) <= 0) {
                for (final int week : weeks) {
                    for (int i = 0; i < 7; i++) {
                        if (weekDays[i] == 1) {
                            final DayOfWeek weekday = DayOfWeek.of(i+1);
                            final LocalDate adjustedDate = monthStartDate
                                .with(TemporalAdjusters.dayOfWeekInMonth(week, weekday));
                            if (adjustedDate.compareTo(dateStart) >= 0 &&adjustedDate.compareTo(dateEnd) <= 0) {
                                bookingDates.add(adjustedDate);
                            }
                        }
                    }
                }
                monthStartDate = monthStartDate.plusMonths(frequency);
                monthEndDate = monthStartDate
                    .withDayOfMonth(monthStartDate.getMonth().length(monthStartDate.isLeapYear()));
            }
        }

        return bookingDates;
	}

	private static List<LocalDate> getAnnualyBookingDates(final LocalDate dateStart, final LocalDate dateEnd,
			final int frequency) {
        final List<LocalDate> bookingDates = new ArrayList<LocalDate>();
        LocalDate tempDate = dateStart;
        while (tempDate.compareTo(dateEnd) <= 0) {
            bookingDates.add(tempDate);
            tempDate = tempDate.plusYears(frequency);
        }
        
        return bookingDates;
    }

	private static List<LocalDate> getWeeklyBookingDates(final LocalDate dateStart, final LocalDate dateEnd,
			final int[] weekDays, final int frequency) {
        final List<LocalDate> bookingDates = new ArrayList<LocalDate>();
        WeekFields weekFields
        = WeekFields.of(DayOfWeek.MONDAY, 1);
        
        final TemporalField fields = weekFields.dayOfWeek();

        LocalDate weekDateStart = dateStart.with(fields, 1);

        while (weekDateStart.compareTo(dateEnd) <= 0) {
            for (int i = 0; i < 7; i++) {
                final int day = weekDays.length > i ? weekDays[i] : 0;
                if (day == 1 && weekDateStart.compareTo(dateStart) >= 0) {
                    bookingDates.add(weekDateStart);
                }
                weekDateStart = weekDateStart.plusDays(1);
                if (weekDateStart.compareTo(dateEnd) > 0) {
                    break;
                }
               
            }
            weekDateStart = weekDateStart.plusWeeks(frequency - 1);
        }

        return bookingDates;
    }

	private static List<LocalDate> getDailyBookingDates(final LocalDate dateStart, final LocalDate dateEnd,
			final int frequency) {
        final List<LocalDate> bookingDates = new ArrayList<LocalDate>();
        LocalDate tempDate = dateStart;
        while (tempDate.compareTo(dateEnd) <= 0) {
            bookingDates.add(tempDate);
            tempDate = tempDate.plusDays(frequency);
        }

        return bookingDates;
    }

	//To get integer array from string values
	private static int[] parseIntArray(String[] arr) {
		if (arr != null) {
			int[] ints = new int[arr.length];
			for (int i = 0; i < ints.length; i++) {
				ints[i] = Integer.parseInt(arr[i]);
			}
			return ints;
		}
		return null;
	}
	
public List<Reservation> getReservationDetailsForActions(String action,String timeInterval) {
		List<Reservation> reservations = getLatestReservations(action);
		List<Reservation> reslist = new ArrayList<Reservation>();
			
		if(!reservations.isEmpty())	{
			for (Reservation reservation : reservations) {
				
				int blId = reservation.getBlId();
				String query1 = "SELECT timezone_id FROM bl WHERE bl_id = "+ blId;
				
				Query nativeQuery1 = this.entityManager.createNativeQuery(query1);
				@SuppressWarnings("unchecked")
				
				String blTimeZone = (String) nativeQuery1.getSingleResult();
				
				Date dateStart = reservation.getDateStart();
				Time timeStart = reservation.getTimeStart();
				Time timeEnd = reservation.getTimeEnd();
				
				ZonedDateTime dateStartAppendedTZ = TimezoneConverter.getDateTimewithTimeZone(dateStart, timeStart,
						blTimeZone);
				ZonedDateTime dateEndAppendedTZ = TimezoneConverter.getDateTimewithTimeZone(dateStart, timeEnd,
						blTimeZone);
				ZonedDateTime convertedCurrentDateTimeBLTZ = TimezoneConverter
						.convertDateTimeToZone(ZonedDateTime.now(), blTimeZone);

				long diffInMinForCheckInReminder = getDifferenceInMinutes(convertedCurrentDateTimeBLTZ,dateStartAppendedTZ);
			
     			long diffInMinForCancel = getDifferenceInMinutes(dateStartAppendedTZ,convertedCurrentDateTimeBLTZ);

     			long diffInMinForComplete = getDifferenceInMinutes(dateEndAppendedTZ,convertedCurrentDateTimeBLTZ);

				switch (action) {
				case "checkReminder":
					if (diffInMinForCheckInReminder > 0
							&& diffInMinForCheckInReminder <= Integer.parseInt(timeInterval)) {
						reslist.add(reservation);
					}
					break;
				case "cancel":
					if (diffInMinForCancel > 0 && diffInMinForCancel >= Integer.parseInt(timeInterval)) {
						reslist.add(reservation);
					}
					break;
				case "autoCheckin":
					if (diffInMinForCancel >= 0) {
						reslist.add(reservation);
					}
					break;
				case "complete":
					if (diffInMinForComplete >= 0) {
						reslist.add(reservation);
					}
					break;
				}
			}
		}
		return reslist;
	}
	
	public List<Reservation> getAllReservations(){
		return reservationRepo.findAll();
	}
	
	public List<ReservationsIdsDTO> getAllReservationIds(){
		List<Reservation> allData = reservationRepo.findAll();
		List<ReservationsIdsDTO> reservationIdsList = new ArrayList<ReservationsIdsDTO>();
		if (!allData.isEmpty()) {
			reservationIdsList = allData.stream().map(reservation -> {
		        ReservationsIdsDTO dto = new ReservationsIdsDTO();
		        dto.setId(reservation.getReserveId()); 
		        dto.setName(reservation.getMeetingName());
		        return dto;
		    }).collect(Collectors.toList());
		}
		return reservationIdsList;
	}
	
	public List<Reservation> getAllReservationByStatus(String status){
		return reservationRepo.findByStatus(status);
	}
	
	public PagedResponse<Reservation> getAllReservationByStatusPaginated(ReservationSearchFilterDTO data){
		GenericSpecification<Reservation> clientSpecification = new GenericSpecification<>();
        Specification<Reservation> spec = clientSpecification.buildSpecificationMultiple(data.getFilterDto().getFilterCriteria());
        if (data.getStatus() != null && data.getStatus().length() > 1) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), data.getStatus()));
        }
        final String sortOrder = data.getFilterDto().getPaginationDTO().getSortOrder();
        final String sortBy[] = data.getFilterDto().getPaginationDTO().getSortBy();
        final int pageSize = data.getFilterDto().getPaginationDTO().getPageSize();
        final int pageNo = data.getFilterDto().getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Reservation> reservationPage = reservationRepo.findAll(spec,pageable);
        return PagedResponse.fromPage(reservationPage);
	}
	
	public List<Reservation> getReqCheckin(){
		String query = "SELECT  * FROM  reserve  WHERE status in ('Approved','Check In') AND date_start BETWEEN (GETDATE() -1) And (GETDATE() +1) ";
		List<Reservation> result = getReqCheckinData(query);
		return result;
	}
	
	public Map<String, Object> getReqCheckinPaginated(FilterDTOCopy filterDto){
		int pageSize=0;
		int pageNumber =0;
		int totalRecords = 0;
		String queryStr = "";
		if(filterDto !=null && filterDto.getPaginationDTO()!=null) {
			 pageSize=filterDto.getPaginationDTO().getPageSize();
			 pageNumber =filterDto.getPaginationDTO().getPageNo();
		}
		String initailString = "select R.* from reserve R join bl B on R.bl_id=B.bl_id join fl F on R.fl_id=F.fl_id join rm RM on R.rm_id=RM.rm_id join fm_users U on R.requested_by=U.user_id WHERE R.status in ('Approved','Check In') AND R.date_start BETWEEN (GETDATE() -1) And (GETDATE() +1) ";
		if(filterDto !=null &&  filterDto.getFilterCriteria()!=null) {
			String filterCondition = CommonUtil.getPaginationFilterQueryMultipleWithoutWhere(filterDto.getFilterCriteria());
			initailString += filterCondition;
		}
		if(pageSize>0) {
			queryStr= " "+initailString+" ORDER BY(SELECT NULL) OFFSET "+pageNumber*pageSize+" ROWS FETCH NEXT "+pageSize+" ROWS ONLY" ;
		}else {
			queryStr = initailString;
		}
		List<Reservation> queryStrResult = getReqCheckinData(queryStr);
		List<Reservation> initailStringResult = getReqCheckinData(initailString);
		Map<String, Object> result = new HashMap<>();
		result.put("content", queryStrResult);
		result.put("totalElements", initailStringResult.size());
		return result;
	}
	
	public List<Reservation> getReqCheckinData(String query){
		AppParams apParamData = this.apParamService.getAppParamsByParamId("meeting_check_in_time_interval");
		String checkInTimeInterval = apParamData != null ? apParamData.getParamValue() : null;
		Query nativeQuery = this.entityManager.createNativeQuery(query, Reservation.class);
		@SuppressWarnings("unchecked")
		List<Reservation> dataRecords = nativeQuery.getResultList();
		List<Reservation> resReqCheckInlist = new ArrayList<Reservation>();
		if (!dataRecords.isEmpty()) {
			for (Reservation reservation : dataRecords) {
				int blId = reservation.getBlId();
				String query1 = "SELECT timezone_id FROM bl WHERE bl_id = " + blId ;
				Query nativeQuery1 = this.entityManager.createNativeQuery(query1);
				String blTimeZone = (String) nativeQuery1.getSingleResult();
				Date dateStart = reservation.getDateStart();
				Time timeStart = reservation.getTimeStart();
				ZonedDateTime dateStartAppendedTZ = TimezoneConverter.getDateTimewithTimeZone(dateStart, timeStart,
						blTimeZone);
				ZonedDateTime currentDateTime = ZonedDateTime.now();
				ZonedDateTime convertedCurrentDateTimeBLTZ = TimezoneConverter.convertDateTimeToZone(currentDateTime,
						blTimeZone);
				long diffInMinForApprovedStatus = getDifferenceInMinutes(convertedCurrentDateTimeBLTZ,dateStartAppendedTZ);
				long diffInMinForCheckInStatus = getDifferenceInMinutes(dateStartAppendedTZ,convertedCurrentDateTimeBLTZ);
				if (reservation.getStatus().equals("Approved") && diffInMinForApprovedStatus <= Long.parseLong(checkInTimeInterval)) {
					resReqCheckInlist.add(reservation);
				} else if (reservation.getStatus().equals("Check In") && diffInMinForCheckInStatus >= 0) {
					resReqCheckInlist.add(reservation);
				}	
			}
		}	
		return resReqCheckInlist;
	}
	
	public Reservation getReservationById(int id) {
		Reservation record = reservationRepo.findById(id).orElse(null);
		return record;
	}
	
	// this method accept current date and returns list of reservation of current
	// day , day before current day and day after current day
	public List<Reservation> getLatestReservations(String action){
		
        String status = "'Approved','Check In'";
        if(action.equals("complete")) {
        	status = status + ",'Waiting for Approval'";
        }
        
		String query = "SELECT  * FROM  reserve  WHERE status in (" + status
				+ ") AND date_start BETWEEN (GETDATE() -1) And (GETDATE() +1)";
		Query nativeQuery = this.entityManager.createNativeQuery(query, Reservation.class);
		@SuppressWarnings("unchecked")
		List<Reservation> dataRecords = nativeQuery.getResultList();
		return dataRecords;
	}
	
	public long getDifferenceInMinutes(ZonedDateTime dateStart,ZonedDateTime dateEnd) {	
		Duration duration = Duration.between(dateStart,dateEnd);
		return duration.toMinutes();
	}
	
	public String getEnumIdByStatus(String status) {
		String query = "select * from enum_list where table_name = 'reserve' and field_name = 'status' and enum_value = '"+ status +"' ";
		Query nativeQuery = this.entityManager.createNativeQuery(query,Enums.class);
		@SuppressWarnings("unchecked")
		List<Enums>  enumIdForStatus =  nativeQuery.getResultList();
		String result = enumIdForStatus.isEmpty() ? "" : enumIdForStatus.get(0).getEnumKey();
		return result;
	}
	
	public String getStatusByEnumId(int enumId) {
		String query = "select * from enum_list where table_name = 'reserve' and field_name = 'status' and enum_list_id = "+ enumId;
		Query nativeQuery = this.entityManager.createNativeQuery(query,Enums.class);
		@SuppressWarnings("unchecked")
		List<Enums> statusForEnumId = nativeQuery.getResultList();
		return statusForEnumId.get(0).getEnumValue();
	}
}
