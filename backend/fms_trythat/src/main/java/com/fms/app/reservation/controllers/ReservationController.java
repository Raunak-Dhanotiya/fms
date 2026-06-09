package com.fms.app.reservation.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fms.app.appParams.models.AppParams;
import com.fms.app.appParams.services.AppParamsService;
import com.fms.app.appParams.services.MessagesService;
import com.fms.app.notification.controllers.NotifyRequestor;
import com.fms.app.reservation.models.Reservation;
import com.fms.app.reservation.models.ReserveAttendees;
import com.fms.app.reservation.models.ReserveResources;
import com.fms.app.reservation.models.dto.BookingReportsFilterDto;
import com.fms.app.reservation.models.dto.RecurrenceBookingInputDTO;
import com.fms.app.reservation.models.dto.RecurrencePatternDTO;
import com.fms.app.reservation.models.dto.ReservationInputDTO;
import com.fms.app.reservation.models.dto.ReservationOutputDTO;
import com.fms.app.reservation.models.dto.ReservationSearchFilterDTO;
import com.fms.app.reservation.models.dto.ReservationsIdsDTO;
import com.fms.app.reservation.models.dto.ReserveAttendeesDTO;
import com.fms.app.reservation.models.dto.ReserveDTO;
import com.fms.app.reservation.models.dto.ReserveResourcesDTO;
import com.fms.app.reservation.models.dto.ReserveRsAvailableCheckDTO;
import com.fms.app.reservation.services.ReservationNativeQueryServices;
import com.fms.app.reservation.services.ReservationService;
import com.fms.app.reservation.services.ReserveAttendeeService;
import com.fms.app.reservation.services.ReserveFilterService;
import com.fms.app.reservation.services.ReserveResourcesService;
import com.fms.app.reservation.services.RmConfigService;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@RestController
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class ReservationController {

	private static final Logger logger = LogManager.getLogger(ReservationController.class);
	@Autowired
	ReservationService reservationSrv;

	@Autowired
	ReserveAttendeeService reserveAttendeeService;

	@Autowired
	ReserveResourcesService reserveResourcesService;

	@Autowired
	ModelMapper mapper;

	@Autowired
	AuthorizeUserInfo userInfo;

	@Autowired
	NotifyRequestor notifyReq;

	@Autowired
	MessagesService messagesService;

	@Autowired
	RmConfigService rmConfigService;
	
	@Autowired
	ReserveFilterService reserveFilterService;

	@Autowired
	AppParamsService apParamService;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	ReservationNativeQueryServices reservationNativeQuerySrv;

	@RequestMapping(value = "/reservation/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveReservation(@RequestBody ReservationInputDTO dataRecords) {
		java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		java.sql.Time currentTime = new java.sql.Time(Calendar.getInstance().getTime().getTime());

		ReserveDTO reserveData = dataRecords.getReserveDTO();
		Boolean isDateOrTimeChanged = reserveData.getIsDateOrTimeChanged();
		List<Integer> deletedAttendeesIdList = reserveData.getDeletedAttendeesIdList();
		List<Integer> deletedResourcesIdList = reserveData.getDeletedResourcesIdList();
		Reservation reserve = this.mapper.map(reserveData, Reservation.class);
		List<ReserveResourcesDTO> reserveResourcesList = dataRecords.getReserveResourcesDTO();
		List<ReserveAttendeesDTO> reserveAttendeesList = dataRecords.getReserveAttendeesDTO();
		boolean isEdit = false;
		Reservation oldRecord = null;
		List<ReserveAttendees> oldAttendesList = new ArrayList<ReserveAttendees>();
		List<String> oldAttendesEmails = new ArrayList<String>();
		if (reserve.getReserveId() == 0) {
			reserve.setDateCreated(currentDate);
			reserve.setTimeCreated(currentTime);
			isEdit = false;
		} else {
			isEdit = true;
			oldRecord = reservationSrv.getReservationById(reserve.getReserveId());
			oldAttendesList = reserveAttendeeService.getByRserveId(reserve.getReserveId());
			this.entityManager.detach(oldRecord);
			oldAttendesList.forEach(attendee -> {
				oldAttendesEmails.add(attendee.getEmail());
			});
			
		}
		List<String> attendesEmails = new ArrayList<>();
		List<String> approverEmails = new ArrayList<>();
		approverEmails.add("approve1.test@gmail.com");
		approverEmails.add("approve2.test@gmail.com");
		approverEmails.add("FMS@FMS.COM");
		
		boolean isAvailable = this.rmConfigService.checkAvailabilityOfRooms(reserve);

		if (isAvailable) {

			try {
				Reservation savedRecord = reservationSrv.saveOrUpdate(reserve);
				int reserveId = savedRecord.getReserveId();
				attendesEmails = this.saveReserveAttendees(reserveAttendeesList, reserveId);
				this.saveReserveResources(reserveResourcesList, savedRecord);
				this.deleteReserveResourcesAndAttendees(deletedResourcesIdList, deletedAttendeesIdList);
				
				// Regular booking and edit booking mail services

//				try {
//					notifyReq.createMailNotificationforBooking(oldRecord, savedRecord, oldAttendesEmails,
//							attendesEmails, null, isEdit, isDateOrTimeChanged, approverEmails);
//				} catch (Exception e) {
//					logger.error(e.getMessage());
//				} finally {
//					return new ResponseEntity<>(savedRecord, HttpStatus.OK);
//				}

			 return new ResponseEntity<>(savedRecord, HttpStatus.OK);
			} catch (Exception e) {
				String exceptionCause = CommonUtil.getExceptionCause(e);
				String stacktrace = CommonUtil.getStakeTrace(e);
				logger.error("Exception in ReservationController.saveReservation: "+stacktrace,e);
				return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(
					new ResponseUtil(String.valueOf(CommonConstant.UNABLE_TO_PROCESS_BOOKING), HttpStatus.PARTIAL_CONTENT.value()),
					HttpStatus.PARTIAL_CONTENT);
		}

	}

	// To get all dates of occurrence in recurrence
	@RequestMapping(value = "/reservation/getListOfOccurances", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getListOfOccurances(@RequestBody RecurrencePatternDTO recurrencePatternDTO) {

		try {
			List<LocalDate> totalDates = this.reservationSrv.getBookingDates(recurrencePatternDTO);
			return new ResponseEntity<>(totalDates, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.getListOfOccurances: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/reservation/getall", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> getAllReservations() {
		try {
			List<Reservation> reservationData = this.reservationSrv.getAllReservations();
			List<ReservationOutputDTO> resData = new ArrayList<ReservationOutputDTO>();
			resData = reservationData.stream().map(each -> this.mapper.map(each, ReservationOutputDTO.class))
					.collect(Collectors.toList());
			
			resData.forEach(res -> {
				res.getRmConfig().forEach(rmCfg ->{
					if (rmCfg.getRm().getDoc() != null && rmCfg.getRm().getDoc().getFileContent() != null) {
						res.setRmPhoto(CommonUtil.decompressBytes(rmCfg.getRm().getDoc().getFileContent()));
					}
				});
			});
			return new ResponseEntity<>(resData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.getAllReservations: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/reservation/getallIds", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> getAllReservationIds() {
		try {
			List<ReservationsIdsDTO> reservationIdsData = this.reservationSrv.getAllReservationIds();
			return new ResponseEntity<>(reservationIdsData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.getAllReservationIds: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/reservation/searchbyfilter", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> searchByfilter(@RequestBody ReservationSearchFilterDTO data) {
		try {
			List<ReservationOutputDTO> resData = new ArrayList<ReservationOutputDTO>();
			List<Reservation> reservData = reservationNativeQuerySrv.getAllReservationsBySearchFilter(data);
			if (reservData != null) {
				resData = reservData.stream().map(each -> this.mapper.map(each, ReservationOutputDTO.class))
						.collect(Collectors.toList());

				resData.forEach(res -> {
					res.getRmConfig().forEach(rmCfg -> {
						if (rmCfg.getRm().getDoc() != null && rmCfg.getRm().getDoc().getFileContent() != null) {
							res.setRmPhoto(CommonUtil.decompressBytes(rmCfg.getRm().getDoc().getFileContent()));
						}
					});
					if(res.getReserveAttendees().size() > 0) {
						res.getReserveAttendees().forEach(attendee -> {
							if (attendee.getIsVisitor().equals("Yes")) {
								if (!attendee.getVisitor().isEmpty() && attendee.getVisitor().get(0).getDoc() != null
										&& attendee.getVisitor().get(0).getDoc().getFileContent() != null) {
									attendee.setPhoto(
											CommonUtil.decompressBytes(attendee.getVisitor().get(0).getDoc().getFileContent()));
								}
							} else {
								if (!attendee.getEm().isEmpty() && attendee.getEm().get(0).getDoc() != null
										&& attendee.getEm().get(0).getDoc().getFileContent() != null) {
									attendee.setPhoto(
											CommonUtil.decompressBytes(attendee.getEm().get(0).getDoc().getFileContent()));
								}
							}
						});
					}
				});
				return new ResponseEntity<>(resData, HttpStatus.OK);
			} 
			else {
				return new ResponseEntity<>(resData, HttpStatus.OK);
			}
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.searchbyfilter: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/reservation/searchbyfilterPaginated", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> searchByfilterPaginated(@RequestBody ReservationSearchFilterDTO data) {
		try {
			Map<String, Object> reservData = reservationNativeQuerySrv.getAllReservationsBySearchFilterPaginated(data);
			List<Reservation> originalresData = (List<Reservation>) reservData.get("content");
			List<ReservationOutputDTO> finalresData = originalresData.stream()
				    .map(each -> {
				        ReservationOutputDTO resDTO = this.mapper.map(each, ReservationOutputDTO.class);
				        resDTO.getRmConfig().forEach(rmCfg -> {
				            if (rmCfg.getRm().getDoc() != null && rmCfg.getRm().getDoc().getFileContent() != null) {
				                resDTO.setRmPhoto(CommonUtil.decompressBytes(rmCfg.getRm().getDoc().getFileContent()));
				            }
				        });
				        if (resDTO.getReserveAttendees().size() > 0) {
				            resDTO.getReserveAttendees().forEach(attendee -> {
				                if (attendee.getIsVisitor().equals("Yes")) {
				                	if (!attendee.getVisitor().isEmpty() && attendee.getVisitor().get(0).getDoc() != null
				                            && attendee.getVisitor().get(0).getDoc().getFileContent() != null) {
				                        attendee.setPhoto(CommonUtil.decompressBytes(attendee.getVisitor().get(0).getDoc().getFileContent()));
				                    }
				                } else {
				                	if (!attendee.getEm().isEmpty() && attendee.getEm().get(0).getDoc() != null
				                            && attendee.getEm().get(0).getDoc().getFileContent() != null) {
				                        attendee.setPhoto(CommonUtil.decompressBytes(attendee.getEm().get(0).getDoc().getFileContent()));
				                    }
				                }
				            });
				        }
				        return resDTO;
				    }).collect(Collectors.toList());
			reservData.put("content", finalresData);
			return new ResponseEntity<>(reservData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.searchByfilterPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/reservation/getbystatus/{status}", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> getReqReservation(@PathVariable("status") String status) {
		try {
			List<Reservation> reservationData = this.reservationSrv.getAllReservationByStatus(status);
			List<ReservationOutputDTO> resData = new ArrayList<ReservationOutputDTO>();
			resData = reservationData.stream().map(each -> this.mapper.map(each, ReservationOutputDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(resData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.getReqReservation: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/reservation/getbystatusPaginated", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getReqReservationPaginated(@RequestBody ReservationSearchFilterDTO data) {
		try {
			PagedResponse<Reservation> reservationData = this.reservationSrv.getAllReservationByStatusPaginated(data);
			List<ReservationOutputDTO> resData = ((Collection<Reservation>) reservationData.getContent()).stream().map(each -> this.mapper.map(each, ReservationOutputDTO.class))
					.collect(Collectors.toList());
			GenericPagedResponse<ReservationOutputDTO> response = new GenericPagedResponse<ReservationOutputDTO>(resData, reservationData.getCurrentPage(),
					reservationData.getTotalPages(), reservationData.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.getReqReservationPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/reservation/getallreqcheckin", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> getReqCheckinReservation() {		
		try {
			List<Reservation> reservationData = this.reservationSrv.getReqCheckin();
			List<ReservationOutputDTO> resData = new ArrayList<ReservationOutputDTO>();
			resData = reservationData.stream().map(each -> this.mapper.map(each, ReservationOutputDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(resData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.getReqCheckinReservation: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/reservation/getallreqcheckinPaginated", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getReqCheckinReservationPaginated(@RequestBody FilterDTOCopy filterDto) {		
		try {
			Map<String, Object> reservationData = this.reservationSrv.getReqCheckinPaginated(filterDto);
			List<ReservationOutputDTO> resData = ((Collection<Reservation>) reservationData.get("content")).stream().map(each -> this.mapper.map(each, ReservationOutputDTO.class))
					.collect(Collectors.toList());
			reservationData.put("content", resData);
			return new ResponseEntity<>(reservationData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.getReqCheckinReservationPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/reservation/deletereserveattendee/{id}", method = RequestMethod.DELETE, produces = "application/json")
	private ResponseEntity<Object> deleteMessageById(@PathVariable("id") int id) {
		try {
			this.reserveAttendeeService.deleteAttendeById(id);
			return new ResponseEntity<>(new ResponseUtil("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.deleteMessageById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	// Save Recurrence Booking
	@RequestMapping(value = "/recurrenceReservation/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveRecurrenceReservation(@RequestBody RecurrenceBookingInputDTO dataRecords) {

		try {
			java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			java.sql.Time currentTime = new java.sql.Time(Calendar.getInstance().getTime().getTime());
			ReserveDTO reserveData = dataRecords.getReserveDTO();
			List<ReserveAttendeesDTO> reserveAttendeesList = dataRecords.getReserveAttendeesDTO();
			List<ReserveResourcesDTO> reserveResourcesList = dataRecords.getReserveResourcesDTO();
			List<Integer> deletedAttendeesIdList = reserveData.getDeletedAttendeesIdList();
			List<Integer> deletedResourcesIdList = reserveData.getDeletedResourcesIdList();
			RecurrencePatternDTO recurrencePatternDTO = dataRecords.getRecurrencePatternDTO();
			List<LocalDate> totalDates = this.reservationSrv.getBookingDates(recurrencePatternDTO);
			List<Date> ListNotAvalDates = new ArrayList<Date>();
			List<Date> ListAvalDates = new ArrayList<Date>();
			List<String> attendesEmails = new ArrayList<>();
			Reservation recordToMail = new Reservation();
			List<String> approverEmails = new ArrayList<>();
			approverEmails.add("approve1.test@gmail.com");
			approverEmails.add("approve2.test@gmail.com");
			approverEmails.add("FMS@FMS.COM");
			int parentId = 0;
			for (LocalDate eachDate : totalDates) {
				Reservation reserveRec = this.mapper.map(reserveData, Reservation.class);
				java.sql.Date formatedDateStart = java.sql.Date.valueOf( eachDate );
				reserveRec.setDateStart(formatedDateStart);
				reserveRec.setReserveId(0);
				reserveRec.setDateCreated(currentDate);
				reserveRec.setTimeCreated(currentTime);
				boolean isAvailable = this.rmConfigService.checkAvailabilityOfRooms(reserveRec);
				if (isAvailable && parentId == 0) {
					ListAvalDates.add(formatedDateStart);
					Reservation parentRecord = reservationSrv.saveOrUpdate(reserveRec);
					parentId = parentRecord.getReserveId();
					parentRecord.setParentId(parentId);
					reservationSrv.saveOrUpdate(parentRecord);
					recordToMail = parentRecord;
					attendesEmails = this.saveReserveAttendees(reserveAttendeesList, parentId);
					this.saveReserveResources(reserveResourcesList, parentRecord);
					
				} else if (isAvailable && parentId != 0) {
					ListAvalDates.add(formatedDateStart);
					reserveRec.setParentId(parentId);
					Reservation savedRecord = reservationSrv.saveOrUpdate(reserveRec);
					int reserveId = savedRecord.getReserveId();
					attendesEmails = this.saveReserveAttendees(reserveAttendeesList, reserveId);
					this.saveReserveResources(reserveResourcesList, savedRecord);
					
				} else {
					ListNotAvalDates.add(formatedDateStart);
				}
			}
			// Recurrence Booking mail services
//			String approvedId = reservationSrv.getEnumIdByStatus("Approved");
//			String waitingForApprovalId = reservationSrv.getEnumIdByStatus("Waiting for Approval");
//			if (recordToMail.getStatus() == approvedId) {
//				notifyReq.notifyReservationSuccess(recordToMail, attendesEmails, ListAvalDates, null);
//			} else if (recordToMail.getStatus() == waitingForApprovalId) {
//				notifyReq.notifyApprovalReminder(recordToMail, approverEmails, ListAvalDates, "");
//				notifyReq.notifyRequestorWaitingForApproval(recordToMail, ListAvalDates);
//			}

			return new ResponseEntity<>(ListNotAvalDates, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.saveRecurrenceReservation: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}

	}

	//check_in reminder scheduler which runs for every 5 mins
	@Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 1000)
	public void autoCheckInReminderScheduler() {
		AppParams apParamData = this.apParamService.getAppParamsByParamId("is_check_in_required");
		String isCheckInReq = apParamData != null ? apParamData.getParamValue() : null;
		if (isCheckInReq != null && isCheckInReq.equalsIgnoreCase("yes")) {
			AppParams apParamCheckInterval = this.apParamService.getAppParamsByParamId("meeting_check_in_time_interval");
			String checkInTimeInterval = apParamCheckInterval != null ? apParamCheckInterval.getParamValue() : null;
			// Need to notify user for check In before 15 mins
			List<Reservation> reservations = this.reservationSrv.getReservationDetailsForActions("checkReminder",
					checkInTimeInterval);
			if (reservations != null) {
				reservations.forEach(element -> {
					if (element.getCheckInNotifyCount()!=null && element.getCheckInNotifyCount() == 0) {
						
						try {
							List<ReserveAttendees> reserveAttendees = reserveAttendeeService
									.getByRserveId(element.getReserveId());
							List<String> attendesEmails = new ArrayList<>();
							reserveAttendees.forEach(rec -> {
								attendesEmails.add(rec.getEmail());
							});
//							notifyReq.notifyCheckInReminder(element,attendesEmails);
						}catch (Exception e) {
							String stacktrace = CommonUtil.getStakeTrace(e);
							logger.error("Exception in ReservationController.autoCheckInReminderScheduler: "+stacktrace,e);
						}

					}
				});
			}
		}
	}
	
	// Auto cancel scheduler which runs for every 5 mins
	@Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 1000)
	public void autoCancelScheduler() {
		AppParams apParamData = this.apParamService.getAppParamsByParamId("is_check_in_required");
		String isCheckInReq = apParamData != null ? apParamData.getParamValue() : null;
		if (isCheckInReq != null && isCheckInReq.equalsIgnoreCase("yes")) {
			AppParams apParam = this.apParamService.getAppParamsByParamId("meeting_cancel_time_interval");
			String meet_cancel_interval = apParam != null ? apParam.getParamValue() : null;
			if (apParam != null) {
				try {

					List<Reservation> reservationsList = this.reservationSrv.getReservationDetailsForActions("cancel",
							meet_cancel_interval);
					if (reservationsList != null) {
						reservationsList.forEach(element -> {
							// element.setCancelledBy(user);
							java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
							java.sql.Time currentTime = new java.sql.Time(Calendar.getInstance().getTime().getTime());
							element.setCancelledReason(
									"User does not checked-in within a specified time after the meeting starts.");
							element.setDateCancelled(currentDate);
							element.setTimeCancelled(currentTime);
							element.setStatus("Cancelled");
							reservationSrv.saveOrUpdate(element);
							// Need to notify user about booking cancellation
							List<ReserveAttendees> reserveAttendees = reserveAttendeeService
									.getByRserveId(element.getReserveId());
							List<String> attendesEmails = new ArrayList<>();
							reserveAttendees.forEach(rec -> {
								attendesEmails.add(rec.getEmail());
							});
							//notifyReq.notifyCancelBooking(element, attendesEmails,null);

						});
					}
				
				}catch (Exception e) {
					String stacktrace = CommonUtil.getStakeTrace(e);
					logger.error("Exception in ReservationController.autoCancelScheduler: "+stacktrace,e);
				}
			}
		}
	}
	
	// Auto check in scheduler which runs for every 5 mins
	@Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 1000)
	public void autoCheckInScheduler() {
		AppParams apParamData = this.apParamService.getAppParamsByParamId("is_check_in_required");
		String isCheckInReq = apParamData != null ? apParamData.getParamValue() : null;
		if (isCheckInReq != null && isCheckInReq.equalsIgnoreCase("no")) {
			try {

				AppParams apParamDataCheckIn = this.apParamService.getAppParamsByParamId("meeting_check_in_time_interval");
				String checkInTimeInterval = apParamDataCheckIn != null ? apParamDataCheckIn.getParamValue() : null;
				List<Reservation> reservationsList = this.reservationSrv.getReservationDetailsForActions("autoCheckin",
						checkInTimeInterval);
				if (reservationsList != null) {
					reservationsList.forEach(element -> {
						element.setStatus("Check In");
						reservationSrv.saveOrUpdate(element);
					});
				}
			
			}catch (Exception e) {
				String stacktrace = CommonUtil.getStakeTrace(e);
				logger.error("Exception in ReservationController.autoCheckInScheduler: "+stacktrace,e);
			}
		}
	}

	// Auto complete scheduler which runs for every 5 mins
	@Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 1000)
	public void autoCompleteBookingScheduler() {
		String interval = "0";
		try {
			List<Reservation> reservationsList = this.reservationSrv.getReservationDetailsForActions("complete",interval);
			if (reservationsList != null) {
				reservationsList.forEach(element -> {
					element.setStatus("Completed");
					reservationSrv.saveOrUpdate(element);
				});
			}
		}catch (Exception e) {
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.autoCompleteBookingScheduler: "+stacktrace,e);
		}
	}

	// Get Reserve Resources Quantity in use
	@RequestMapping(value = "/reserveRs/getQuanitiyInUse", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getReserveRsQtnInUse(@RequestBody ReserveRsAvailableCheckDTO data) {
		try {
			int quanitiyInUse = reserveResourcesService.getReserveResourcesQuanitiyInUse(data);
			return new ResponseEntity<>(quanitiyInUse, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.getReserveRsQtnInUse: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	//Update All Recurrence Bookings
	@RequestMapping(value = "/recurrenceReservation/updateAll", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> updateRecurrenceReservation(@RequestBody List<ReservationInputDTO> reservationInputDTOList) {

		try {
			List<Date> ListNotAvalDates = new ArrayList<Date>();
			for(ReservationInputDTO dataRecords:reservationInputDTOList){
				ResponseEntity<Object> result = this.saveReservation(dataRecords);
				if(result.getStatusCode().equals(HttpStatus.PARTIAL_CONTENT)) {
						ListNotAvalDates.add(dataRecords.getReserveDTO().getDateStart());
				}
			};
			
			return new ResponseEntity<>(ListNotAvalDates, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.updateRecurrenceReservation: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	private List<String> saveReserveAttendees(List<ReserveAttendeesDTO> reserveAttendeesList, int reserveId) {
		try {
			List<String> attendesEmails = new ArrayList<>();
			if (!reserveAttendeesList.isEmpty()) {
				reserveAttendeesList.stream().forEach(element -> {
					ReserveAttendees record = this.mapper.map(element, ReserveAttendees.class);
					record.setReserveId(reserveId);
					reserveAttendeeService.saveOrUpdate(record);
					attendesEmails.add(element.getEmail());
				});
			}
			return attendesEmails;
		}catch (Exception e) {
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.saveReserveAttendees: "+stacktrace,e);
			return null;
		}
	}

	private void saveReserveResources(List<ReserveResourcesDTO> reserveResourcesList, Reservation savedRecord) {
		try {
			if (!reserveResourcesList.isEmpty()) {
				reserveResourcesList.stream().forEach(element -> {
					String type = element.getType();
					boolean checkAval = true;
					if (type != null && type.equalsIgnoreCase("Limited")) {
						ReserveRsAvailableCheckDTO reserveRsAvailableCheckDTO = new ReserveRsAvailableCheckDTO();
						int reqQuantity = element.getQty();
						int reserveRsId = element.getReserveRsId();
						reserveRsAvailableCheckDTO.setReserveRsId(reserveRsId);
						reserveRsAvailableCheckDTO.setResourcesId(element.getResourcesId());
						reserveRsAvailableCheckDTO.setDate(savedRecord.getDateStart());
						reserveRsAvailableCheckDTO.setTimeStart(savedRecord.getTimeStart());
						reserveRsAvailableCheckDTO.setTimeEnd(savedRecord.getTimeEnd());
						checkAval = reserveResourcesService.checkIsResourceAvailable(reserveRsAvailableCheckDTO,
								reqQuantity);
					}

					String status = checkAval ? "Available" : "Not Available";
					ReserveResources record = this.mapper.map(element, ReserveResources.class);
					record.setReserveId(savedRecord.getReserveId());
					record.setStatus(status);
					reserveResourcesService.saveOrUpdate(record);
				});
			}
		}catch (Exception e) {
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.saveReserveResources: "+stacktrace,e);
		}
	}

	private void deleteReserveResourcesAndAttendees(List<Integer> deletedResourcesIdList,
			List<Integer> deletedAttendeesIdList) {
		if (deletedAttendeesIdList != null) {
			if (deletedAttendeesIdList.size() > 0) {
				deletedAttendeesIdList.forEach(id -> {
					reserveAttendeeService.deleteAttendeById(id);
				});
			}
		}

		if (deletedResourcesIdList != null) {
			if (deletedResourcesIdList.size() > 0) {
				deletedResourcesIdList.forEach(id -> {
					reserveResourcesService.deleteReserveRsById(id);
				});
			}
		}
	}

	@RequestMapping(value = "/reservation/countTotalByMonth", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> countTotalReqByMonth(@RequestBody BookingReportsFilterDto filterData) {
		try {
			List<Object[]> obj = this.reservationNativeQuerySrv.getCountByMonth(filterData);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.countTotalReqByMonth: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/reservation/countTotalReservationByMonth", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> countTotalREservationByMonth(@RequestBody BookingReportsFilterDto filterData) {
		try {
			List<Reservation> listOfReservations = this.reservationNativeQuerySrv.getAllReservationsByMonth(filterData);
			return new ResponseEntity<>(listOfReservations, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReservationController.countTotalREservationByMonth: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
