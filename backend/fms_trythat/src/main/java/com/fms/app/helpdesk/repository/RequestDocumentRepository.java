package com.fms.app.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.helpdesk.models.RequestDocuments;

public interface RequestDocumentRepository extends JpaRepository<RequestDocuments, Integer> {

	List<RequestDocuments> findByRequestId(int requestId);

}
