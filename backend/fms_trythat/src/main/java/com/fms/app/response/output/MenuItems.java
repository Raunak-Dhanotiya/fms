package com.fms.app.response.output;

import java.io.Serializable;
import java.util.List;

public class MenuItems implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3698038277204811477L;

	private String title;
	private String icon;
	private List<SubProcessItem> subProcessItem;
	private List<SubMenuItems> subMenuItems;

	public MenuItems() {
		super();
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the subMenuItems
	 */
	public List<SubMenuItems> getSubMenuItems() {
		return subMenuItems;
	}

	/**
	 * @param subMenuItems the subMenuItems to set
	 */
	public void setSubMenuItems(List<SubMenuItems> subMenuItems) {
		this.subMenuItems = subMenuItems;
	}

	@Override
	public String toString() {
		return "MenuItems [title=" + title + ", icon=" + icon + ", subMenuItems=" + subMenuItems + "]";
	}

	public List<SubProcessItem> getSubProcessItem() {
		return subProcessItem;
	}

	public void setSubProcessItem(List<SubProcessItem> subProcessItem) {
		this.subProcessItem = subProcessItem;
	}
	
	

}
