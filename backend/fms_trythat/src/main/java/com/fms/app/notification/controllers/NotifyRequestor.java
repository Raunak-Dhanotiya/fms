package com.fms.app.notification.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

import com.fms.app.appParams.services.AppParamsService;
import com.fms.app.appParams.services.MessagesService;
import com.fms.app.common.models.Email;
import com.fms.app.employee.services.EmployeeService;
import com.fms.app.reservation.models.Reservation;
import com.fms.app.reservation.services.ReservationNativeQueryServices;
import com.fms.app.reservation.services.ReservationService;
import com.fms.app.sidenav.FMProcesses;
import com.fms.app.sidenav.FMProcessesServiceImpl;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.Fl;
import com.fms.app.spaceManagement.models.Rm;
import com.fms.app.spaceManagement.services.BlService;
import com.fms.app.spaceManagement.services.FlService;
import com.fms.app.spaceManagement.services.RmService;
import com.fms.app.userModels.User;
import com.fms.app.userServices.UserServiceImpl;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class NotifyRequestor {
	private static final Logger logger = LogManager.getLogger(NotifyRequestor.class);

	@Autowired
	JavaMailSender mailSender;
	@Autowired
	EmployeeService emService;
	@Autowired
	UserServiceImpl userService;
	@Autowired
	NotifyUsers notifyController;

	@Autowired
	MessagesService messagesService;

	@Autowired
	AppParamsService apParamService;

	@Autowired
	ReservationService reservationSrv;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	FMProcessesServiceImpl fMProcessesServiceImpl;
	
	@Autowired
	ReservationNativeQueryServices reservationNativeQuerySrv;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	BlService blService;
	
	@Autowired 
	FlService flService;
	
	@Autowired
	RmService rmService;
	
	public void notifyUser(String emId, String pswd, String compEmail) {
		String emailTo = emId;
		String cc = compEmail;
		String content = "Welcome to: " + emId + "</br>" + "</br>" + "User Name: " + emId + "</br>" + "</br>"
				+ "Password: " + pswd + "</br>" + "</br>" + "Note: (Please change password on first login)";
		String subject = "New user creation";
		Email mail = notifyController.prepareEmail(emailTo, cc, subject, content);
		try {
			notifyController.sendEmail(mail);
		}  catch (Exception e) {
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in NotifyRequestor.notifyUser: "+stacktrace,e);
		}

	}

	public void notifyCompany(String compName, String emId, String pswd, String role, int noOfEmplys,
			String compRegNum) {
		String emailTo = emId;
		String cc = "";
		String content = "Company Name: " + compName + "</br>" + "</br>" + "Company Regestration Number: " + compRegNum
				+ "</br>" + "</br>" + "No of Employees : " + noOfEmplys + "</br>" + "</br>" + "User Name: " + emId
				+ "</br>" + "</br>" + "Password: " + pswd + "</br>" + "</br>" + "Role: " + role + "</br>" + "</br>";
		String subject = "New Company";
		Email mail = notifyController.prepareEmail(emailTo, cc, subject, content);

		try {
			notifyController.sendEmail(mail);
		} catch (Exception e) {
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in NotifyRequestor.notifyCompany: "+stacktrace,e);
		}
	}

	public void notifyLimitExceeded(String compName, String emId, String msg) {
		String emailTo = emId;
		String cc = "";
		String body = CommonConstant.limitExceed_request_body.replace("#compName#", compName);
		body = body.replace("#msg#", msg);
		body = body.replace("#regards#", CommonConstant.MAIL_FROM);

		String content = body;

		String subject = "Limit Exceeded";
		Email mail = notifyController.prepareEmail(emailTo, cc, subject, content);

		try {
			notifyController.sendEmail(mail);
		}  catch (Exception e) {
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in NotifyRequestor.notifyLimitExceeded: "+stacktrace,e);
		}

	}

	public void notifyReservationSuccess(Reservation savedRecord, List<String> attendesEmails,
			List<Date> listAvalDates, List<String> approverEmails ) {
		User user = this.userService.getUser(savedRecord.getRequestedBy());
		if(user.getEmEmployee()!=null) {
			String emailTo = user.getEmEmployee().getEmail();
			FMProcesses process = this.fMProcessesServiceImpl.getFMProcessesByProcessCode("Workspace Management");
			String subject = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "Meeting Notification Subject")
					.getMsgText() + " Id: " + savedRecord.getReserveId();
			String details = createMailContentForBooking(savedRecord, listAvalDates,"requestor approved");
			String regards = "<div>Regards,</br>FMS </div>";
			String content =  details + regards;
			String cc = "";
			Email mail = notifyController.prepareEmail(emailTo, cc, subject, content);
			try {
				notifyController.sendEmail(mail);
			} catch (Exception e) {
				String stacktrace = CommonUtil.getStakeTrace(e);
				logger.error("Exception in NotifyRequestor.notifyReservationSuccess: "+stacktrace,e);
			}
			String subjectForAttendee = messagesService
					.getMessagesByProcessIdAndCode(process.getProcessId(), "approved_meeting_sub_attendee").getMsgText() + " Id: "
					+ savedRecord.getReserveId();
			String attendeeContent = createMailContentForBooking(savedRecord,listAvalDates,"attendee invited");
			attendesEmails.forEach(attendee -> {
				sendAttendeesEmail(attendee, subjectForAttendee, attendeeContent);
			});
			
			if(approverEmails != null) {
				List<String> otherApprovers = new ArrayList<>(approverEmails);
				User approvedByUser = this.userService.getUser(savedRecord.getApprovedBy());
				if(approvedByUser!=null && approvedByUser.getEmEmployee()!=null) {
					String approvedBy = approvedByUser.getEmEmployee().getEmail();
					otherApprovers.remove(approvedBy);
					notifyToApprovers(savedRecord,otherApprovers,"approver already approved");
				}
			}
		}
	}

	public void notifyCancelBooking(Reservation savedRecord, List<String> attendesEmails ,List<String> approverEmails) {
		User user = this.userService.getUser(savedRecord.getRequestedBy());
		if(user.getEmEmployee()!=null) {
			String emailTo = user.getEmEmployee().getEmail();
			FMProcesses process = this.fMProcessesServiceImpl.getFMProcessesByProcessCode("Workspace Management");
			String subject = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "meeting_cancel_subject")
					.getMsgText() + " Id: " + savedRecord.getReserveId();
			;
			String checkCancel = "";
			if(savedRecord.getCancelledBy() > 0) {
				checkCancel = "cancelled";
			}else {
				checkCancel = "auto cancelled";
			}
			String details = createMailContentForBooking(savedRecord, null,checkCancel);
			String regards = "<div>Regards,</br>FMS </div>";
			String reason = "<div style='margin-bottom:20px'><b>Reason</b> : " + savedRecord.getCancelledReason()
					+ "</div>  ";
			String content =  details + regards + reason;
			String cc = "";
			Email mail = notifyController.prepareEmail(emailTo, cc, subject, content);
			try {
				notifyController.sendEmail(mail);
			}  catch (Exception e) {
				String stacktrace = CommonUtil.getStakeTrace(e);
				logger.error("Exception in NotifyRequestor.notifyCancelBooking: "+stacktrace,e);
			}
			
			if(attendesEmails != null) {
				attendesEmails.forEach(attendee -> {
					sendAttendeesEmail(attendee, subject, content);
				});
			}
			if(approverEmails != null) {
				notifyToApprovers(savedRecord,approverEmails,"cancelled");
			}
		}
		
	}

	public void notifyCheckInReminder(Reservation savedRecord, List<String> attendesEmails) {
		User user = this.userService.getUser(savedRecord.getRequestedBy());
		if(user.getEmEmployee()!=null) {
			String emailTo = user.getEmEmployee().getEmail();
			FMProcesses process = this.fMProcessesServiceImpl.getFMProcessesByProcessCode("Workspace Management");
			String subject = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "check_in_reminder_subject")
					.getMsgText() + " Id: " + savedRecord.getReserveId();
			String details = createMailContentForBooking(savedRecord, null,"requestor check in");
			String regards = "<div>Regards,</br>FMS </div>";
			String content =  details + regards;
			String cc = "";
			Email mail = notifyController.prepareEmail(emailTo, cc, subject, content);
			try {
				notifyController.sendEmail(mail);
				if(savedRecord.getCheckInNotifyCount()==null) {
					savedRecord.setCheckInNotifyCount(1);
				}else {
					savedRecord.setCheckInNotifyCount(savedRecord.getCheckInNotifyCount() + 1);
				}
				reservationSrv.saveOrUpdate(savedRecord);

			}  catch (Exception e) {
				String stacktrace = CommonUtil.getStakeTrace(e);
				logger.error("Exception in NotifyRequestor.notifyCheckInReminder: "+stacktrace,e);
			}
			attendesEmails.forEach(attendee -> {
				sendAttendeesEmail(attendee, subject, content);
			});
		}
	}

	public void notifyApprovalReminder(Reservation savedRecord , List<String> approverEmails ,List<Date> listAvalDates, String update) {
		FMProcesses process = this.fMProcessesServiceImpl.getFMProcessesByProcessCode("Workspace Management");
		String subject = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "approver_waiting_for_approval_subject")
				.getMsgText() + " Id :" + savedRecord.getReserveId() + " " + update;
		String details = createMailContentForBooking(savedRecord, listAvalDates ,"approver waiting for approval");
		String regards = "<div>Regards,</br>FMS </div>";
		String content = details + regards;
		if(approverEmails != null) {
			approverEmails.forEach( each -> {
				String email = each ;
				sendApproverEmail(email,subject,content);
			});
		}
	}

	public String createMailContentForBooking(Reservation reserveData, List<Date> listAvalDates, String statusText) {
		String datePattern = "dd MMM yyyy";
		String timePattern = "HH:mm";
		SimpleDateFormat simpleDate = new SimpleDateFormat(datePattern);
		SimpleDateFormat simpleTime = new SimpleDateFormat(timePattern);
		String dateText = "";

		if (listAvalDates != null) {
			dateText += createDatesTableForBooking(listAvalDates) + " ";

		} else {
			dateText += simpleDate.format(reserveData.getDateStart()) + " ";

		}
		String  topContent = "";
		FMProcesses process = this.fMProcessesServiceImpl.getFMProcessesByProcessCode("Workspace Management");
		String salutation = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "user_salutation").getMsgText();
		if(statusText.equalsIgnoreCase("requestor waiting for approval")) {
			topContent = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "requestor_waiting_for_approval_content").getMsgText();
		}
		else if(statusText.equalsIgnoreCase("requestor approved")) {
			topContent = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "requestor_approved_content").getMsgText();
		}
		else if(statusText.equalsIgnoreCase("requestor rejected")) {
			topContent = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "requestor_rejected_content").getMsgText();
		}
		else if(statusText.equalsIgnoreCase("requestor check in")) {
			topContent = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "requestor_check_in_reminder_content_part1").getMsgText()
					+ " "+ simpleTime.format(reserveData.getTimeStart()) + " " +
					messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "requestor_check_in_reminder_content_part2").getMsgText();
		}
		else if(statusText.equalsIgnoreCase("requestor updated")) {
			topContent = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "requestor_meeting_updated_content").getMsgText();
		}
		else if(statusText.equalsIgnoreCase("cancelled")) {
			topContent = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "meeting_cancelled_content").getMsgText()
					+" "+ reserveData.getCancelledByUser().getUserName() +".";
		}
		else if (statusText.equalsIgnoreCase("auto cancelled")) {
			topContent = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "meeting_auto_cancelled_content").getMsgText();
		}
		else if (statusText.equalsIgnoreCase("attendee invited")) {
			topContent = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "attendee_invited_meeting_content").getMsgText()
					+ " " + reserveData.getRequestedByUser().getUserName() +".";
		}
		else if (statusText.equalsIgnoreCase("attendee removed")) {
			topContent = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "attendee_removed_meeting_content").getMsgText();
		}
		else if (statusText.equalsIgnoreCase("approver waiting for approval")) {
			topContent = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "approver_waiting_for_approval_content").getMsgText();
		}
		else if (statusText.equalsIgnoreCase("approver already approved")) {
			topContent = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "approver_already_approved_content").getMsgText()
					+ " " + reserveData.getApprovedByUser().getUserName() +".";
		}
		else if (statusText.equalsIgnoreCase("approver already cancelled")) {
			topContent = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "approver_already_cancelled_content").getMsgText()
					+ " " + reserveData.getCancelledByUser().getUserName() +".";
		}
		else if (statusText.equalsIgnoreCase("approver already rejected")) {
			topContent = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "approver_already_rejected_content").getMsgText()
					+ " " + reserveData.getRejectedByUser().getUserName() +".";
		}
