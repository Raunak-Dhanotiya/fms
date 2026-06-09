package com.fms.app.sidenav;

import java.util.List;

public interface IFMProcessesService {

	public List<FMProcesses> getFMProcesses();

	public FMProcesses getFMProcesses(String processId);

	public void saveFMProcesses(FMProcesses record);

	public void delete(String processId);

	public void delete(FMProcesses record);

	FMProcesses getFMProcessesByProcessCode(String processCode);
}
