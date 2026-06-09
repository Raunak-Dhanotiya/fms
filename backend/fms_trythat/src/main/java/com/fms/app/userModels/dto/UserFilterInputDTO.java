package com.fms.app.userModels.dto;

import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;

public class UserFilterInputDTO {

	private Integer id;
	private String name;
	private String userRole;
	private String status;
	private String userName;
	private FilterDTOCopy filterDto;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public FilterDTOCopy getFilterDto() {
		return filterDto;
	}

	public void setFilterDto(FilterDTOCopy filterDto) {
		this.filterDto = filterDto;
	}
	
}
