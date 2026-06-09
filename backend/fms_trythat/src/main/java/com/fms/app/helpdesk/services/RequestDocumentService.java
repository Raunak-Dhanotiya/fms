package com.fms.app.helpdesk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.RequestDocuments;
import com.fms.app.helpdesk.repository.RequestDocumentRepository;

@Service
public class RequestDocumentService {

	@Autowired
	RequestDocumentRepository requestDocumentRepo;

	public void saveDocuments(RequestDocuments data) {

		this.requestDocumentRepo.save(data);

	}

	public List<RequestDocuments> getDocumentsByRequest(int requestId) {

		List<RequestDocuments> requestDocuments = this.requestDocumentRepo.findByRequestId(requestId);
		return requestDocuments;
	}

	public void deleteById(int id) {
		this.requestDocumentRepo.deleteById(id);
	}

}
