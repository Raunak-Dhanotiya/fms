package com.fms.app.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.fms.app.helpdesk.models.Wr;

@Repository
public interface IWrRepository extends JpaRepository<Wr, Integer>, JpaSpecificationExecutor<Wr>
{

	boolean existsByWrId(int wrId);

	Wr findByWrId(int wrId);
}

