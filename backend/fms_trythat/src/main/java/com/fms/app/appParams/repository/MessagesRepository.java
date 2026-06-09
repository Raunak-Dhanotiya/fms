package com.fms.app.appParams.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fms.app.appParams.models.Messages;

public interface MessagesRepository extends JpaRepository<Messages,Integer>,JpaSpecificationExecutor<Messages>{
	
	boolean existsByMsgId(int msgId );
	
	Messages findByProcessIdAndMsgCode(Integer processId,String msgCode);
	
	@Modifying
	@Query("INSERT INTO Messages(processId,msgId,msgText) "
			+ "SELECT processId,msgId,msgText FROM Messages")
	void insertData();
}
