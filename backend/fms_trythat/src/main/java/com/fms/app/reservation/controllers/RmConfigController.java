package com.fms.app.reservation.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fms.app.reservation.models.ReserveFilter;
import com.fms.app.reservation.models.RmConfig;
import com.fms.app.reservation.models.RmResources;
import com.fms.app.reservation.models.dto.BookingFilterDTO;
import com.fms.app.reservation.models.dto.BookingOutPutDTO;
import com.fms.app.reservation.models.dto.RecurrenceFilterDTO;
import com.fms.app.reservation.models.dto.RecurrenceOutPutDTO;
import com.fms.app.reservation.models.dto.ReservationFilterDTO;
import com.fms.app.reservation.models.dto.RmConfigFilterInputDTO;
import com.fms.app.reservation.models.dto.RmConfigOutputDTO;
import com.fms.app.reservation.services.ReserveFilterService;
import com.fms.app.reservation.services.RmConfigService;
import com.fms.app.reservation.services.RmResourcesService;
import com.fms.app.spaceManagement.models.Rm;
import com.fms.app.spaceManagement.models.dto.RMDTO;
import com.fms.app.spaceManagement.services.RmService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericPagedResponse;
import com.fms.paginator.models.PagedResponse;

@RestController
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class RmConfigController {
	private static final Logger logger = LogManager.getLogger(RmConfigController.class);

	@Autowired
	RmConfigService rmConfigService;

	@Autowired
	RmResourcesService rmResourcesSrv;

	@Autowired
	ModelMapper mapper;
	@Autowired
	RmService rmService;

	@Autowired
	ReserveFilterService reserveFilterService;

	@RequestMapping(value = "/rmConfig/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveRmConfig(@RequestBody RmConfig dataRecord) {
		java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		java.sql.Time currentTime = new java.sql.Time(Calendar.getInstance().getTime().getTime());

		dataRecord.setDateCreated(currentDate);
		dataRecord.setTimeCreated(currentTime);
		try {
			RmConfig savedData = rmConfigService.saveOrUpdate(dataRecord);
			return new ResponseEntity<>(savedData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmConfigController.saveRmConfig: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/rmConfig/getAllFilterData", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getFilterPanelData() {
		try {
			List<Map<String, Object>> blData = this.rmConfigService.getBlFilterData();
			List<Map<String, Object>> flData = this.rmConfigService.getFlFilterData();
			List<Map<String, Object>> rmData = this.rmConfigService.getRmFilterData();
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("blList", blData);
			obj.put("flList", flData);
			obj.put("rmList", rmData);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmConfigController.getFilterPanelData: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/rmConfig/getRmConfigList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAvailableRoomsForRegular(@RequestBody BookingFilterDTO filterData) {
		try {
			List<BookingOutPutDTO> bookingOutPutDTO = new ArrayList<BookingOutPutDTO>();
			List<RmConfig> allRooms = this.rmConfigService.getFilteredRooms(filterData);
			for (RmConfig room : allRooms) {
					boolean isAvailable = this.rmConfigService.checkRoomAvailableforEachDateandTime(room, filterData.getDate(),
							filterData.getFromTime(), filterData.getToTime());
					if(isAvailable) {
						bookingOutPutDTO.add(this.mapper.map(room, BookingOutPutDTO.class));
					}		
				}
			bookingOutPutDTO.forEach(rm -> {
				if (rm.getRmDocFileContent() != null) {
					rm.setRmPhoto(CommonUtil.decompressBytes(rm.getRmDocFileContent()));
				}
			});
			return new ResponseEntity<>(bookingOutPutDTO, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmConfigController.getAvailableRoomsForRegular: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/rmResources/getAllRmResources", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getRmResourcesData() {
		try {
			List<RmResources> rmResourcesData = this.rmResourcesSrv.getAll();
			return new ResponseEntity<>(rmResourcesData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmConfigController.getRmResourcesData: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/rmConfig/getById", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getRmConfigById(@RequestBody RmConfigFilterInputDTO input) {
		try {
			List<RmConfig> rmConfig = this.rmConfigService.getRmConfigById(input.getBlId(), input.getFlId(),
					input.getRmId());
			if (rmConfig != null) {
				List<RmConfigOutputDTO> rmConfigOutput = rmConfig.stream()
						.map(element -> this.mapper.map(element, RmConfigOutputDTO.class)).collect(Collectors.toList());

				return new ResponseEntity<>(rmConfigOutput, HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmConfigController.getRmConfigById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/rmConfig/delete/{configId}", method = RequestMethod.DELETE, produces = "application/json")
	private ResponseEntity<Object> deleteRmConfigById(@PathVariable("configId") int configId) {
		try {
			this.rmConfigService.deleteRmCById(configId);
			return new ResponseEntity<>(new ResponseUtil("Record Deleted", HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmConfigController.deleteRmConfigById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/rmConfig/checkRmConfig", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> checkRmArrangementExists(@RequestBody RmConfigFilterInputDTO input) {
		try {
			boolean isExist = false;
			isExist = this.rmConfigService.checkRmArrangementExists(input.getBlId(), input.getFlId(), input.getRmId(),
					input.getArrangementId());
			return new ResponseEntity<>(new ResponseUtil(String.valueOf(isExist), 200), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmConfigController.checkRmArrangementExists: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	// To get all rooms which are reservable.
	@RequestMapping(value = "/rmConfig/getRoomsList/{isReservable}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getRMList(@PathVariable("isReservable") int isReservable) {
		try {
			List<Rm> roomsList = this.rmService.getAllRmByIsReservable(isReservable);
			List<RMDTO> rmOutPut = roomsList.stream().map(element -> this.mapper.map(element, RMDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(rmOutPut, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmConfigController.getRMList: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rmConfig/getReservRmPaginated", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getReservRmPaginated(@RequestBody FilterDTOCopy filterDto) {
		try {
			PagedResponse<Rm> roomsList = this.rmService.getRmConfigRoomsPaginated(filterDto);
			List<RMDTO> finalResult = ((Collection<Rm>) roomsList.getContent()).stream().map(element -> this.mapper.map(element, RMDTO.class))
					.collect(Collectors.toList());
			GenericPagedResponse<RMDTO> response = new GenericPagedResponse<RMDTO>(finalResult, roomsList.getCurrentPage(),
					roomsList.getTotalPages(), roomsList.getTotalElements());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmConfigController.getReservRmPaginated: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	//To Filter Available Rooms For Recurrence Booking
	@RequestMapping(value = "/rmConfig/recurrence", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getAvailableRoomsForRecurrence(
			@RequestBody RecurrenceFilterDTO recurrenceFilterData) {
		try {
			BookingFilterDTO filterData = recurrenceFilterData.getBookingFilterDTO();
			List<LocalDate> recurrenceDates = recurrenceFilterData.getRecurrenceDates();
			List<RmConfig> allRooms = this.rmConfigService.getFilteredRooms(filterData);
			
			List<RecurrenceOutPutDTO> ListRecurrenceOutPutDTO = new ArrayList<RecurrenceOutPutDTO>();	
			for (RmConfig room : allRooms) {
				RecurrenceOutPutDTO recurrenceOutPutDTO = new RecurrenceOutPutDTO();
				List<Date> ListAvalDates = new ArrayList<Date>();
				List<Date> ListNotAvalDates = new ArrayList<Date>();
				for (LocalDate date : recurrenceDates) {
					java.sql.Date formatedDate = java.sql.Date.valueOf( date );
					boolean isAvailable = this.rmConfigService.checkRoomAvailableforEachDateandTime(room, formatedDate,
							filterData.getFromTime(), filterData.getToTime());

					if (isAvailable) {
						ListAvalDates.add(formatedDate);
					} else {
						ListNotAvalDates.add(formatedDate);
					}

				}
				BookingOutPutDTO bookingOutPutDTO = this.mapper.map(room,BookingOutPutDTO.class);
				bookingOutPutDTO.setConflicts(ListNotAvalDates.size());
				if (bookingOutPutDTO.getRmDocFileContent() != null) {
					bookingOutPutDTO.setRmPhoto(CommonUtil.decompressBytes(bookingOutPutDTO.getRmDocFileContent()));
					
				}
				if (ListAvalDates.size() > ListNotAvalDates.size()) {
					recurrenceOutPutDTO.setBookingOutPutDTO(bookingOutPutDTO);
					recurrenceOutPutDTO.setListAvalDates(ListAvalDates);
					recurrenceOutPutDTO.setListNotAvalDates(ListNotAvalDates);
					ListRecurrenceOutPutDTO.add(recurrenceOutPutDTO);
				}

			}

			return new ResponseEntity<>(ListRecurrenceOutPutDTO, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmConfigController.getAvailableRoomsForRecurrence: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rmConfig/getallByIds", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getRmConfigById(@RequestBody List<RmConfigFilterInputDTO> input) {
		try {
			List<List<RmConfigOutputDTO>> resultData = new ArrayList<>();
			input.forEach(eachInput -> {
				List<RmConfig> rmConfig = this.rmConfigService.getRmConfigById(eachInput.getBlId(), eachInput.getFlId(),
						eachInput.getRmId());
				
				if (rmConfig != null) {
					List<RmConfigOutputDTO> rmConfigOutput = rmConfig.stream()
							.map(element -> this.mapper.map(element, RmConfigOutputDTO.class)).collect(Collectors.toList());
					resultData.add(rmConfigOutput);
					
				}
			});
			return new ResponseEntity<>(resultData, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmConfigController.getRmConfigById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/rmConfig/getallbyblandfl", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> getRmConfigByBlAndFlId(@RequestBody RmConfigFilterInputDTO input) {
		try {
			List<RmConfigOutputDTO> rmConfigOutput = new ArrayList<>();
			List<RmConfig> rmConfig = this.rmConfigService.getRmConfigByBlAndFlId(input.getBlId(), input.getFlId());
			if (rmConfig != null) {
				rmConfigOutput = rmConfig.stream()
						.map(element -> this.mapper.map(element, RmConfigOutputDTO.class)).collect(Collectors.toList());
			}
		return new ResponseEntity<>(rmConfigOutput, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmConfigController.getRmConfigByBlAndFlId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/rmConfig/setDefaultFilter", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveDefaultFilter(@RequestBody ReservationFilterDTO reserveFilterDto) {

		try {
			ReserveFilter reserveFilter = this.mapper.map(reserveFilterDto, ReserveFilter.class);

			List<ReserveFilter> reserveFilterData = reserveFilterService.getReserveFilterByUserId();
			ReserveFilter dataRecord = new ReserveFilter();

			List<ReserveFilter> recordsWithDefault = reserveFilterData.stream().filter(p -> p.getIsDefault() == 1)
					.collect(Collectors.toList());
			ReserveFilter defaultFilterRecord;
			ReserveFilter newValue;
			int count = reserveFilterData.size();

			if (reserveFilterData.size() < 5) {
				if (reserveFilterData.size() == 0) {

					reserveFilter.setSeqId(1);
					dataRecord = reserveFilterService.saveOrUpdate(reserveFilter);
				} else if (!reserveFilterService.isDuplicate(reserveFilterData, reserveFilter)
						&& reserveFilter.getIsDefault() != 1) {
					count = reserveFilterData.size() + 1;
					reserveFilter.setSeqId(count);
					dataRecord = reserveFilterService.saveOrUpdate(reserveFilter);
				} else {
					defaultFilterRecord = recordsWithDefault.get(0);
					newValue = reserveFilterService.SwapRecords(defaultFilterRecord, reserveFilter);
					dataRecord = reserveFilterService.saveOrUpdate(newValue);
				}
			} else if (!reserveFilterService.isDuplicate(reserveFilterData, reserveFilter)
					&& reserveFilter.getIsDefault() != 1) {
				List<ReserveFilter> recordsWithoutDefault = reserveFilterData.stream().filter(p -> p.getIsDefault() != 1)
						.collect(Collectors.toList());

				for (int i = 0; i < recordsWithoutDefault.size(); i++) {
					if (recordsWithoutDefault.get(i).getIsDefault() == 0 && i < recordsWithoutDefault.size() - 1) {
						newValue = reserveFilterService.SwapRecords(recordsWithoutDefault.get(i),
								recordsWithoutDefault.get(i + 1));
						dataRecord = reserveFilterService.saveOrUpdate(newValue);
					} else {
						newValue = reserveFilterService.SwapRecords(recordsWithoutDefault.get(i), reserveFilter);
						dataRecord = reserveFilterService.saveOrUpdate(newValue);
					}
				}
			} else {
				defaultFilterRecord = recordsWithDefault.get(0);
				newValue = reserveFilterService.SwapRecords(defaultFilterRecord, reserveFilter);
				dataRecord = reserveFilterService.saveOrUpdate(newValue);
			}
			return new ResponseEntity<>(reserveFilter, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmConfigController.saveDefaultFilter: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/rmConfig/getReserveFilter", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> getResourceFilter() {
		try {
			List<ReserveFilter> reserveFilterData = reserveFilterService.getReserveFilterByUserId();
			List<ReservationFilterDTO> reserveFilterRecords = reserveFilterData.stream()
					.map(p -> this.mapper.map(p, ReservationFilterDTO.class)).collect(Collectors.toList());

			return new ResponseEntity<>(reserveFilterRecords, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RmConfigController.getResourceFilter: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

}
