package com.fms.app.response.output;

import java.io.Serializable;
import java.util.List;

public class SubProcessItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String icon;
	private List<SubMenuItems> subMenuItems;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<SubMenuItems> getSubMenuItems() {
		return subMenuItems;
	}
	public void setSubMenuItems(List<SubMenuItems> subMenuItems) {
		this.subMenuItems = subMenuItems;
	}
	public SubProcessItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SubProcessItem(String title, String icon, List<SubMenuItems> subMenuItems) {
		super();
		this.title = title;
		this.icon = icon;
		this.subMenuItems = subMenuItems;
	}
	
	
}
