package com.fms.app.documents.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fms.app.documents.models.Document;
import com.fms.app.documents.models.dto.DocumentRequestDTO;
import com.fms.app.documents.services.DocumentServiceImpl;
import com.fms.app.employee.models.Employee;
import com.fms.app.employee.services.EmployeeService;
import com.fms.app.reservation.models.Visitors;
import com.fms.app.reservation.services.VisitorsService;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.Rm;
import com.fms.app.spaceManagement.models.Site;
import com.fms.app.spaceManagement.services.BlService;
import com.fms.app.spaceManagement.services.FlService;
import com.fms.app.spaceManagement.services.RmService;
import com.fms.app.spaceManagement.services.SiteService;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1/doc")
public class DocumentController {

	@Autowired
	DocumentServiceImpl docService;
	@Autowired
	EmployeeService employeeService;

	@Autowired
	SiteService siteService;
	
	@Autowired
	BlService blService;
	
	@Autowired
	RmService rmService;
	
	@Autowired  
	VisitorsService visitorService;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	@Autowired  
	FlService flService;
	
	private static final Logger logger = LogManager.getLogger(DocumentController.class);

	@PostMapping("/upload")
	public ResponseEntity<Object> uplaodImage(
			@RequestParam(name = "imageFile", required = false) MultipartFile imageFile,
			@RequestParam(name = "tableName", required = false) String tableName,
			@RequestParam(name = "fieldName", required = false) String fieldName,
			@RequestParam(name = "pkeyValue", required = false) String pkeyValue)
			throws IOException {

		try {
			if (!imageFile.isEmpty()) {
				Document docRecord = new Document();
				String extention = imageFile.getOriginalFilename().split("\\.")[1];
				docRecord.setFileContent(CommonUtil.compressBytes(imageFile.getBytes()));
				docRecord.setFileType(extention);
				docRecord.setTableName(tableName);
				docRecord.setFieldName(fieldName);
				docRecord.setFileName(imageFile.getOriginalFilename());
				docRecord.setPkeyValue(pkeyValue);
				Calendar c = Calendar.getInstance();
				docRecord.setDateCreated(c.getTime());

				Document rec = docService.saveDocument(docRecord);
				if (tableName.equals("em")) {
					Employee empRecord = this.employeeService.getEmployeeByID(Integer.parseInt(pkeyValue));
					empRecord.setEmPhoto(rec.getDocId());
					this.employeeService.saveEmployee(empRecord);
					return new ResponseEntity<>(new ResponseUtil("Record inserted", 200), HttpStatus.OK);
				}else if( tableName.equals("site") ) {
					Site siteRecord = this.siteService.getSiteById(Integer.parseInt(pkeyValue));
					if(siteRecord != null) {
						siteRecord.setSitePhoto(rec.getDocId());
						this.siteService.saveOrUpdate(siteRecord);
					}
					return new ResponseEntity<>(new ResponseUtil("Record inserted", 200), HttpStatus.OK);
				}else if(tableName.equals("bl")) {
					Bl blRecord = this.blService.getBlById(Integer.parseInt(pkeyValue));
					if(blRecord != null) {
						blRecord.setBlPhoto(rec.getDocId());
						this.blService.saveOrUpdate(blRecord);
					}
					return new ResponseEntity<>(new ResponseUtil("Record inserted", 200), HttpStatus.OK);
				}else if(tableName.equals("rm")) {
					Rm rmRecord = this.rmService.getRmById(Integer.parseInt(pkeyValue));
					if(rmRecord != null) {
						rmRecord.setRmPhoto1(rec.getDocId());
						this.rmService.saveOrUpdate(rmRecord);
					}
					return new ResponseEntity<>(new ResponseUtil("Record inserted", 200), HttpStatus.OK);
				}else if (tableName.equals("visitors")) {
					Visitors visitor = this.visitorService.getVisitorsById(Integer.parseInt(pkeyValue));
					visitor.setPicture(rec.getDocId());
					this.visitorService.saveOrUpdate(visitor);
					return new ResponseEntity<>(new ResponseUtil("Record inserted", 200), HttpStatus.OK);
				}
				
			}
			return new ResponseEntity<>(new ResponseUtil("Unable to fetch the record", 400), HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DocumentController.uplaodImage: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	@GetMapping(path = { "/get/{imageName}" })
	public ResponseEntity<Document> getImage(@PathVariable("imageName") Integer imageName) {

		try {
			final Document retrievedImage = docService.getDocument(imageName);
			retrievedImage.setFileContent(CommonUtil.decompressBytes(retrievedImage.getFileContent()));
			return new ResponseEntity<>(retrievedImage, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DocumentController.getImage: "+stacktrace,e);
			return null;
		}
	}
	
	@RequestMapping(value = "/getDocumentsByRequest", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getDocumentsByReqId(@RequestBody DocumentRequestDTO reqData) {
		try {
			String tableName="", fieldName="", pkey="";
			if(reqData != null) {
				tableName = reqData.getTableName();
				fieldName = reqData.getFieldName();
				pkey = reqData.getPkeyValue();
			}
			List<Document> list = this.docService.getDocumentsByPkey(tableName, fieldName, pkey);
			List<Document> docList = new ArrayList<Document>();
			list.stream().forEach(data -> {
				Document doc = data;
				byte[] cont = doc.getFileContent() ;
				if(cont != null) {
					doc.setFileContent(CommonUtil.decompressBytes(doc.getFileContent()));
				}
				docList.add(doc);
			});
			
			return new ResponseEntity<>(docList, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DocumentController.getDocumentsByReqId: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/getCountByRequest", method = RequestMethod.GET, produces = "application/json")
	private ResponseEntity<Object> getCountByRequest()   
	{  
		try {
			List<Object[]> obj = this.docService.getCountByRequests();		
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DocumentController.getCountByRequest: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/deleteDocById/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> deleteDocById(@PathVariable("id") Integer id) {
		
		try {
			Document doc = this.docService.getDocument(id);
			this.docService.delete(id);
			//HelpdeskRequest req = this.helpdeskSevice.getRequestById(Integer.parseInt(doc.getPkeyValue()));
			int userId = this.userInfo.getUserIfo().getUserId();
			//this.statusLogController.saveStatusLog(req, "Attachment Deleted",userId);
			
			return new ResponseEntity<>(null, HttpStatus.OK);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DocumentController.deleteDocById: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}
	
	@PostMapping("/uploadDocument")
	public ResponseEntity<Object> uplaodDocument(
			@RequestParam(name = "imageFile", required = false) MultipartFile imageFile,
			@RequestParam(name = "tableName", required = false) String tableName,
			@RequestParam(name = "fieldName", required = false) String fieldName,
			@RequestParam(name = "pkeyValue", required = false) String pkeyValue)
			throws IOException {
		
		try {
			if (!imageFile.isEmpty()) {
				
				Calendar c = Calendar.getInstance();
				
				String rootDir= System.getProperty("user.dir");
				
				
				String timeStamp = c.get(Calendar.DATE) + "" + c.get(Calendar.MONTH) + c.get(Calendar.YEAR) ;
				timeStamp +=  "_" + c.get(Calendar.HOUR_OF_DAY) + "" + c.get(Calendar.MINUTE);  
				
				
				String dirPath = rootDir + "\\assets\\Documents\\";
				
				if(! new File(dirPath).exists())
	            {
	              Files.createDirectories(Paths.get(dirPath));
	            }
				
				String name = tableName + "_"  +  timeStamp + imageFile.getOriginalFilename() ; 
				
				File convertFile = new File(dirPath + name);
				convertFile.createNewFile();
				
				try (FileOutputStream fout = new FileOutputStream(convertFile)) {
					fout.write(imageFile.getBytes());
				}catch (Exception e) {
					e.printStackTrace();
				}
	            
				Document docRecord = new Document();
				String filePath = "assets\\Documents\\" + name ;
				String extention = imageFile.getOriginalFilename().split("\\.")[1];
				
				docRecord.setFileType(extention);
				docRecord.setTableName(tableName);
				docRecord.setFieldName(fieldName);
				docRecord.setFileName(imageFile.getOriginalFilename());
				docRecord.setPkeyValue(pkeyValue);
				docRecord.setFilePath(filePath);
				docRecord.setDateCreated(new Date());

				docService.saveDocument(docRecord);

				
			}
			return new ResponseEntity<>(new ResponseUtil("Unable to fetch the record", 400), HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in DocumentController.uplaodDocument: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
		
	}
	
	
}
