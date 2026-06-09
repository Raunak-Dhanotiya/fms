package com.fms.app.helpdesk.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.fms.app.helpdesk.models.Holidays;


@Repository
public interface IHolidaysRepository extends JpaRepository<Holidays, Integer>,JpaSpecificationExecutor<Holidays>{
	
	public Holidays findByHolidayDate(Date holidayDate);
	
	boolean existsByHolidayDate(Date date);

}
