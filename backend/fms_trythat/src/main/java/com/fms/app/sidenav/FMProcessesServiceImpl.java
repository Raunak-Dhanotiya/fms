package com.fms.app.sidenav;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FMProcessesServiceImpl implements IFMProcessesService {

	@Autowired
	IFMProcessesRepository repository;
	@Override
	public List<FMProcesses> getFMProcesses() {
		return this.repository.findAll();
	}

	@Override
	public FMProcesses getFMProcesses(String processId) {
		// TODO Auto-generated method stub
		return this.repository.findByProcessId(processId);
	}

	@Override
	public void saveFMProcesses(FMProcesses record) {
		this.repository.save(record);

	}

	@Override
	public void delete(String processId) {
		this.repository.deleteByProcessId(processId);

	}

	@Override
	public void delete(FMProcesses record) {
		this.repository.delete(record);
	}
	
	@Override
	public FMProcesses getFMProcessesByProcessCode(String processCode) {
		// TODO Auto-generated method stub
		return this.repository.findByProcessCode(processCode);
	}

}
