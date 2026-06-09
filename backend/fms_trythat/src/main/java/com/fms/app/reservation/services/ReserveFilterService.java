package com.fms.app.reservation.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fms.app.reservation.models.ReserveFilter;
import com.fms.app.reservation.repository.ReserveFilterRepository;
import com.fms.app.security.AuthorizeUserInfo;

@Service
public class ReserveFilterService {

	@Autowired
	ReserveFilterRepository reserveFilterRepository;

	@Autowired
	AuthorizeUserInfo userInfo;

	public ReserveFilter saveOrUpdate(ReserveFilter reserveFilter) {
		ReserveFilter record =  reserveFilterRepository.save(reserveFilter);
		return record;
	}

	public List<ReserveFilter> getReserveFilterByUserId() {
		final int userId = userInfo.getUserIfo().getUserId();
		return reserveFilterRepository.getReserveFilterByUserId(userId);
	}
	
	public boolean isDuplicate(List<ReserveFilter> reserveFilterData, ReserveFilter secondRecord) {

		List<ReserveFilter> recordsWithoutDefault = reserveFilterData.stream()
			    .filter(p ->
			    		p.getBlId() == secondRecord.getBlId() && 
						p.getRmId() == secondRecord.getRmId() &&
						p.getFlId() == secondRecord.getFlId() &&
						p.getUserId() == secondRecord.getUserId() &&
						p.getTimeStart().equals(secondRecord.getTimeStart()) &&
						p.getTimeEnd().equals(secondRecord.getTimeEnd()) &&
						p.getIsDefault() == secondRecord.getIsDefault() &&
						p.getCapacity() == secondRecord.getCapacity()   		
			    		).collect(Collectors.toList());
		
		if(recordsWithoutDefault.size()>0)
				return true;
		
		return false;
	}
	
	public ReserveFilter SwapRecords(ReserveFilter firstRecord, ReserveFilter secondRecord) {
		firstRecord.setBlId(secondRecord.getBlId());
		firstRecord.setFlId(secondRecord.getFlId());
		firstRecord.setRmId(secondRecord.getRmId());
		firstRecord.setUserId(secondRecord.getUserId());
		firstRecord.setTimeStart(secondRecord.getTimeStart());
		firstRecord.setTimeEnd(secondRecord.getTimeEnd());
		firstRecord.setIsDefault(secondRecord.getIsDefault());
		firstRecord.setCapacity(secondRecord.getCapacity());
		return firstRecord;
	}
}