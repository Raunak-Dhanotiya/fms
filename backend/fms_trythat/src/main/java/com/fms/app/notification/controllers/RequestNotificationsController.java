package com.fms.app.notification.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fms.app.appParams.services.MessagesService;
import com.fms.app.common.models.Email;
import com.fms.app.employee.models.Employee;
import com.fms.app.employee.services.EmployeeService;
import com.fms.app.helpdesk.models.dto.WrDto;
import com.fms.app.sidenav.FMProcesses;
import com.fms.app.sidenav.FMProcessesServiceImpl;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class RequestNotificationsController 
{
	private static final Logger logger = LogManager.getLogger(RequestNotificationsController.class);
	@Autowired
	NotifyUsers notifyController;
	
	@Autowired
	MessagesService messagesService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	FMProcessesServiceImpl fMProcessesServiceImpl;
	
	private final JavaMailSender mailSender;
	
	

    @Autowired
    public RequestNotificationsController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
	
	public String sendNotificationforRequest(WrDto savedRecord,List<String> emailTo, String actionNeeded){
	
		String status = savedRecord.getStatus();
		String subject = "";
		if(status.equalsIgnoreCase("requested")&& actionNeeded == null) {
			subject = CommonConstant.REQUEST_GENERATED + " "+  String.valueOf(savedRecord.getWrId());
		} else if(actionNeeded != null && actionNeeded.equals("Approval") ) {
			subject = CommonConstant.REQUEST_PENDING_APPROVAL + " "+  String.valueOf(savedRecord.getWrId());
		}else if (actionNeeded != null && actionNeeded.equals("Assigned")) {
			subject = CommonConstant.REQUEST_ASSIGNED + " "+ String.valueOf(savedRecord.getWrId());
		}
		else {
			FMProcesses process = this.fMProcessesServiceImpl.getFMProcessesByProcessCode("Facilities Desk");
			subject = this.messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "subject_for_request_notification").getMsgText();
			subject = subject.replace("{wr.wrId}", String.valueOf(savedRecord.getWrId()));
			subject = subject.replace("{wr.status}", status);
			
		}
		
		String content = createMailContentForRequest(savedRecord);
		String cc = "";
		Email mail = notifyController.prepareEmailListOfTo(emailTo, cc, subject, content);
		
		try {
			notifyController.sendEmail(mail);
			return CommonConstant.MAIL_SENT_SUCCESSFULLY;
			
		} catch (Exception e) {
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestNotificationsController.sendNotificationforRequest: "+stacktrace,e);
			return CommonConstant.MAIL_NOT_SENT;
		}
	}
	
	public String createMailContentForRequest(WrDto WrData) {
		
        //DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

        LocalDate date = LocalDate.parse(WrData.getDateRequested());
        //LocalDate date = LocalDate.parse(WrData.getDateRequested(), inputFormatter);
        
        String formattedDate = outputFormatter.format(date);

		Employee emRecord = this.employeeService.getEmployeeByID(WrData.getRequestedFor());
		String empName = "";
		if (emRecord != null) {
			empName = emRecord.getFirstName() + " " + emRecord.getLastName();

		}
		FMProcesses process = this.fMProcessesServiceImpl.getFMProcessesByProcessCode("Facilities Desk");
		String content = this.messagesService.getMessagesByProcessIdAndCode(process.getProcessId(),"content_for_request_notification").getMsgText();
		content = content.replace("{wr.wrId}",String.valueOf(WrData.getWrId()));
		content = content.replace("{wr.requestedFor}", empName);
		content = content.replace("{wr.dateRequested}",formattedDate );
		content = content.replace("{wr.status}", WrData.getStatus());
		content = content.replace("{wr.comments}", WrData.getComments() == null ? "": WrData.getComments());
		
		return content;

	}
	
}
