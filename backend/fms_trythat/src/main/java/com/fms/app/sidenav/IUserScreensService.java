package com.fms.app.sidenav;

import java.util.List;

public interface IUserScreensService {

	public List<UserScreens> getUserScreens();

	public UserScreens getUserScreens(int id);

	public void saveUserScreens(UserScreens record);

	public void delete(int id);

	public void delete(UserScreens record);
}
