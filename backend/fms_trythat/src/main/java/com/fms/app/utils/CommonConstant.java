package com.fms.app.utils;

import java.util.Arrays;
import java.util.List;

public final class CommonConstant {

	public static final String BUCKET_PATH = "fms-dir";
	public static final String SUCCESS_MSG = "Record saved successfully.";
	public static final String SUCCESS_GET_RECORDS = "All Records fetched successfully.";
	public static final String UNABLE_TO_PROCESS_MSG = "Unable to process the record";
	public static final String UNABLE_TO_PROCESS_BOOKING = "Booking already exists";
	public static final String UNABLE_TO_PROCESS_SLA = "SLA already exists";
	public static final String UNABLE_TO_PROCESS_SLA_STEPS = "SLA step is already exists";

	public static final int CLIENT_STATUS = 19;

	// Mail Constant
	public static final String MAIL_RESET_SUBJECT = "FMS Password reset";
	public static final String MAIL_ACK_USER = "Dear #user#,<br>";
	public static final String MAIL_FROM = "<br><br><div> Regards,</div><div>FMS</div>";
	public static final String MAIL_RESET_BODY = "You recently requested for a password reset from your account. "
			+ "You can find the below link that will be active for 24 hrs<br>";
	public static final String MAIL_TERMS = "<br>If it wasn't you who requested for a password reset, or accidentally made a request, ignore this email. "
			+ "Worry not, your password won't be changed unless initiated by you.<br>";
	
	public static final String MAIL_REQUEST_SUBJECT = "Work Request Created";
	public static final String MAIL_Notify_BODY = "<br>The work request #requestCode# details<br><br>"
			+"<table>"
			+ "<tr><td><b>Problem Type:</b>  </td><td> #probType# </td></tr>"
			+ "<tr><td><b>Sub Problem Type:</b> </td><td> #subProbType# </td></tr>"
			+ "<tr><td><b>Priority:</b> </td><td> #priority# </td></tr>"
			+ "<tr><td><b>Date Requested:</b> </td><td> #dateReq# </td></tr>"
			+ "<tr><td><b>Assigned To:</b> </td><td> #assignTo# </td></tr>"
			+ "<tr><td><b>Response By Date:</b> </td><td> #dateRespBy# </td></tr>"
			+ "<tr><td><b>Complete By Date:</b> </td><td> #dateCompBy# </td></tr>"
			+ "<tr><td><b>Date Completed:</b> </td><td> #dateComp# </td></tr>"
			+ "<tr><td><b>Status:</b> </td><td> #status# </td></tr></table>";
	
	public static final String new_request_body = "<br>On <b> #dateLogged# </b> you contacted our Technical Support Department to log a request, <br>"
			+ "which has now been issued the reference number <b> #requestCode# </b> <br><br>"
			+ "<p>We are currently looking into resolving this request for you and will be in contact shortly.<br />\r\n" + 
			"   <br />"
			+ "In the mean time please click on this <a href=\"http://localhost:9090/request-details\">link</a> "
			+ " to access our Online Customer Support Helpdesk, where you can view, add or upload any comments, actions or "
			+ "documents which may relate to this recent request. </p><br/>"
			+ "Thank you for your patience in this matter.";
	
	public static final String complet_request_body = "<br>On <b> #dateLogged# </b> you contacted our technical support department <br>"
			+ "and were issued the reference number <b> #requestCode# </b><br>"
			+ "<p>We hope that this request has now been completed to your satisfaction.<br />\r\n" + 
			"   <br />"
			+ "We are constantly striving to improve our service performance and your opinion is greatly appreciated.";
	
	public static final String limitExceed_request_body = "Dear <b>#compName#</b> <br/>  <br/>  #msg# <br/>  <br/> #regards#" ;


	// Table Constant

	public static final String TABLE_SW_TYPE = "sw_type";
	public static final String TABLE_WR_TYPE = "wr_type";
	public static final String TABLE_AC_PSR_COST = "ac_psr_cost";
	public static final String TABLE_AC_PO = "ac_po";
	public static final String TABLE_HELPDESK_REQUEST = "helpdesk_request";

	// Field Constant

	public static final String FIELD_STATUS = "status";

	// KEY Constant

	public static final String KEY_ACTIVE = "active";

	public static final String HELPDESK_COMP_STATUS = "Completed";
			
	public static final String DEFAULT_ROLE = "Admin";

	public static final List<Integer> DEFAULT_SCREENS_NUM = Arrays.asList(1, 2, 10);

	public static final String DEFAUL_PWD = "Fms!2#";

	public static final String WITHIN_WEEK = "Within Week";

	public static final String WITHIN_2WEEK = "Within 2 Weeks";

	public static final String WITHIN_MONTH = "Within Month";
	
	//PDF Constant 
	
	public static final String FILE_NAME = "/userPlanReport";
	public static final String IMAGE_PATH = "src\\main\\resources\\assets\\Images\\fms-side-nav-logo.png";
	public static final String PDF_TITLE = "Monthly Plan Report";
	public static final String PDF_COMP_NAME_HEAD = "Company Name";
	public static final String PDF_DATE_START_HEAD = "Date Started";
	public static final String PDF_PLAN_HEAD = "Plan";
	public static final String PDF_NUM_OF_USERS_HEAD = "Number of Users";
	public static final String PDF_TOTAL_REQSTS_HEAD = "Number Requests For Plan";
	public static final String PDF_AVALB_REQSTS_HEAD = "Available Requests";
	public static final String PDF_MODULE_HEAD = "Module";
	public static final String PDF_NUM_OF_REQSTS_CREATED_HEAD = "Number of Requests Created";
	public static final String PDF_TOTAL_COUNT_HEAD = "Total Requests Created";
	public static final String PDF_MSG ="According to the current plan the number of records created are exceeded by ";
	public static final String PDF_COST_FOR_ADD_REQST = "Cost For Additional Request";
	public static final String PDF_ADD_COST = "Additional Cost";
	public static final String EURO_SYMBOL  = "\u00A3";
	public static final String FILE_NAME1 = "/monthlybookingReport";
	public static final String PDF_TITLE1 = "Monthly Booking Report";
	//If Available Requests < 0 
	public static final String  PLAN_LIMIT_EXHAUSTED_MSG ="According to the current plan the number of records created are exceeded.";
	public static final String PLAN_LIMIT_Warning_MSG = PLAN_LIMIT_EXHAUSTED_MSG + "<br/>" +
			                                         "Current Plan moved to 'pay as you go' for the current month.";
	public static final String MAIL_SENT_SUCCESSFULLY = "Mail sent successfully";
	public static final String MAIL_NOT_SENT = "Mail not sent";
	public static final String REQUEST_GENERATED = "Request Generated with Id:";
	public static final String REQUEST_UPDATED = "Updated";
	public static final String REQUEST_PENDING_APPROVAL = "Approval pending for Request:";
	public static final String REQUEST_ASSIGNED = "Request Assigned with Id:";
	public static final String PREVENTIVE_MAINTENANCE = "Preventive Maintenance";
	public static final String SOMETHING_WENT_WRONG = "Something went wrong";

}