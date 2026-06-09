package com.fms.app.sidenav;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.response.output.HomeDashboard;
import com.fms.app.security.AuthorizeUserInfo;

@Service
public class UserScreensService implements IUserScreensService {

	@Autowired
	IUserScreenRepository repository;
	@Autowired
	AuthorizeUserInfo userInfo;

	@Override
	public List<UserScreens> getUserScreens() {
		// TODO Auto-generated method stub
		return this.repository.findAll();
	}

	@Override
	public UserScreens getUserScreens(int id) {
		// TODO Auto-generated method stub
		return this.repository.findById(id).get();
	}

	@Override
	public void saveUserScreens(UserScreens record) {
		long recCount = this.repository.countByUserRoleAndScreenNum(record.getUserRoleId(),
				record.getScreenNum());
		if (recCount == 0)
			this.repository.save(record);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		this.repository.deleteById(id);
	}

	@Override
	public void delete(UserScreens record) {
		// TODO Auto-generated method stub
		this.repository.delete(record);
	}

	public List<UserScreens> getUserScreensByUserRole(Integer userRole) {
		return this.repository.findByUserRole(userRole);
	}

	public HomeDashboard getUserDashboards(final int userRole) {
		List<List<Object>> dataObj = this.repository.getUserDashboard(userRole);
		List<Object> dataRecord = dataObj.stream().flatMap(x -> x.stream()).collect(Collectors.toList());
		HomeDashboard resultSet = new HomeDashboard(dataRecord);
		return resultSet;
	}

}
