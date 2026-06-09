package com.fms.app.reservation.services;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fms.app.reservation.models.Reservation;
import com.fms.app.reservation.models.RmConfig;
import com.fms.app.reservation.models.RmResources;
import com.fms.app.reservation.models.dto.BookingFilterDTO;
import com.fms.app.reservation.repository.RmConfigRepository;
import com.fms.app.security.AuthorizeUserInfo;

@Service
@CacheConfig(cacheNames = "RmConfigService_Ehcache_Cache_Config")
public class RmConfigService {

	@Autowired
	RmConfigRepository repository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	RmResourcesService rmResourcesSrv;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	@Autowired
	ReservationService reservationSrv;

	@CacheEvict(allEntries = true)
	public RmConfig saveOrUpdate(RmConfig rmConfig) {
		return repository.save(rmConfig);
	}

	@Cacheable(value = "RmConfigService_Ehcache_Cache_Config",key = "{#blId,#flId,#rmId}")
	public List<RmConfig> getRmConfigById(int blId, int flId, int rmId) {
		String query = "Select * from rm_config where rm_id =" + rmId + "" + " and bl_id = " + blId + ""
				+ "and fl_id = " + flId + "";
		Query nativeQuery = this.entityManager.createNativeQuery(query, RmConfig.class);
		@SuppressWarnings("unchecked")
		List<RmConfig> dataRecords = nativeQuery.getResultList();
		if (!dataRecords.isEmpty()) {
			return dataRecords;
		}
		return null;
	}

	@CacheEvict(allEntries = true)
	public void deleteRmCById(int configId) {
		repository.deleteById(configId);
	}

	@Cacheable(value = "RmConfigService_Ehcache_Cache_Config" ,key="{#p0}")
	public List<Map<String, Object>> getBlFilterData() {
		List<Map<String, Object>> blData = new ArrayList<Map<String, Object>>();
		String query = "select distinct rm_config.bl_id, bl.bl_name,bl.bl_code from rm_config LEFT JOIN bl ON rm_config.bl_id = bl.bl_id";
		Query nativeQuery = this.entityManager.createNativeQuery(query);

		@SuppressWarnings("unchecked")
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			blData.add(converObjectTOBlData(Arrays.asList(x)));
		});
		return blData;

	}

	@Cacheable(value = "RmConfigService_Ehcache_Cache_Config" ,key="{#p0}")
	public List<Map<String, Object>> getFlFilterData() {
		List<Map<String, Object>> flData = new ArrayList<Map<String, Object>>();
		String query = "select distinct rm_config.fl_id,fl.fl_name,fl.fl_code,rm_config.bl_id,bl.bl_code,fl.svg_name from rm_config LEFT JOIN fl ON rm_config.fl_id = fl.fl_id and rm_config.bl_id = fl.bl_id left join bl on bl.bl_id = fl.bl_id";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			flData.add(converObjectTOFlData(Arrays.asList(x)));
		});
		return flData;

	}

	@Cacheable(value = "RmConfigService_Ehcache_Cache_Config" ,key="{#p0}")
	public List<Map<String, Object>> getRmFilterData() {
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String query = "select distinct rm_config.rm_id,rm.rm_code,rm.rm_name,rm_config.bl_id,bl.bl_code,rm_config.fl_id,fl.fl_code from rm_config LEFT JOIN rm ON rm_config.rm_id = rm.rm_id "+
				" and rm_config.fl_id = rm.fl_id and rm_config.bl_id = rm.bl_id left join bl on bl.bl_id=rm_config.bl_id left join fl on fl.fl_id=rm_config.fl_id and fl.bl_id=rm_config.bl_id";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTORmData(Arrays.asList(x)));
		});
		return rmData;

	}

	public List<RmConfig> getAll() {

		List<RmConfig> rmConfig = new ArrayList<RmConfig>();
		repository.findAll().forEach(rmConfig1 -> rmConfig.add(rmConfig1));
		return rmConfig;
	}

	
	private Map<String, Object> converObjectTOBlData(List<Object> data) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		IntStream.range(0, len).forEach(index -> {
			if (index == 0) {
				convertDTO.put("blId", data.get(index));
			} else if(index == 1) {
				convertDTO.put("blName", data.get(index));
			} else if(index==2) {
				convertDTO.put("blCode", data.get(index));
			}
		});
		return convertDTO;
	}

	private Map<String, Object> converObjectTOFlData(List<Object> data) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		IntStream.range(0, len).forEach(index -> {
			if (index == 0) {
				convertDTO.put("flId", data.get(index));
			} else if (index == 1) {
				convertDTO.put("flName", data.get(index));
			} else if(index ==2) {
				convertDTO.put("flCode", data.get(index));
			}else if (index ==3){
				convertDTO.put("blId", data.get(index));
			}else if (index ==4){
				convertDTO.put("blCode", data.get(index));
			}else if (index ==5){
				convertDTO.put("svgName", data.get(index));
			}
		});
		return convertDTO;
	}

	private Map<String, Object> converObjectTORmData(List<Object> data) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		IntStream.range(0, len).forEach(index -> {
			if (index == 0) {
				convertDTO.put("rmId", data.get(index));
			} else if (index == 1) {
				convertDTO.put("rmCode", data.get(index));
			} else if (index == 2) {
				convertDTO.put("rmName", data.get(index));
			} else if(index == 3){
				convertDTO.put("blId", data.get(index));
			}else if(index == 4){
				convertDTO.put("blCode", data.get(index));
			}else if(index == 5){
				convertDTO.put("flId", data.get(index));
			}else if(index == 6){
				convertDTO.put("flCode", data.get(index));
			}
		});
		return convertDTO;
	}

