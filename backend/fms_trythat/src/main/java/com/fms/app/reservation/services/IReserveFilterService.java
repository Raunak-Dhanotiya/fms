package com.fms.app.reservation.services;

import java.util.List;
import com.fms.app.reservation.models.ReserveFilter;

public interface IReserveFilterService {
	
	public  ReserveFilter saveOrUpdate(ReserveFilter reserveFilter);
	
	public List<ReserveFilter> getReserveFilterByUserName();
		
}
