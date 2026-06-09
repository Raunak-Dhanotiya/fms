package com.fms.app.reservation.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.reservation.models.Resources;
import com.fms.app.reservation.models.RmResources;
import com.fms.app.reservation.repository.RmResourcesRepository;

@Service
public class RmResourcesService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	RmResourcesRepository repository;

	public void saveOrUpdate(RmResources data) {
		repository.save(data);

	}
	
	public List<RmResources> getAll() {

		List<RmResources> rmResources = new ArrayList<RmResources>();
		repository.findAll().forEach(rmResource -> rmResources.add(rmResource));
		return rmResources;
	}

	@Transactional
	public void deleteById(int rmResourcesId) {
		this.repository.deleteById(rmResourcesId);
	}

	public List<RmResources> getAssignResources(int blId, int flId, int rmId) {

		return this.repository.getByBlIdAndFlIdAndRmId(blId, flId, rmId);
	}

	public List<Resources> getUnAssignResources(int blId, int flId, int rmId) {
		String query = "Select * from resources where "
				+ " resources_id not in (Select resources_id from rm_resources where "
				+ " rm_id =" + rmId + " and bl_id = " + blId + " and fl_id = " + flId + " )";
		Query nativeQuery = this.em.createNativeQuery(query, Resources.class);
		@SuppressWarnings("unchecked")
		List<Resources> dataRecords = nativeQuery.getResultList();
		return dataRecords;
	}

}
