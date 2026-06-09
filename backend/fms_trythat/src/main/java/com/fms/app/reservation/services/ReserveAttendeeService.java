package com.fms.app.reservation.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.reservation.models.ReserveAttendees;
import com.fms.app.reservation.repository.ReserveAttendeeRepository;

@Service
public class ReserveAttendeeService {

	@Autowired
	ReserveAttendeeRepository reserveAttendeeRepository;

	public void saveOrUpdate(ReserveAttendees data) {
		reserveAttendeeRepository.save(data);
	}
	
	public void deleteAttendeById(int id) {
		reserveAttendeeRepository.deleteById(id);
	}
	
	public List<ReserveAttendees> getByRserveId(int reserveId) {
		return reserveAttendeeRepository.getByReserveId(reserveId);
	}
	
}
