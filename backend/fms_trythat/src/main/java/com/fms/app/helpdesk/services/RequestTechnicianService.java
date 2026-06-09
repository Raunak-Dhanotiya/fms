package com.fms.app.helpdesk.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fms.app.helpdesk.models.CraftsPerson;
import com.fms.app.helpdesk.models.RequestTechnician;
import com.fms.app.helpdesk.models.WorkTeams;
import com.fms.app.helpdesk.models.Wr;
import com.fms.app.helpdesk.models.dto.WrDto;
import com.fms.app.helpdesk.repository.RequestTechnicianRepository;
import com.fms.app.notification.controllers.RequestNotificationsController;


@Service
public class RequestTechnicianService 
{
	@Autowired
	RequestTechnicianRepository requestTechnicianRepository;
	
	@Autowired
	RequestStepsLogService requestStepsLogService;
	
	@Autowired
	CraftsPersonService technicianService;
	
	@Autowired
	RequestNotificationsController requestNotificationsController;
	
	@Autowired
	WrServices wrService;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	SlaResponseParametersServices slaResponseParametersServ;
	
	
	public RequestTechnician saveOrUpdate(RequestTechnician dataRecord) {
		boolean isTechnicianChanged = false;
		Integer requestTechnicianId = dataRecord.getRequestTechnicianId();
		if(requestTechnicianId != null && requestTechnicianId > 0) {
			RequestTechnician newTechnicianData = this.requestTechnicianRepository.findByRequestTechnicianId(requestTechnicianId);
			if(newTechnicianData.getTechnicianId() == dataRecord.getTechnicianId()) {
				isTechnicianChanged = true;
			}
		}
		
		RequestTechnician data = this.requestTechnicianRepository.save(dataRecord);
		
		if(requestTechnicianId == null || requestTechnicianId == 0) { // New Record
			this.notifyRequestTechnician(data);
		}else if( requestTechnicianId > 0 && !isTechnicianChanged) { // Edit Technician 
			this.notifyRequestTechnician(data);
		}
		
		return data;
	}

	public List<RequestTechnician> getAllRequestTechnician()
	{
		return this.requestTechnicianRepository.findAll();
	}

	public List<RequestTechnician> getByRequestId(int requestId) {
		return this.requestTechnicianRepository.findByRequestId(requestId);
	}
	
	public void deleteByRequestId(RequestTechnician RequestTechnicianData) {
		this.requestTechnicianRepository.delete(RequestTechnicianData);
	}
    
	public RequestTechnician getById(int id) {
		return this.requestTechnicianRepository.findById(id).orElse(null);
	}

	public boolean checkIsTechnicianExist(int requestId, int technicianId) {
		
		return this.requestTechnicianRepository.existsByRequestIdAndTechnicianId(requestId,technicianId);
	}
	
	
	//Assign technician when request is auto issued
	public void AssignResponsibleTechnicians(Integer teamId, Integer technicianId,double requiredHours,WrDto wrData) {
		List<String> technicianMails = new ArrayList<>();
		if(technicianId != null) {
			String mail = assignRequestTechnician(technicianId,wrData.getWrId(),requiredHours,teamId);
			technicianMails.add(mail);
		} else {
			List<WorkTeams> teamsList = requestStepsLogService.getTeamsByTeamId(teamId);
			if(teamsList.size() > 0) {
				teamsList.forEach(team -> {
					if(team.getCfId() != null) {
						String mail = assignRequestTechnician(team.getCfId(),wrData.getWrId(),requiredHours,team.getTeamId());
						technicianMails.add(mail);
					}
				});
			}
		}
		notifyTechnician(wrData,technicianMails);
	}
	
	public String assignRequestTechnician(Integer technicianId,Integer requestId,double requiredHours,Integer teamId) {
		java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		java.sql.Time currentTime = new java.sql.Time(Calendar.getInstance().getTime().getTime());
		
		CraftsPerson technician = technicianService.getCraftspersonById(technicianId);
		RequestTechnician requestTechnician = new RequestTechnician();
		requestTechnician.setRequestTechnicianId(0);
		requestTechnician.setDateAssign(currentDate);
		requestTechnician.setTimeAssign(currentTime);
		requestTechnician.setRequestId(requestId);
		requestTechnician.setTechnicianId(technician.getCfId());
		requestTechnician.setHoursRequired(requiredHours); //Calculated hours from service window of SLA
		requestTechnician.setTeamId(teamId);
		saveOrUpdate(requestTechnician);
		return technician.getEmail();
	}
	
	//while saving request technician notify the technician
	public void notifyRequestTechnician(RequestTechnician data) {
		List<String> technicianMails = new ArrayList<>();
		Wr wr = this.wrService.getWrId(data.getRequestId());
		WrDto wrData = this.mapper.map(wr, WrDto.class);
		CraftsPerson technician = technicianService.getCraftspersonById(data.getTechnicianId());
	    technicianMails.add(technician.getEmail());
		this.notifyTechnician(wrData,technicianMails);
	}
	
	//notify technician 
	public void notifyTechnician(WrDto wrData,List<String> technicianMail) {
		requestNotificationsController.sendNotificationforRequest(wrData, technicianMail,"Assigned");
		
	}

	public RequestTechnician getByRequestIdandTechnicianId(int requestId, Integer technicianId) {
		return this.requestTechnicianRepository.findByRequestIdAndTechnicianId(requestId, technicianId);
	}

	//update request techncian, if request completed by technician.
	public void updateRequestTechncian(Wr wrData, Integer loggedInTechnicianId) {
		RequestTechnician requestTechnicianData = this.getByRequestIdandTechnicianId(wrData.getWrId(), loggedInTechnicianId);
		
		if(requestTechnicianData.getRequestId() > 0 && requestTechnicianData.getTechnicianId() > 0) {
			requestTechnicianData.setCompleteDate(wrData.getDateCompleted());
			requestTechnicianData.setCompleteTime(wrData.getTimeCompleted());
			
			this.requestTechnicianRepository.save(requestTechnicianData);
		}
		
	}
	
}