//To check the availability of room in reserve table.
	public boolean checkAvailabilityOfRooms(Reservation reserve) {
		String blId = convertToIdRestriction("bl_id",reserve.getBlId());
		String flId = convertToIdRestriction("fl_id", reserve.getFlId());
		String rmId = convertToIdRestriction("rm_id", reserve.getRmId());
		String reserveId = convertToIdNotRestriction("reserve_id",reserve.getReserveId());
		String startTime = "'" + new SimpleDateFormat("HH:mm:ss.S").format(reserve.getTimeStart()) + "'";
		String endTime = "'" + new SimpleDateFormat("HH:mm:ss.S").format(reserve.getTimeEnd()) + "'";
		String startDate = "'" + new SimpleDateFormat("yyyy-MM-dd").format(reserve.getDateStart()) + "'";
		String query = "Select case when not  exists ( select * from reserve where date_start = convert(date," + startDate + ") "
				+ blId + flId + rmId + reserveId +" and reserve.status not in('Rejected','Cancelled','Completed')"
				+ " and  not(" + " convert(time, "+startTime+") >= convert(time,time_end) or ( convert(time, " + startTime
				+ ") <= convert(time,time_start) and convert(time, " + endTime + ") <= convert(time,time_start)))) then 'true' else 'false' end";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		boolean isAvalInReserve = Boolean.parseBoolean((String) nativeQuery.getSingleResult());
		return isAvalInReserve;
	}

	public boolean checkAvailabilityOfRoomsByResources(RmConfig rmConfig, Collection<Integer> resourcesIds) {

		List<RmResources> rmResourcesList = this.rmResourcesSrv.getAssignResources(
				rmConfig.getBlId(), rmConfig.getFlId(), rmConfig.getRmId());

		boolean isAvailable = false;

		List<Integer> allMatchedIds = new ArrayList<Integer>();
		List<Integer> allResourcesIds = new ArrayList<Integer>();

		rmResourcesList.forEach(element -> {
			allResourcesIds.add(element.getResourcesId());
		});

		for (int id : resourcesIds) {
			if (allResourcesIds.contains(id)) {
				allMatchedIds.add(id);
				if (allMatchedIds.size() == resourcesIds.size()) {
					isAvailable = true;
				}
			} else {
				isAvailable = false;
			}

		}
		return isAvailable;
	}

	public String convertToRestriction(String field, String value) {
		return value != null && value.length() >= 1 ? " and " + field + " = " + "'" + value + "'  " : " and 1=1";
	}
	
	public String convertToIdNotRestriction(String field, Integer value) {
		if(value != null) {
			return value!=0 ? " and " + field + " != " + value : " and 1=1";
		}else {
			return " and 1=1";
		}
		
	}
	public String convertToIdRestriction(String field, Integer value) {
		if(value != null) {
			return value!=0 ? " and " + field + " = " + value : " and 1=1";
		}else {
			return " and 1=1";
		}
		
	}

	public boolean checkRmArrangementExists(int blId, int flId, int rmId, int arrangementId) {
		
		return this.repository.existsByBlIdAndFlIdAndRmIdAndArrangementId(blId, flId, rmId, arrangementId);
	}
	
	//To Get all rmConfig with the bl,fl,rm,arrangementType and capacity Filters.
	public List<RmConfig> getFilteredRooms(BookingFilterDTO filterData) {
		
		String blId = convertToIdRestriction("bl_id",filterData.getBlId());
		Collection<Integer> resourcesIds = filterData.getResourceIdList();
		int capacity = filterData.getCapacity();
		String flRestriction = convertToIdRestriction("fl_id", filterData.getFlId());
		String rmRestriction = convertToIdRestriction("rm_id", filterData.getRmId());
		String arrangementTypeRestriction = convertToIdRestriction("arrangement_id", filterData.getArrangementId());
		String query1 = "Select * from rm_config where max_capacity >= "+capacity+blId+" and is_reservable = '"+"Yes"+"' ";
		String rmIsReservable = " and not exists ( select * from rm where rm.bl_id = rm_config.bl_id and rm.fl_id = rm_config.fl_id "
				+ "and rm.rm_id = rm_config.rm_id and rm.is_hotelable = '"+"Yes"+"' )";
		String query = query1 + flRestriction + rmRestriction + arrangementTypeRestriction + rmIsReservable;
		Query nativeQuery = this.entityManager.createNativeQuery(query, RmConfig.class);
		@SuppressWarnings("unchecked")
		List<RmConfig> rmConfigList = nativeQuery.getResultList();
		List<RmConfig> availableRooms = new ArrayList<RmConfig>();
		if (resourcesIds == null || resourcesIds.size() <= 0) {
			availableRooms = rmConfigList;
		} else {
			for (RmConfig element : rmConfigList) {
				boolean isAllResourcesAval = this.checkAvailabilityOfRoomsByResources(element, resourcesIds);
				if (isAllResourcesAval) {
					availableRooms.add(element);
				}
			}
		}
		return availableRooms;
	}
	
