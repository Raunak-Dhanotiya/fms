package com.fms.app.helpdesk.models;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "wr_comments")
@Table(name = "wr_comments")
public class WrComments {

	@Id
	@Column(name = "comment_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer commentId;

	@Column(name = "em_id")
	private Integer emId;

	@Column(name = "comment_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date commentDate;

	@Column(name = "comment_time")
	@JsonFormat(pattern = "HH:mm:ss")
	private Time commentTime;

	@Column(name = "wr_id")
	private Integer wrId;

	@Column(name = "comments")
	private String comments;

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getEmId() {
		return emId;
	}

	public void setEmId(Integer emId) {
		this.emId = emId;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public Time getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Time commentTime) {
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

	public WrComments(Integer commentId, Integer emId, Date commentDate, Time commentTime, Integer wrId, String comments) {
		super();
		this.commentId = commentId;
		this.emId = emId;
		this.commentDate = commentDate;
		this.commentTime = commentTime;
		this.wrId = wrId;
		this.comments = comments;
	}

	public WrComments() {
		super();
		// TODO Auto-generated constructor stub
	}

}
