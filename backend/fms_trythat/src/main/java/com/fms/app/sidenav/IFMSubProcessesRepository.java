package com.fms.app.sidenav;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IFMSubProcessesRepository extends JpaRepository<FMSubProcess, Integer>   {

	public FMSubProcess findBySubProcessId(int subProcessId);

	public void deleteBySubProcessId(int subProcessId);
	
	@Query("SELECT e FROM FMSubProcess e WHERE e.processId = ?1 ORDER BY e.processId DESC")
	public List<FMSubProcess> findByProcessId(int processId);
}
