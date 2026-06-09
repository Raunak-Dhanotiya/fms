package com.fms.app.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fms.app.spaceManagement.models.Ctry;
import com.fms.app.spaceManagement.repository.CtryRepository;
import com.fms.app.spaceManagement.services.ICtryService;
import com.fms.app.userModels.User;

public class CtryServiceImpl implements ICtryService{
	
	@Autowired
	CtryRepository ctryRepository;
	
	@Override
	public Ctry getCtry(Integer ctry_id) {
		return this.ctryRepository.findById(ctry_id).orElse(null);
	}
	
	public List<Ctry> getAllCtry(){
		return this.ctryRepository.findAll();
	}

}
