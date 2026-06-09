package com.fms.app.helpdesk.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.employee.models.Employee;
import com.fms.app.employee.services.EmployeeService;
import com.fms.app.helpdesk.models.CraftsPerson;
import com.fms.app.helpdesk.models.SlaRequestSteps;
import com.fms.app.helpdesk.models.WorkTeams;
import com.fms.app.helpdesk.models.dto.WrDto;
import com.fms.app.helpdesk.repository.IRequestStepsLogRepository;
import com.fms.app.notification.controllers.RequestNotificationsController;

@Service
public class RequestNotificationsStepsService {
	
	@Autowired
	WorkTeamsSerrvices workTeamsServices;

	@Autowired
	EmployeeService emService;

	@Autowired
	CraftsPersonService technicianService;

	@Autowired
	RequestNotificationsController requestNotificationsController;

	@Autowired
	IRequestStepsLogRepository requestStepLogRepo;

	public void sendNotifications(WrDto requestData, SlaRequestSteps responsible,String stepType) {

		 List<String> emailsToString = getResponsibleMails(responsible);
		 if(emailsToString.size() > 0) {
			 requestNotificationsController.sendNotificationforRequest(requestData, emailsToString,stepType);
		 }
		

	}

	public List<String> getResponsibleMails(SlaRequestSteps responsible) {
		List<String> teamMailIds = new ArrayList<String>();

		if (responsible.getEmId() != null && responsible.getEmId() >0) {
			Employee employee = emService.getEmployeeByID(responsible.getEmId());
			teamMailIds.add(employee.getEmail());
		} else if (responsible.getCfId() != null && responsible.getCfId() >0) {
			CraftsPerson technician = technicianService.getCraftspersonById(responsible.getCfId());
			teamMailIds.add(technician.getEmail());
		} else if (responsible.getTeamId() != null && responsible.getTeamId() >0) {
			teamMailIds = getTeamMembersMails(responsible.getTeamId());
		}
		return teamMailIds;

	}

	public List<String> getTeamMembersMails(int teamId) {

		List<String> teamMailIds = new ArrayList<String>();
		List<WorkTeams> allTeamsById = this.workTeamsServices.getAllByTeamId(teamId);
		if(allTeamsById.size() > 0) {
			for (WorkTeams wrTeam : allTeamsById) {
				if (wrTeam.getEmId() > 0) {
					Employee employee = emService.getEmployeeByID(wrTeam.getEmId());
					teamMailIds.add(employee.getEmail());
				} else if (wrTeam.getCfId() != null) {
					CraftsPerson technician = technicianService.getCraftspersonById(wrTeam.getCfId());
					teamMailIds.add(technician.getEmail());
				}
			}
		}
		return teamMailIds;
	}
}
