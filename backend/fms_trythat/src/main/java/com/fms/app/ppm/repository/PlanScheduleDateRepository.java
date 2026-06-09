package com.fms.app.ppm.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import com.fms.app.ppm.models.PlanScheduleDate;

public interface PlanScheduleDateRepository extends JpaRepository<PlanScheduleDate,Integer> {

	@Query("SELECT psd FROM PlanScheduleDate psd WHERE psd.scheduleDate >= :startDate AND psd.scheduleDate <= :endDate")
    List<PlanScheduleDate> findByScheduleDateBetweenInclusive(
    		@Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

	void deleteByPlanScheduleId(int planScheduleId);

	void deleteByPlanScheduleIdAndWrIdIsNull(int planScheduleId);

}
