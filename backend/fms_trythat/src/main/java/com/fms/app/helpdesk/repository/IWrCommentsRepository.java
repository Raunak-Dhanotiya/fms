package com.fms.app.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fms.app.helpdesk.models.WrComments;

@Repository
public interface IWrCommentsRepository extends JpaRepository<WrComments, Integer>{

	List<WrComments> findByWrId(int wrId);

}
