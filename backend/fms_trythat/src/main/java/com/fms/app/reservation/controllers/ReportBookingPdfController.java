package com.fms.app.reservation.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fms.app.reservation.models.dto.ReportBookingDto;
import com.fms.app.reservation.services.ReportBookingPdfService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@RestController
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class ReportBookingPdfController {
	@Autowired
	ReportBookingPdfService reportBookingSer;
	
	private static final Logger logger = LogManager.getLogger(ReportBookingPdfController.class);

	@RequestMapping(value = "/booking/printPdf", method = RequestMethod.POST, produces = "application/pdf")
	public ResponseEntity<byte[]> printPdf(@RequestBody ReportBookingDto dataRecord) {

		try {
			byte[] doc = this.reportBookingSer.generatePdf(dataRecord);

			return new ResponseEntity<>(doc, HttpStatus.OK);
		}catch (Exception e) {
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in ReportBookingPdfController.printPdf: "+stacktrace,e);
			return null;
		}

	}
}
