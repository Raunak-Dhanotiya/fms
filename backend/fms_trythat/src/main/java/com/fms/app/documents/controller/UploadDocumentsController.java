package com.fms.app.documents.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fms.app.Equipment.models.Equipment;
import com.fms.app.EquipmentService.EquipmentService;
import com.fms.app.documents.models.dto.DocumentDetails;
import com.fms.app.documents.services.UploadDocumentsServices;
import com.fms.app.helpdesk.models.Wr;
import com.fms.app.helpdesk.services.WrServices;
import com.fms.app.ppm.models.Plan;
import com.fms.app.ppm.models.PlanStep;
import com.fms.app.ppm.services.PlanServices;
import com.fms.app.ppm.services.PlanStepServices;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.utils.CommonConstant;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@RestController
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class UploadDocumentsController {

	@Autowired
	UploadDocumentsServices uploadDocumentsServices;
	
    @Autowired
    Environment env;
    
	@Autowired
	AuthorizeUserInfo userInfo;
	
	@Autowired
	WrServices wrService;
	
	@Autowired
	EquipmentService eqService;
	
	@Autowired
	PlanServices planService;
	
	@Autowired
	PlanStepServices planStepService;
	
	private static final Logger logger = LogManager.getLogger(UploadDocumentsController.class);

	@RequestMapping(value = "/documents/upload", method = RequestMethod.POST, produces = "application/json")
	private ResponseEntity<Object> saveDocuments(@RequestParam(name = "document") MultipartFile document,
							@RequestParam(name = "documemtType") String documemtType,@RequestParam(name = "documentName") String documentName,
							@RequestParam(name = "tableName") String tableName, @RequestParam(name = "fieldName") String fieldName,
							@RequestParam(name = "docBucketId") int docBucketId, @RequestParam(name = "notes") String notes,
							@RequestParam(name = "docType") String docType,@RequestParam(name = "pkValue") String pkValue,
							@RequestParam(name = "pkField") String pkField)
	{
		
		String userName = this.userInfo.getUserIfo().getUsername();
		int UserId = this.userInfo.getUserIfo().getUserId();
		String filePath = "";
		String rootDir = System.getProperty("catalina.base");//use while build
		String webAppPath = String.format("%s/webapps", rootDir);
		if(env.getProperty("spring.profiles.active").equals("dev")) {
			 webAppPath = System.getProperty("user.dir");// for local testing
		}
		
		String[] dateFolderArr = this.uploadDocumentsServices.createFolderNamesWithDate();
		String year = dateFolderArr[0];
		String month = dateFolderArr[1];
		String date = dateFolderArr[2];
		String path = year + File.separator + month + File.separator + date + File.separator + userName
				+ File.separator;
		filePath = webAppPath + File.separator + CommonConstant.BUCKET_PATH + File.separator + path.toLowerCase();
		if (docBucketId == 0) {
			docBucketId = this.uploadDocumentsServices.saveDocumentDetails(tableName, fieldName, filePath ,pkValue,pkField);
		}

		int docBucketItemId = this.uploadDocumentsServices.saveDocBucketItem(docBucketId, documentName, documemtType,
				UserId, notes,docType);
		filePath = filePath + File.separator + Integer.toString(docBucketItemId) + "." + documemtType;
		File file = new File(filePath);

		try {
			Files.createDirectories(Paths.get(filePath));
			document.transferTo(file);
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UploadDocumentsController.saveDocuments: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ResponseUtil(Integer.toString(docBucketId), HttpStatus.OK.value()),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/documents/download/{docBucketItemId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getDocumentById(@PathVariable("docBucketItemId") int docBucketItemId) {
		
		try {
			String fileContent = this.uploadDocumentsServices.getDocumentContentById(docBucketItemId);
			if (fileContent == null) {
				return new ResponseEntity<>(new ResponseUtil("file not exists", HttpStatus.BAD_GATEWAY.value()),
						HttpStatus.BAD_GATEWAY);
			}

			return new ResponseEntity<>(new ResponseUtil(fileContent, HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UploadDocumentsController.getDocumentById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/documents/delete", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> deleteDocumentById(@RequestBody DocumentDetails docDetails) {
		try {
			this.uploadDocumentsServices.deleteDocBucketItem(docDetails);
			return new ResponseEntity<>(new ResponseUtil("Document Deleted", HttpStatus.OK.value()), HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UploadDocumentsController.deleteDocumentById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/documents/getAll/{docBucketId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllById(@PathVariable("docBucketId") int docBucketId) {
		
		try {
			List<DocumentDetails> docsList = this.uploadDocumentsServices.getAllDocuments(docBucketId);
			return new ResponseEntity<>(docsList, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UploadDocumentsController.getAllById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}

	}
	
	@RequestMapping(value = "/documents/getAll/{requestId}/{docBucketId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllByRequestIdAndDocBucketId(@PathVariable("requestId") int requestId,@PathVariable("docBucketId") int docBucketId) {
		try {
			List<DocumentDetails> docsList = new ArrayList<DocumentDetails>();
			Map<String, Object> docObjects = new HashMap<String, Object>();
			if(docBucketId != 0) {
				 docsList = this.uploadDocumentsServices.getAllDocuments(docBucketId);
				 docObjects.put("wrDocs", docsList);
			}
			Wr request = this.wrService.getWrId(requestId);
			if(request.getEq() != null) {
				Equipment eq = this.eqService.getEquipmentByEqId(request.getEqId());
				if(eq.getDocBucketId() != null) {
					List<DocumentDetails> eqDocsList = this.uploadDocumentsServices.getAllDocuments(eq.getDocBucketId());
					docObjects.put("eqDocs", eqDocsList);
				}
				
			}
			
			if(request.getPlanId() != null) {
				Plan plan = this.planService.getPlanById(request.getPlanId());
				PlanStep planStep = this.planStepService.getAllByPlanId(plan.getPlanId()).get(0);
				if(planStep.getDocBucketId() != null) {
					List<DocumentDetails> planDocsList = this.uploadDocumentsServices.getAllDocuments(planStep.getDocBucketId());
					docObjects.put("planDocs", planDocsList);
				}
				
			}
			
			return new ResponseEntity<>(docObjects, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in UploadDocumentsController.getAllByRequestIdAndDocBucketId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}

	}
}
