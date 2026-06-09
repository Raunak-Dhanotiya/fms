package com.fms.app.sidenav;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ScreenServiceImpl implements IScreenService {

	@Autowired
	IScreenRepository repository;
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Screen> getScreen() {
		// TODO Auto-generated method stub
		return this.repository.findAll(Sort.by("displayOrder").ascending());
	}

	@Override
	public Screen getScreen(int id) {
		// TODO Auto-generated method stub
		return this.repository.findById(id).get();
	}

	@Override
	public void saveScreen(Screen record) {
		this.repository.save(record);

	}

	@Override
	public void delete(int id) {
		this.repository.deleteById(id);
	}

	@Override
	public void delete(Screen record) {
		this.repository.delete(record);

	}

	public List<Screen> getUserUnAssignScreens(int userRoleId) {
		String query = "Select * from screen where screen_num not in (Select screen_num from user_screens where "
				+ " user_role_id =" + userRoleId + " )";
		Query nativeQuery = this.em.createNativeQuery(query, Screen.class);
		List<Screen> dataRecords = nativeQuery.getResultList();
		return dataRecords;
	}

}
