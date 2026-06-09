package com.fms.app.svg.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.fms.app.spaceManagement.models.Rm;

@Service
public class SvgViewService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Rm> getRmBySvgName(String svgName) {
		String query = "Select * from rm left join fl on rm.fl_id = fl.fl_id and rm.bl_id= fl.bl_id "
				+ " where fl.svg_name = '"+svgName+"'"+"";
		Query nativeQuery = this.entityManager.createNativeQuery(query, Rm.class);
		@SuppressWarnings("unchecked")
		List<Rm> dataRecords = nativeQuery.getResultList();
		if (!dataRecords.isEmpty()) {
			return dataRecords;
		}
		return null;
	}
}
