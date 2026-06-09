package com.fms.app.documents.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.documents.models.DocumentBucketItems;

public interface DocumentBucketItemsRepository extends JpaRepository<DocumentBucketItems,Integer> {

	List<DocumentBucketItems> findByDocBucketId(int docBucketId);

}