// To Check each room availability with date and startTime,endTime.
	public boolean checkRoomAvailableforEachDateandTime(RmConfig rmConfig, Date date, Time stratTime, Time endTime) {
		int configId = rmConfig.getConfigId();
		String startDateRestriction = "'" + new SimpleDateFormat("yyyy-MM-dd").format(date) + "'";
		String startTimeRestriction = "'" + new SimpleDateFormat("HH:mm:ss.S").format(stratTime) + "'";
		String endTimeRestriction = "'" + new SimpleDateFormat("HH:mm:ss.S").format(endTime) + "'";
		String query1 = "Select * from rm_config where config_id  = "+configId+" and convert(time,"+ startTimeRestriction + ") >= day_start"
		                 + " and convert(time," + endTimeRestriction+ ") <= day_end";
		String query2 = " and not exists (select * from reserve where reserve.bl_id = rm_config.bl_id and reserve.fl_id = rm_config.fl_id "
		+ "and reserve.rm_id = rm_config.rm_id and reserve.date_start = convert(date," + startDateRestriction + ")" +
		" and reserve.status not in('Rejected','Cancelled','Completed') and not(convert(time,"+ startTimeRestriction + ") >= "
		+ "convert(time,reserve.time_end) or (convert(time," + startTimeRestriction+ ") <= convert(time,reserve.time_start) and convert(time," + endTimeRestriction+ ")"
		+ " <= convert(time,reserve.time_start))))";
		String query = query1 + query2;
		Query nativeQuery = this.entityManager.createNativeQuery(query, RmConfig.class);
		@SuppressWarnings("unchecked")
		List<RmConfig> rmConfigList = nativeQuery.getResultList();
		boolean isAvailable = rmConfigList.size() > 0;
		return isAvailable;
	}
	
	public List<RmConfig> getRmConfigByBlAndFlId(int blId, int flId) {
		String query = "Select * from rm_config where  bl_id =" + blId + "'"
				+ "and fl_id =" + flId ;
		Query nativeQuery = this.entityManager.createNativeQuery(query, RmConfig.class);
		@SuppressWarnings("unchecked")
		List<RmConfig> dataRecords = nativeQuery.getResultList();

		if (!dataRecords.isEmpty()) {
			return dataRecords;
		}
		return null;
	}


}
