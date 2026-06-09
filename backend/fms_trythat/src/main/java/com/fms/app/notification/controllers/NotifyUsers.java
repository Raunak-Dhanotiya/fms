package com.fms.app.notification.controllers;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.common.models.Email;
import com.fms.app.employee.models.Employee;
import com.fms.app.employee.services.EmployeeService;
import com.fms.app.response.input.UserPwdReset;
import com.fms.app.userModels.User;
import com.fms.app.userServices.UserServiceImpl;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@EnableAsync
@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class NotifyUsers {

	@Autowired
	JavaMailSender mailSender;
	@Autowired
	EmployeeService emService;
	@Autowired
	UserServiceImpl userService;
	
	private static final Logger logger = LogManager.getLogger(NotifyUsers.class);

	@RequestMapping(value = "/auth/forgetpwd", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> forgetPwd(@RequestBody UserPwdReset userDto) {
		if (userDto != null && userDto.getUserEmail() != null) {
			Employee empData = this.emService.getEmpByEmail(userDto.getUserEmail().trim());
			if (empData != null) {
				User userData = this.userService.getUserByEmId(empData.getEmId());
				if (userData != null && userData.getUserStatus() == "Active") {

					StringBuilder sb = new StringBuilder();

					sb.append(
							userDto.getUrl() + "?user=" + userData.getUserName() + "&key=" + this.createResetPwdLink());
					final String resetLink = "<a href='" + sb.toString() + "'>" + sb.toString() + "</a>";
					final String content = createMailBody(empData.getFirstName(), resetLink);
					Email mail = prepareEmail(userDto.getUserEmail(), "", CommonConstant.MAIL_RESET_SUBJECT, content);
					try {
						sendEmail(mail);
					} catch (MessagingException e) {
						String stacktrace = CommonUtil.getStakeTrace(e);
						logger.error("Exception in NotifyUsers.forgetPwd: "+stacktrace,e);
						return new ResponseEntity<>(
								new ResponseUtil("Email is not send. Please contact adminstrator for more details.",
										HttpStatus.NOT_FOUND.value()),
								HttpStatus.OK);
					}
					return new ResponseEntity<>(
							new ResponseUtil("We sent a reset password link to you mail", HttpStatus.OK.value()),
							HttpStatus.OK);
				}
			}

		}
		return new ResponseEntity<>(
				new ResponseUtil("We couldn't find an account associated with " + userDto.getUserEmail(),
						HttpStatus.NOT_FOUND.value()),
				HttpStatus.OK);

	}

	private String createMailBody(String name, String link) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div>").append(CommonConstant.MAIL_ACK_USER.replace("#user#", name)).append("</div>");
		sb.append("<div>").append(CommonConstant.MAIL_RESET_BODY).append("</div>").append("<br/	><div>").append(link)
				.append("</div>").append("<div>").append(CommonConstant.MAIL_TERMS).append("</div>").append("<div>")
				.append(CommonConstant.MAIL_FROM).append("</div>");

		return sb.toString();
	}

	private String createResetPwdLink() {
		String link = UUID.randomUUID().toString();
		link = link + "_" + new Date().getTime();
		return link;
	}

	Email prepareEmail(String userEmail, String cc, String subject, String content) {

		Email notify = new Email();
		notify.setEmailTo(userEmail);
		if (cc != "") {
			notify.setEmailCC(cc);
		}

		notify.setMailSubject(subject);
		notify.setMailContent(content);

		return notify;

	}

	Email prepareEmailListCC(String userEmail, List<String> cc, String subject, String content) {

		Email notify = new Email();
		notify.setEmailTo(userEmail);
		if (cc.size() > 1) {
			notify.setEmailCCMultiple(cc);
		} else if (cc.size() == 1) {
			String atnd = cc.get(0);
			notify.setEmailCC(atnd);
		}

		notify.setMailSubject(subject);
		notify.setMailContent(content);

		return notify;

	}
	
	Email prepareEmailListOfTo(List<String> userEmails, String cc, String subject, String content) {

		Email notify = new Email();
		notify.setEmailToMultiple(userEmails);
		if (cc!= "") {
			notify.setEmailCC(cc);
		} 

		notify.setMailSubject(subject);
		notify.setMailContent(content);

		return notify;

	}


	@Async
	void sendEmail(Email mail) throws MessagingException {
		// SimpleMailMessage msg = new SimpleMailMessage();
		MimeMessage msg = mailSender.createMimeMessage();

		// true = multipart message
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);

		if(mail.getEmailToMultiple() != null) {
			String[] multimail = new String[mail.getEmailToMultiple().size()];
			multimail = mail.getEmailToMultiple().toArray(multimail);
			helper.setTo(multimail);
			
		}
		if(mail.getEmailTo() != null) {
			helper.setTo(mail.getEmailTo());
		}

		if (mail.getEmailCCMultiple() != null) {
			String[] multimail = new String[mail.getEmailCCMultiple().size()];
			multimail = mail.getEmailCCMultiple().toArray(multimail);
			helper.setCc(multimail);
		}
		if (mail.getEmailCC() != null) {
			helper.setCc(mail.getEmailCC());
		}

		helper.setSubject(mail.getMailSubject());

		// true = text/html
		helper.setText(mail.getMailContent(), true);

		mailSender.send(msg);
	}
}
