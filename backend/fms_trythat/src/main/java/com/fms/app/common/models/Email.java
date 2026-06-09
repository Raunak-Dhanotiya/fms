package com.fms.app.common.models;

import java.util.List;

public class Email {

	private String emailTo;
	private String emailCC;
	private String mailSubject;
	private String mailContent;
	private List<String> emailCCMultiple;
	private List<String> emailToMultiple;

	/**
	 * @return the emailTo
	 */
	public String getEmailTo() {
		return emailTo;
	}

	/**
	 * @param emailTo the emailTo to set
	 */
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	/**
	 * @return the emailCC
	 */
	public String getEmailCC() {
		return emailCC;
	}

	/**
	 * @param emailCC the emailCC to set
	 */
	public void setEmailCC(String emailCC) {
		this.emailCC = emailCC;
	}

	/**
	 * @return the mailSubject
	 */
	public String getMailSubject() {
		return mailSubject;
	}

	/**
	 * @param mailSubject the mailSubject to set
	 */
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	/**
	 * @return the mailContent
	 */
	public String getMailContent() {
		return mailContent;
	}

	/**
	 * @param mailContent the mailContent to set
	 */
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public Email() {

	}
	
	
	public Email(String emailTo, String emailCC, String mailSubject, String mailContent, List<String> emailCCMultiple) {
		super();
		this.emailTo = emailTo;
		this.emailCC = emailCC;
		this.mailSubject = mailSubject;
		this.mailContent = mailContent;
		this.emailCCMultiple = emailCCMultiple;
	}

	public List<String> getEmailCCMultiple() {
		return emailCCMultiple;
	}

	public void setEmailCCMultiple(List<String> emailCCMultiple) {
		this.emailCCMultiple = emailCCMultiple;
	}

	public List<String> getEmailToMultiple() {
		return emailToMultiple;
	}

	public void setEmailToMultiple(List<String> emailToMultiple) {
		this.emailToMultiple = emailToMultiple;
	}

	

}
