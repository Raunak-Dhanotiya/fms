package com.fms.app.employee.services;

import java.util.List;

import com.fms.app.employee.models.EmStd;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.PagedResponse;


public interface IEmStdService {
	
	public PagedResponse<EmStd> getEmStdPaginated(FilterDTOCopy filterDTO);
	
	public List<EmStd> getEmStd();

	public EmStd getEmStd(String emStd);

	public void saveEmStd(EmStd emStd);

	public void delete(String emStd);

	public void delete(EmStd emStd);

	public boolean checkStdExist(String emStd);


}
