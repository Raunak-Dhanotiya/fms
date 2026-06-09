package com.fms.app.response.output;

import java.io.Serializable;
import java.util.List;

public class SideNavItems implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 967711384469287073L;
	
	private List<MenuItems> menuItems;
	
	public SideNavItems() {
		super();
	}
	
	public SideNavItems(List<MenuItems> menuItems) {
		super();
		this.menuItems = menuItems;
	}

	/**
	 * @return the menuItems
	 */
	public List<MenuItems> getMenuItems() {
		return menuItems;
	}

	/**
	 * @param menuItems the menuItems to set
	 */
	public void setMenuItems(List<MenuItems> menuItems) {
		this.menuItems = menuItems;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
	

}
