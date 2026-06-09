package com.fms.app.helpdesk.models.dto;



public class wrCommentsOutPutDTO {
	private Integer commentId;
	private String emId;
	private String commentDate;
	private String commentTime;
	private Integer wrId;
	private String comments;
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public String getEmId() {
		return emId;
	}
	public void setEmId(String emId) {
		this.emId = emId;
	}
	public String getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
	public String getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
	public Integer getWrId() {
		return wrId;
	}
	public void setWrId(Integer wrId) {
		this.wrId = wrId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
