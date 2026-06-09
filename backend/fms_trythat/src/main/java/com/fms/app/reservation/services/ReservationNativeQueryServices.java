package com.fms.app.reservation.services;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.fms.app.reservation.models.Reservation;
import com.fms.app.reservation.models.dto.BookingReportsFilterDto;
import com.fms.app.reservation.models.dto.ReservationSearchFilterDTO;
import com.fms.app.utils.CommonUtil;
@Service
public class ReservationNativeQueryServices {
	@PersistenceContext
	private EntityManager entityManager;
	public List<Reservation> getAllReservationsBySearchFilter(ReservationSearchFilterDTO filterData) {
		String query = getReservationByFilterQuery(filterData);
		Query nativeQuery = this.entityManager.createNativeQuery(query, Reservation.class);
		@SuppressWarnings("unchecked")
		List<Reservation> dataRecords = nativeQuery.getResultList();
		if (!dataRecords.isEmpty()) {
			return dataRecords;
		}
		return null;
	}
	
	public Map<String, Object> getAllReservationsBySearchFilterPaginated(ReservationSearchFilterDTO filterData) {
		int pageSize=0;
		int pageNumber =0;
		int totalRecords = 0;
		String queryStr = "";
		if(filterData.getFilterDto() !=null && filterData.getFilterDto().getPaginationDTO()!=null) {
			 pageSize=filterData.getFilterDto().getPaginationDTO().getPageSize();
			 pageNumber =filterData.getFilterDto().getPaginationDTO().getPageNo();
		}
		String initailString = getReservationByFilterQuery(filterData);
		if(filterData.getFilterDto() !=null &&  filterData.getFilterDto().getFilterCriteria()!=null) {
			String filterCondition = CommonUtil.getPaginationFilterQueryMultipleWithoutWhere(filterData.getFilterDto().getFilterCriteria());
			initailString += filterCondition;
		}
		if(pageSize>0) {
			queryStr= " "+initailString+" ORDER BY(SELECT NULL) OFFSET "+pageNumber*pageSize+" ROWS FETCH NEXT "+pageSize+" ROWS ONLY" ;
		}else {
			queryStr = initailString;
		}
		Query nativeQuery = this.entityManager.createNativeQuery(queryStr, Reservation.class);
		@SuppressWarnings("unchecked")
		List<Reservation> dataRecords = nativeQuery.getResultList();
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
	
	public String getReservationByFilterQuery(ReservationSearchFilterDTO filterData) {
		String idRestriction = createIdRestriction("reserve_id", filterData.getReserveId());
		String requestedByRestriction = createIdRestriction("requested_by", filterData.getRequestedBy());
		String blRestriction = createIdRestriction("R.bl_id", filterData.getBlId());
		String flRestriction = createIdRestriction("R.fl_id", filterData.getFlId());
		String rmRestriction = createIdRestriction("R.rm_id", filterData.getRmId());
		String statusRestriction = createRestriction("R.status", filterData.getStatus());
		String fromDateRestriction = createDateRestriction(filterData.getDateStart(),"date_start",">=");
		String toDateRestriction = createDateRestriction(filterData.getDateEnd(),"date_start","<=");
		String query = "select R.* from reserve R join bl B on R.bl_id=B.bl_id join fl F on R.fl_id=F.fl_id join rm RM on R.rm_id=RM.rm_id join fm_users U on R.requested_by=U.user_id "
		 + " where (1=1) "+ idRestriction + requestedByRestriction
				+ blRestriction + flRestriction + rmRestriction + statusRestriction + fromDateRestriction + toDateRestriction;
		return query;
	}
	public String createRestriction(String field, String value) {
		return value != null && value.length() >= 1 ? " and " + field + " = " + "'" + value + "'  " : " and 1=1";
	}
	public String createIdRestriction(String field, Integer value) {
		if(value != null) {
			return value != 0 ? " and " + field + " = " + value : " and 1=1 ";
		}else {
			return  " and 1=1 ";
		}
		
	}
	
	public String createIdWithoutAndRestriction(String field, Integer value) {
		if(value != null) {
			return value != 0 ? " " + field + " = " + value : " and 1=1 ";
		}else {
			return  " 1=1 ";
		}
		
	}
	
	public String createDateRestriction(Date date, String field, String operator) {
		return date != null ? " and  convert(date,"+ field + ") "+ operator +" convert(date,'"+ date + "')" : " and 1=1 "; 
	}
		
	public List<Object[]> getCountByMonth(BookingReportsFilterDto filterData) {
		String blRestriction = createIdRestriction("bl_id", filterData.getBlId());
		String flRestriction = createIdRestriction("fl_id", filterData.getFlId());
		String rmRestriction = createIdRestriction("rm_id", filterData.getRmId());
		String statusRestriction = createRestriction("status", filterData.getStatus());
		int year = filterData.getYear();
		String query = "select month(date_start) as month ,COUNT(month(date_start)) as count from reserve "
				+ "where  and (year(date_start)= " + year + ")" + blRestriction + flRestriction
				+ rmRestriction + statusRestriction + " group by month(date_start)";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> dataRecords = nativeQuery.getResultList();
		if (!dataRecords.isEmpty()) {
			return dataRecords;
		}
		return null;
	}

	public List<Reservation> getAllReservationsByMonth(BookingReportsFilterDto filterData) {
		int year = filterData.getYear();
		int month = filterData.getMonth();

		String query = "select * from reserve where year(date_start) = '" + year + "' and month(date_start) = '" + month
				+ "' ";
		Query nativeQuery = this.entityManager.createNativeQuery(query, Reservation.class);
		@SuppressWarnings("unchecked")
		List<Reservation> dataRecords = nativeQuery.getResultList();
		if (!dataRecords.isEmpty()) {
			return dataRecords;
		}
		return null;
	}
}
