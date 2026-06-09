package com.fms.paginator.models;

public class FilterDTO {
	private PaginationDTO paginationDTO;
	private FilterCriteria filterCriteria;
	public PaginationDTO getPaginationDTO() {
		return paginationDTO;
	}
	public void setPaginationDTO(PaginationDTO paginationDTO) {
		this.paginationDTO = paginationDTO;
	}
	public FilterCriteria getFilterCriteria() {
		return filterCriteria;
	}
	public void setFilterCriteria(FilterCriteria filterCriteria) {
		this.filterCriteria = filterCriteria;
	}
	
}
