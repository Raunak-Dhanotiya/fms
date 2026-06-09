package com.fms.app.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.helpdesk.models.ProblemType;

public interface ProblemTypeRepository  extends JpaRepository<ProblemType,Integer>{

	List<ProblemType> findByParentId(int parentId);

	List<ProblemType> findByParentIdIsNull();

	ProblemType findByProblemTypeId(int problemType);

	ProblemType findByProbType(String probType);

}
