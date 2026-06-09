package com.fms.paginator.models;

import java.util.List;

public class FilterDTOCopy {
	private PaginationDTO paginationDTO;
	private List<FilterCriteria> filterCriteria;
	public PaginationDTO getPaginationDTO() {
		return paginationDTO;
	}
	public void setPaginationDTO(PaginationDTO paginationDTO) {
		this.paginationDTO = paginationDTO;
	}
	public List<FilterCriteria> getFilterCriteria() {
		return filterCriteria;
	}
	public void setFilterCriteria(List<FilterCriteria> filterCriteria) {
		this.filterCriteria = filterCriteria;
	}
	
}
