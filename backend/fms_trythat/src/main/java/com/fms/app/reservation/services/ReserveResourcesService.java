package com.fms.app.reservation.services;

import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.reservation.models.ReserveResources;
import com.fms.app.reservation.models.Resources;
import com.fms.app.reservation.models.dto.ReserveRsAvailableCheckDTO;
import com.fms.app.reservation.repository.ReserveResourcesRepository;

@Service
public class ReserveResourcesService {
	@Autowired
	ReserveResourcesRepository reserveResourcesRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired  
	ResourcesService resourcesService;
	
	@Autowired
	ReservationService reservationSrv;

	public void saveOrUpdate(ReserveResources data) {
		reserveResourcesRepository.save(data);
	}

	public void deleteReserveRsById(int id) {
		reserveResourcesRepository.deleteById(id);
	}

	public int getReserveResourcesQuanitiyInUse(ReserveRsAvailableCheckDTO data) {
		String startTime = "'" + new SimpleDateFormat("HH:mm:ss.S").format(data.getTimeStart()) + "'";
		String endTime = "'" + new SimpleDateFormat("HH:mm:ss.S").format(data.getTimeEnd()) + "'";
		String startDate = "'" + new SimpleDateFormat("yyyy-MM-dd").format(data.getDate()) + "'";
		int reserveRsId = data.getReserveRsId();
        int resourcesId = data.getResourcesId();
		String query = "select isnull(sum(reserve_rs.qty),0) as quanitiy_in_use from reserve_rs "
				+ " left join reserve on reserve.reserve_id = reserve_rs.reserve_id "
				+ " where reserve_rs.resources_id = " + resourcesId  
				+ " and not  reserve_rs.reserve_rs_id = "+ reserveRsId 
				+" and reserve_rs.status = 'Available' and reserve.date_start = convert(date," + startDate + ")"
		        +" and reserve.status not in ('Rejected','Cancelled','Completed')"
			    + " and  not(" + " convert(time, " + startTime  +")"
				+ " >= convert(time,time_end) or ( convert(time, " + startTime + ")"
				+ " <= convert(time,time_start) and convert(time, " + endTime + ") <= convert(time,time_start)))";

		Query nativeQuery = this.entityManager.createNativeQuery(query);
		int quanitiyInUse = (int) nativeQuery.getSingleResult();
		return quanitiyInUse;
	}
	
	public boolean checkIsResourceAvailable(ReserveRsAvailableCheckDTO reserveRsAvailableCheckDTO,int reqQuantity) {
		int quanitiyInUse = getReserveResourcesQuanitiyInUse(reserveRsAvailableCheckDTO);
		Resources resource = resourcesService.getResourceById(reserveRsAvailableCheckDTO.getResourcesId());
		boolean isAval = reqQuantity <= resource.getQuanity() -  quanitiyInUse;
		return isAval;
	}

}
