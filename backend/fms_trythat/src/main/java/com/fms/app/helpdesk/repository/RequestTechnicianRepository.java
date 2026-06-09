package com.fms.app.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.helpdesk.models.RequestTechnician;

public interface RequestTechnicianRepository extends JpaRepository<RequestTechnician, Integer>
{

	List<RequestTechnician> findByRequestId(int requestId);

	boolean existsByRequestIdAndTechnicianId(int requestId, int technicianId);

	RequestTechnician findByRequestTechnicianId(int requestTechnicianId);

	RequestTechnician findByRequestIdAndTechnicianId(int requestId, int technicianId);
}
