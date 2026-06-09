package com.fms.app.response.output;

import java.io.Serializable;

public class SubMenuItems implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8389368595962568323L;

	private String title;
	private String link;

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
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	public SubMenuItems() {
		super();
	}

	public SubMenuItems(String title, String link) {
		super();
		this.title = title;
		this.link = link;
	}

	@Override
	public String toString() {
		return "SubMenuItems [title=" + title + ", link=" + link + "]";
	}
	
}
