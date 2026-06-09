package com.fms.app.helpdesk.controllers;

import java.sql.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.fms.app.helpdesk.models.RequestDocuments;
import com.fms.app.helpdesk.models.RequestDocumentsDTO;
import com.fms.app.helpdesk.services.RequestDocumentService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class RequestDocumentController {

	@Autowired
	RequestDocumentService requestDocumentService;
	
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(RequestDocumentController.class);

	@RequestMapping(value = "/requestDocuments/save", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveDocuments(@RequestParam(name = "document") MultipartFile document,
			@RequestParam(name = "requestId") int requestId, @RequestParam(name = "requestDocId") int requestDocId,
			@RequestParam(name = "documemtType") String documemtType,
			@RequestParam(name = "documentName") String documentName,
			@RequestParam(name = "docDescription") String docDescription

	) {
		try {
			if (!document.isEmpty()) {
				RequestDocuments docRecord = new RequestDocuments();

				docRecord.setDocumentContent(CommonUtil.compressBytes(document.getBytes()));
				docRecord.setRequestId(requestId);
				docRecord.setRequestDocId(requestDocId);
				docRecord.setDocType(documemtType);// file extension
				docRecord.setDocumentName(documentName);
				if(!docDescription.equals("null")) {
					docRecord.setDocDescription(docDescription);
				}
				
				// current date.
				java.util.Date currentDate = Calendar.getInstance().getTime();
				Date sqlDate = new Date(currentDate.getTime());
				docRecord.setDateAdded(sqlDate);

				this.requestDocumentService.saveDocuments(docRecord);
				return new ResponseEntity<>(new ResponseUtil("Data Record Saved", HttpStatus.OK.value()),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new ResponseUtil("Unable to save the request document",
						HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestDocumentController.saveDocuments: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/requestDocuments/getDocumentsByRequest/{requestId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getDocumentsByRequest(@PathVariable("requestId") int requestId) {
		try {

			List<RequestDocuments> requestDocuments = this.requestDocumentService.getDocumentsByRequest(requestId);

			List<RequestDocuments> docList = new ArrayList<RequestDocuments>();

			requestDocuments.stream().forEach(data -> {
				RequestDocuments doc = data;
				byte[] cont = doc.getDocumentContent();
				if (cont != null) {
					doc.setDocumentContent(CommonUtil.decompressBytes(doc.getDocumentContent()));
				}
				docList.add(doc);
			});
			
			List<RequestDocumentsDTO> data = docList.stream().map(each -> this.mapper.map(each, RequestDocumentsDTO.class))
					.collect(Collectors.toList());
			

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestDocumentController.getDocumentsByRequest: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/requestDocuments/deleteById/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteById(@PathVariable("id") int id) {
		try {
			this.requestDocumentService.deleteById(id);
			return new ResponseEntity<>(new ResponseUtil("Document deleted successfully", HttpStatus.OK.value()),
					HttpStatus.OK);

		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in RequestDocumentController.deleteById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
}
