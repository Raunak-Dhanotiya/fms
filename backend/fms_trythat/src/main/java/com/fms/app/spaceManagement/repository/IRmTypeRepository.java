package com.fms.app.spaceManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.fms.app.spaceManagement.models.RmType;

@Repository
public interface IRmTypeRepository extends JpaRepository<RmType, Integer>, JpaSpecificationExecutor<RmType> {
	
	public RmType findByRmType(String rmType);
	
	public List<RmType> findByRmtypeIdAndRmcatId(int rmtypeId, int rmcatId);
	
	public Optional<RmType> findByRmtypeId(int rmtypeId);

	public void deleteByRmtypeIdAndRmcatId(int rmtypeId, int rmcatId);
	boolean existsByRmtypeIdAndRmcatId(int rmtypeId, int rmcatId);

}
