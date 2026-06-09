package com.fms.app.sidenav;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FMSubProcessesService implements IFMSubProcessesService {

	@Autowired
	IFMSubProcessesRepository repository;
	
	@Override
	public List<FMSubProcess> getFMSubProcess() {
		return this.repository.findAll();
	}

	@Override
	public List<FMSubProcess> getFMSubProcessByProcessId(int processId) {
		// TODO Auto-generated method stub
		return this.repository.findByProcessId(processId);
	}
	
	public FMSubProcess getFMSubProcessById(int subProcessId) {
		// TODO Auto-generated method stub
		return this.repository.findBySubProcessId(subProcessId);
	}
}
