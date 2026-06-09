package com.fms.app.sidenav;

import java.util.List;

public interface IFMSubProcessesService {

	public List<FMSubProcess> getFMSubProcess();
	public List<FMSubProcess> getFMSubProcessByProcessId(int processId);
}
