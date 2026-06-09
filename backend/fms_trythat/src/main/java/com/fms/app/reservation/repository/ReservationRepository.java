package com.fms.app.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.reservation.models.Reservation;



public interface ReservationRepository extends JpaRepository<Reservation, Integer>,JpaSpecificationExecutor<Reservation> {
	public List<Reservation> findAllByOrderByDateStartDesc();
	public List<Reservation> findByStatus(String status);
}
