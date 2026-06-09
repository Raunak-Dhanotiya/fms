package com.fms.app.spaceManagement.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fms.app.spaceManagement.models.Fl;

@Repository
public interface FlRepository extends JpaRepository<Fl, Integer>, JpaSpecificationExecutor<Fl> {

	@Cacheable(value = "Ehcache_Cache_Config",key = "'IEmployeeRepository_findByFlId'+#flId")
	public Fl findByFlId(int flId);
	
	@Cacheable(value = "Ehcache_Cache_Config",key = "'FloorCache_FlRelation'+#blId+#flId")
	public List<Fl> findByBlIdAndFlId(int blId, int flId);

	public void deleteByFlIdAndBlId(int flId, int blId);
	
	public void deleteByFlId(int flId);
	
	@Cacheable(value = "Ehcache_Cache_Config",key = "'FloorCache_siteId'+#siteId")
	@Query("SELECT f FROM fl f join bl b on b.blId=f.blId join site s on s.siteId=b.siteId WHERE s.siteId = ?1")
	public List<Fl> getFlBySiteId(int siteId);
	
	boolean existsBySvgName(String svgName );
	
	public boolean existsByFlIdAndBlId(int flId, int blId);
	
	public boolean existsByFlCodeAndBlId(String flCode, int blId);
	
	public List<Fl> findBySvgName(String svgName);
	
	@Cacheable(value = "Ehcache_Cache_Config",key = "'FloorCache_fl_code'+#flCode")
	public List<Fl> findByFlCodeContainingOrFlNameContaining(String flCode, String flName);

}
