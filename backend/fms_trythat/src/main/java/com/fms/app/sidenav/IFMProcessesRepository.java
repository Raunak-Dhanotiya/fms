package com.fms.app.sidenav;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFMProcessesRepository extends JpaRepository<FMProcesses, Long> {

	public FMProcesses findByProcessId(String processId);

	public void deleteByProcessId(String processId);

	public FMProcesses findByProcessCode(String processCode);
}
