package com.fms.app.documents.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.documents.models.DocumentBucket;

public interface DocumentBucketRepository extends JpaRepository<DocumentBucket,Integer> {

}
