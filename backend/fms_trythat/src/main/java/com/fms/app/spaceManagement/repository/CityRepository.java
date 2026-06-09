package com.fms.app.spaceManagement.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.spaceManagement.models.City;

public interface CityRepository extends JpaRepository<City, Integer>, JpaSpecificationExecutor<City> {


	public City getCityByCityId(String cityId);

	public boolean existsByCityCodeAndStateIdAndRegnIdAndCtryId(String cityCode, Integer stateId, Integer regnId,
			Integer cntryId);
}
