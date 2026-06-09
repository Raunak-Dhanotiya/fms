package com.fms.app.spaceManagement.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.spaceManagement.models.RmTrans;
import com.fms.app.spaceManagement.repository.RmTransRepository;

@Service
public class RmTransService {
	@Autowired
	RmTransRepository rmtransrepo;
	
	@Autowired
	AuthorizeUserInfo userInfo;

	public List<RmTrans> getRmTransByTermId(int termId){
		List<RmTrans> allData = this.rmtransrepo.findAll();
		List<RmTrans> resData = allData.stream().filter(each -> each.getTermId()==termId).collect(Collectors.toList());
		return resData;
	}
	
	public RmTrans savermtrans(RmTrans rmtrans)   
	{  
		RmTrans r= this.rmtransrepo.save(rmtrans);
		return r;
	}
	
	public List<RmTrans> getAllRmTrans(){
		List<RmTrans> trans = new ArrayList<RmTrans>(); 
		this.rmtransrepo.findAll();  
		return trans;  
	}
}
