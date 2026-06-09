package com.fms.app.sidenav;

import java.util.List;

public interface IScreenService {

	public List<Screen> getScreen();

	public Screen getScreen(int id);

	public void saveScreen(Screen record);

	public void delete(int processId);

	public void delete(Screen record);

}
