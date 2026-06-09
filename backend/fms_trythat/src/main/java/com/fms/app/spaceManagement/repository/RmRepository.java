package com.fms.app.spaceManagement.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fms.app.spaceManagement.models.Rm;

@Repository
public interface RmRepository extends JpaRepository<Rm, Integer>, JpaSpecificationExecutor<Rm> {

	@Cacheable(value = "Ehcache_Cache_Config",key = "'RoomCache_getRmByRmId'+#rmId")
	public Rm getRmByRmId(int rmId);
	
	public List<Rm> findByBlIdAndFlIdAndRmId(int blId, int flId, int rmId);
	
	public void deleteByBlIdAndFlIdAndRmId(int blId, int flId, int rmId);
	
	public void deleteByRmId(int rmId);
	
	@Cacheable(value = "Ehcache_Cache_Config",key = "'RoomCache_getRmByIds_'+#siteId")
	@Query("SELECT r FROM  rm r join fl f  on r.flId = f.flId and r.blId=f.blId join bl b on b.blId=f.blId join site s on s.siteId=b.siteId WHERE s.siteId = ?1")
	public List<Rm> getRmBySiteId(int siteId);

	@Cacheable(value = "Ehcache_Cache_Config",key = "'RoomCache_getByIsReservable_'+#isReservable")
	public List<Rm> getByIsReservable(int isReservable);
	
	public boolean existsByRmIdAndBlIdAndFlId(int id ,int blId,int flId);
	
	@Modifying
	@Query("update rm rm set rm.rmcatId=?1,rm.rmtypeId=?2,rm.divId=?3,rm.depId=?4 where rm.blId=?5 and rm.flId=?6 and rm.rmId=?7")
	void updatermprop(Integer rmcatId,Integer rmtypeId,Integer divId,Integer depId,int blId, int flId, int rmId);
	
	@Modifying
	@Query("update rm rm set rm.rmName=?1,rm.rmcatId=?2,rm.rmtypeId=?3,rm.rmArea=?4,rm.svgElementId=?5"
			+ ",rm.commonAreaType=?6 where rm.blId=?7 and rm.flId=?8 and rm.rmCode=?9")
	void udpateRoomViaAcad(String rmName, Integer rmcatId, Integer rmtypeId, Double rmArea,String svgElementId,
			String commonAreaType,int blId, int flId, String rmCode);
	
	@Cacheable(value = "Ehcache_Cache_Config",key = "'RoomCache_rm_code'+#rmCode")
	public List<Rm> findByRmCodeContainingOrRmNameContaining(String rmCode, String rmName);
	
	public Rm getRmByBlIdAndFlIdAndRmCode(Integer blId,Integer flId,String rmCode);
}
