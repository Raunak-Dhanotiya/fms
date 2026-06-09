package com.fms.app.ppm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.ppm.models.PlanSchedule;

public interface PlanScheduleRepository extends JpaRepository<PlanSchedule,Integer> {

	List<PlanSchedule> findByPlanLocEqId(int planLocEqId);

}
