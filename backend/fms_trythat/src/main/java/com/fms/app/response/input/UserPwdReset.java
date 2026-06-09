package com.fms.app.response.input;

public class UserPwdReset {

	private String userEmail;
	private String url;

	/**
	 * @return the userName
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public UserPwdReset() {
		super();
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
