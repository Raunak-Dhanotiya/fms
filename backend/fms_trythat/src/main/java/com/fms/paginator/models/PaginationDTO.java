package com.fms.paginator.models;

public class PaginationDTO {
	
	private Integer pageNo;
	private Integer pageSize;
	private String[] sortBy;
	private String sortOrder;
	
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String[] getSortBy() {
		return sortBy;
	}
	public void setSortBy(String[] sortBy) {
		this.sortBy = sortBy;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
