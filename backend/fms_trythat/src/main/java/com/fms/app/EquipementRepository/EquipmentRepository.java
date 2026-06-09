package com.fms.app.EquipementRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fms.app.Equipment.models.Equipment;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer>, JpaSpecificationExecutor<Equipment> {

	boolean existsByEqId(int id);
	
	@Modifying
	@Query("update eq eq set eq.blId=?1,eq.flId=?2,eq.rmId=?3,eq.eqStdId=?4,eq.description=?5,eq.svgElementId=?6 where eq.eqId=?7")
	void udpateAssetViaAcad(int blId,int flId,int rmId,int eqStdId,String description,String svgElementId,int eqId);
}
