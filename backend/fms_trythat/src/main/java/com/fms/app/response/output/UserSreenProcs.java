package com.fms.app.response.output;

import java.util.List;

public class UserSreenProcs {

	private List<UserScreensOutput> userProcs;

	/**
	 * @return the userProcs
	 */
	public List<UserScreensOutput> getUserProcs() {
		return userProcs;
	}

	/**
	 * @param userProcs the userProcs to set
	 */
	public void setUserProcs(List<UserScreensOutput> userProcs) {
		this.userProcs = userProcs;
	}

	public UserSreenProcs() {
		super();
	}

	public UserSreenProcs(List<UserScreensOutput> userProcs) {
		super();
		this.userProcs = userProcs;
	}

}