//		Bl blRecord = this.blService.getBlById(reserveData.getBlId());
//		Fl flRecord = this.flService.getFlByBlIdAndFlId(reserveData.getFlId(),reserveData.getBlId()).get(0);
		Rm rmRecord = this.rmService.getRmByBlIdAndFlIdAndRmId(reserveData.getBlId(),reserveData.getFlId(),reserveData.getRmId()).get(0);
		Bl blRecord = rmRecord.getBl();
		Fl flRecord = rmRecord.getFl();
		String blName = blRecord.getBlName();
		if(blName == null || blName.length() <= 0) {
			blName = blRecord.getBlCode();
		}
		
		String flName = flRecord.getFlName();
		if(flName == null || flName.length() <= 0) {
			flName = flRecord.getFlCode();
		}
		
		String rmName = rmRecord.getRmName();
		if(rmName == null || rmName.length() <= 0) {
			rmName = rmRecord.getRmCode();
		}
		
		String query1 = "SELECT timezone_id FROM bl WHERE bl_id = "+reserveData.getBlId() ;
		Query nativeQuery1 = this.entityManager.createNativeQuery(query1);

		String blTimeZone = (String) nativeQuery1.getSingleResult();

		String content =  salutation +" ," + "</br>" + "</br>" + 
				topContent + "</br>"+ "</br>"+
				"<b>Booking Id</b> " + " : " + String.valueOf(reserveData.getReserveId()) + "</br>" + "<b>Status</b> "
				+ " : " + reserveData.getStatus() + "</br>" + "<b>Location</b> " + "	: Building-"
				+ blName + " | Floor-" + flName + " | Room-" + rmName
				+ "</br>" + "<b>Timings</b>	" + " : " + simpleTime.format(reserveData.getTimeStart())+ " - "
				+ simpleTime.format(reserveData.getTimeEnd())+" ["+blTimeZone + "] </br>" + "<div style='display:flex '>" + "<b>Date</b> "
				+ " : " + dateText + "</div>" + "</br>";

		return content;
	}

	public String createDatesTableForBooking(List<Date> listAvalDates) {
		String datePattern = "dd MMM yyyy";
		SimpleDateFormat simpleDate = new SimpleDateFormat(datePattern);
		String dates = " ";

		for (Date date : listAvalDates) {
			dates = dates + simpleDate.format(date) + " | ";
		}
		;

		dates = dates.substring(0, dates.length() - 2);
		return dates;
	}

	public void sendAttendeesEmail(String attendeeEmail, String sub, String cont) {
		String emailTo = attendeeEmail;
		String subject = sub;
		String content = cont;
		String cc = "";
		Email mail = notifyController.prepareEmail(emailTo, cc, subject, content);

		try {
			notifyController.sendEmail(mail);
		} catch (Exception e) {
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in NotifyRequestor.sendAttendeesEmail: "+stacktrace,e);
		}

	}

	public void notifyRejectBooking(Reservation savedRecord ,List<String> approverEmails) {
		User user = this.userService.getUser(savedRecord.getRequestedBy());
		if(user.getEmEmployee()!=null) {
			String emailTo = user.getEmEmployee().getEmail();
			FMProcesses process = this.fMProcessesServiceImpl.getFMProcessesByProcessCode("Workspace Management");
			String subject = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "meeting_reject_subject")
					.getMsgText() + " Id: " + savedRecord.getReserveId();
			String details = createMailContentForBooking(savedRecord, null,"requestor rejected");
			String regards = "<div>Regards,</br>FMS </div>";
			String reason = "<div style='margin-bottom:20px'><b>Reason</b> : " + savedRecord.getRejectionReason()
					+ "</div>  ";
			String content = details + reason + regards;
			String cc = "";
			Email mail = notifyController.prepareEmail(emailTo, cc, subject, content);
			try {
				notifyController.sendEmail(mail);
			} catch (Exception e) {
				String stacktrace = CommonUtil.getStakeTrace(e);
				logger.error("Exception in NotifyRequestor.notifyRejectBooking: "+stacktrace,e);
			}
			if(approverEmails != null) {
				List<String> otherApprovers = new ArrayList<>(approverEmails);
				User rejectedByUser = this.userService.getUser(savedRecord.getRejectedBy());
				if(rejectedByUser!=null && rejectedByUser.getEmEmployee()!=null) {
					String rejectedBy = savedRecord.getRejectedByUser().getEmEmployee().getEmail();
					otherApprovers.remove(rejectedBy);
					notifyToApprovers(savedRecord,otherApprovers,"approver already rejected");	
				}
			}
		}
	}

	public String attendeeMailContent(Reservation savedRecord, String action, List<Date> listAvalDates) {
		String details = "";
		String regards = "<div>Regards,</br>FMS </div>";
		String content = "";
		switch (action) {
		case "Added":
			details = createMailContentForBooking(savedRecord, null,"attendee invited");
			content =  details + regards;
			break;
		case "Removed":
			details = createMailContentForBooking(savedRecord, null,"attendee removed");
			content =  details + regards;
			break;
		case "Updated":
			details = createMailContentForBooking(savedRecord, null,"attendee invited");
			content =  details + regards;
			break;

		}
		return content;
	}

	public void notifyReservationUpdate(Reservation savedRecord, List<String> attendesEmails,
			List<Date> listAvalDates) {
		User user = this.userService.getUser(savedRecord.getRequestedBy());
		if(user.getEmEmployee()!=null) {
			String emailTo = user.getEmEmployee().getEmail();
			FMProcesses process = this.fMProcessesServiceImpl.getFMProcessesByProcessCode("Workspace Management");
			String subject = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "update_meeting_subject")
					.getMsgText() + " Id: " + savedRecord.getReserveId();
			String details = createMailContentForBooking(savedRecord, listAvalDates,"requestor updated");
			String regards = "<div>Regards,</br>FMS </div>";
			String content =  details + regards;
			String cc = "";
			Email mail = notifyController.prepareEmail(emailTo, cc, subject, content);
			
			try {
				notifyController.sendEmail(mail);
			}  catch (Exception e) {
				String stacktrace = CommonUtil.getStakeTrace(e);
				logger.error("Exception in NotifyRequestor.notifyReservationUpdate: "+stacktrace,e);
			}
			if(attendesEmails != null) { 
				if(attendesEmails.size()>0) {
					String subjectForAttendee = messagesService
							.getMessagesByProcessIdAndCode(process.getProcessId(), "update_meeting_subject_attendee").getMsgText() + " Id: "
							+ savedRecord.getReserveId();
					String attendeeContent = createMailContentForBooking(savedRecord,listAvalDates,"attendee invited");
					attendesEmails.forEach(attendee -> {
						sendAttendeesEmail(attendee, subjectForAttendee, attendeeContent);
					});
				}
				
			}
		}
	}

	public void createMailNotificationforBooking(Reservation oldRecord, Reservation savedRecord,
			List<String> oldAttendesEmails, List<String> newAttendesEmails, List<Date> listAvalDates, boolean isEdit,
			Boolean isDateOrTimeChanged, List<String> approverEmails) {
		FMProcesses process = this.fMProcessesServiceImpl.getFMProcessesByProcessCode("Workspace Management");
		String newStatus = savedRecord.getStatus();
		if (isEdit) {//checks either new or old record
			String oldStatus = oldRecord.getStatus();
			if (!oldStatus.equalsIgnoreCase(newStatus)) {//checks if status changed
				switch (newStatus) {
				case "Approved":
					notifyReservationSuccess(savedRecord, newAttendesEmails, listAvalDates ,approverEmails);
					break;
				case "Rejected":
					notifyRejectBooking(savedRecord,approverEmails);
					break;
				case "Cancelled":
					if(oldStatus.equalsIgnoreCase("Waiting for Approval")) {
						notifyCancelBooking(savedRecord, null,approverEmails);
						break;
					}
					else if (oldStatus.equalsIgnoreCase("Approved")) {
						notifyCancelBooking(savedRecord, newAttendesEmails, null);
						break;
					}
					
				}
			} 
			//checks when date or times not changed and status is not Waiting for Approval
			else if (isDateOrTimeChanged != null && !isDateOrTimeChanged && !newStatus.equals("Waiting for Approval")) {
				//checks attendees are changed or not
				if(!newAttendesEmails.equals(oldAttendesEmails)) {
				List<String>copyOfNew = new ArrayList<String>(newAttendesEmails);
					newAttendesEmails.removeAll(oldAttendesEmails);
					oldAttendesEmails.removeAll(copyOfNew);
					if(newAttendesEmails.size()>0) {
						/// send reservation success for new attendees
						String subjectForAttendee = messagesService
								.getMessagesByProcessIdAndCode(process.getProcessId(), "approved_meeting_sub_attendee")
								.getMsgText() + " Id: " + savedRecord.getReserveId();
						String content = attendeeMailContent(savedRecord, "Added", listAvalDates);
						newAttendesEmails.forEach(attendee -> {
							sendAttendeesEmail(attendee, subjectForAttendee, content);
						});
					}
					if(oldAttendesEmails.size()>0) {
						/// send removed for attendees
						String subjectForAttendee = messagesService
								.getMessagesByProcessIdAndCode(process.getProcessId(), "update_meeting_subject_attendee")
								.getMsgText() + " Id: " + savedRecord.getReserveId();
						String content = attendeeMailContent(savedRecord, "Removed", listAvalDates);
						oldAttendesEmails.forEach(attendee -> {
							sendAttendeesEmail(attendee, subjectForAttendee, content);
						});
					}
				}
			}
			//checks when date or times  changed  
			else if (isDateOrTimeChanged != null  && isDateOrTimeChanged) {
				//checks status is not Waiting for Approval
				if (!newStatus.equals("Waiting for Approval")) {
					if(!newAttendesEmails.equals(oldAttendesEmails)) {
						List<String>copyOfNew = new ArrayList<String>(newAttendesEmails);
						List<String>copyOfOld = new ArrayList<String>(oldAttendesEmails);
						newAttendesEmails.removeAll(oldAttendesEmails);
						oldAttendesEmails.removeAll(copyOfNew);
						if(newAttendesEmails.size()>0) {
							/// send reservation success for new attendees
							String subjectForAttendee = messagesService
									.getMessagesByProcessIdAndCode(process.getProcessId(), "approved_meeting_sub_attendee")
									.getMsgText() + " Id: " + savedRecord.getReserveId();
							String content = attendeeMailContent(savedRecord, "Added", listAvalDates);
							newAttendesEmails.forEach(attendee -> {
								sendAttendeesEmail(attendee, subjectForAttendee, content);
							});
						}
						if(oldAttendesEmails.size()>0) {
							/// send removed for attendees
							String subjectForAttendee = messagesService
									.getMessagesByProcessIdAndCode(process.getProcessId(), "update_meeting_subject_attendee")
									.getMsgText() + " Id: " + savedRecord.getReserveId();
							String content = attendeeMailContent(savedRecord, "Removed", listAvalDates);
							oldAttendesEmails.forEach(attendee -> {
								sendAttendeesEmail(attendee, subjectForAttendee, content);
							});
						}
						copyOfOld.removeAll(oldAttendesEmails);
						// old attendees send meeting update mail
						// requestor send meeting update mail
						notifyReservationUpdate(savedRecord, copyOfOld, listAvalDates);
					}
					else {
						// requestor send meeting update mail
						// old attendees send meeting update mail
						notifyReservationUpdate(savedRecord, newAttendesEmails, listAvalDates);
					}
				}
				else {
					notifyReservationUpdate(savedRecord, null, listAvalDates);
					notifyApprovalReminder(savedRecord,approverEmails ,null,"(updated)");
				}
			}
		} 
		else {
			if (newStatus.equals("Approved")) {
				notifyReservationSuccess(savedRecord, newAttendesEmails, listAvalDates , null);
			} else {
				notifyApprovalReminder(savedRecord,approverEmails,null,"");
				notifyRequestorWaitingForApproval(savedRecord,listAvalDates);
			}
		}
	}
	
	public void notifyRequestorWaitingForApproval(Reservation savedRecord,List<Date> listAvalDates) {
		User user = this.userService.getUser(savedRecord.getRequestedBy());
		if(user.getEmEmployee()!=null) {
			FMProcesses process = this.fMProcessesServiceImpl.getFMProcessesByProcessCode("Workspace Management");
			String emailTo = user.getEmEmployee().getEmail();
			String subject = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "approve_reminder_subject")
					.getMsgText() + " Id: " + savedRecord.getReserveId();
			String details = createMailContentForBooking(savedRecord, listAvalDates,"requestor waiting for approval");
			String regards = "<div>Regards,</br>FMS </div>";
			String content =  details + regards;
			String cc = "";
			Email mail = notifyController.prepareEmail(emailTo, cc, subject, content);
			try {
				notifyController.sendEmail(mail);
			}  catch (Exception e) {
				String stacktrace = CommonUtil.getStakeTrace(e);
				logger.error("Exception in NotifyRequestor.notifyRequestorWaitingForApproval: "+stacktrace,e);
			}
		}
	}
	
	public void sendApproverEmail(String Email, String sub, String cont) {
		String emailTo = Email;
		String subject = sub;
		String content = cont;
		String cc = "";
		Email mail = notifyController.prepareEmail(emailTo, cc, subject, content);
		try {
			notifyController.sendEmail(mail);
		} catch (Exception e) {
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in NotifyRequestor.sendApproverEmail: "+stacktrace,e);
		}

	}
	
	public void notifyToApprovers(Reservation savedRecord , List<String> approverEmails , String type) {
		FMProcesses process = this.fMProcessesServiceImpl.getFMProcessesByProcessCode("Workspace Management");
		String subject = messagesService.getMessagesByProcessIdAndCode(process.getProcessId(), "approver_action_already_done_subject")
				.getMsgText() + " Id :" + savedRecord.getReserveId() ;
		String details = createMailContentForBooking(savedRecord, null ,type);
		String reason = ""; 
		if(type.equals("cancelled")) {
			reason = "<div style='margin-bottom:20px'><b>Reason</b> : " + savedRecord.getCancelledReason() + "</div>  ";
		}
		else if (type.equals("approver already rejected")) {
			reason = "<div style='margin-bottom:20px'><b>Reason</b> : " + savedRecord.getRejectionReason() + "</div>  ";
		}
				
		String regards = "<div>Regards,</br>FMS </div>";
		String content = details + reason + regards;
		approverEmails.forEach( each -> {
				String email = each ;
				sendApproverEmail(email,subject,content);
		});
	}
	
}
