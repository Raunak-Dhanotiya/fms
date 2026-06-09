package com.fms.app.reservation.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fms.app.reservation.models.ReserveAttendees;

public interface ReserveAttendeeRepository extends CrudRepository<ReserveAttendees, Integer>{

	List<ReserveAttendees> getByReserveId(int reserveId);


}
