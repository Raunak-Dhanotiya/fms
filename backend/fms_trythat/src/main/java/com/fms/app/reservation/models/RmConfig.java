package com.fms.app.reservation.models;

import java.sql.Time;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.Fl;
import com.fms.app.spaceManagement.models.Rm;

@Entity
@Table(name = "rm_config")
public class RmConfig {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "config_id")
	private Integer configId;

	@Column(name = "bl_id")
	private Integer blId;

	@Column(name = "fl_id")
	private Integer flId;

	@Column(name = "rm_id")
	private Integer rmId;

	@Column(name = "pre_block")
	private String preBlock;

	@Column(name = "post_block")
	private String postBlock;

	@Column(name = "max_capacity")
	private String maxCapacity;

	@Column(name = "min_capacity")
	private String minCapacity;

	@Column(name = "external_allowed")
	private String externalAllowed;

	@Column(name = "day_start")
	@JsonFormat(pattern="HH:mm:ss")
	private Time dayStart;

	@Column(name = "day_end")
	@JsonFormat(pattern="HH:mm:ss")
	private Time dayEnd;

	@Column(name = "date_created")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date dateCreated;

	@Column(name = "time_created")
	@JsonFormat(pattern="HH:mm:ss")
	private Time timeCreated;

	@Column(name = "date_last_updated")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date dateLastUpdated;

	@Column(name = "is_reservable")
	private String isReservable;
	
	@Column(name="is_approval_req")
	private String isApprovalRequired;
	
	@Column(name = "arrangement_id")
	private Integer arrangementId;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rm_id", referencedColumnName = "rm_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Rm rm;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fl_id", referencedColumnName = "fl_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Fl fl;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bl_id", referencedColumnName = "bl_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Bl bl;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "arrangement_id", referencedColumnName = "arrangement_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Arrangement arrangement;

	public RmConfig() {
		super();

	}

	public RmConfig(Integer configId, Integer blId, Integer flId, Integer rmId, String preBlock, String postBlock,
			String maxCapacity, String minCapacity, String externalAllowed, Time dayStart, Time dayEnd, Date dateCreated,
			Time timeCreated, Date dateLastUpdated, String isReservable,
			String isApprovalRequired, Integer arrangementId ) {
		super();
		this.configId = configId;
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
		this.preBlock = preBlock;
		this.postBlock = postBlock;
		this.maxCapacity = maxCapacity;
		this.minCapacity = minCapacity;
		this.externalAllowed = externalAllowed;
		this.dayStart = dayStart;
		this.dayEnd = dayEnd;
		this.dateCreated = dateCreated;
		this.timeCreated = timeCreated;
		this.dateLastUpdated = dateLastUpdated;
		this.isReservable = isReservable;
		this.isApprovalRequired = isApprovalRequired;
		this.arrangementId = arrangementId;
	}

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public Integer getBlId() {
		return blId;
	}

	public void setBlId(Integer blId) {
		this.blId = blId;
	}

	public Integer getFlId() {
		return flId;
	}

	public void setFlId(Integer flId) {
		this.flId = flId;
	}

	public Integer getRmId() {
		return rmId;
	}

	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}

	public String getPreBlock() {
		return preBlock;
	}

	public void setPreBlock(String preBlock) {
		this.preBlock = preBlock;
	}

	public String getPostBlock() {
		return postBlock;
	}

	public void setPostBlock(String postBlock) {
		this.postBlock = postBlock;
	}

	public String getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(String maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public String getMinCapacity() {
		return minCapacity;
	}

	public void setMinCapacity(String minCapacity) {
		this.minCapacity = minCapacity;
	}

	public String getExternalAllowed() {
		return externalAllowed;
	}

	public void setExternalAllowed(String externalAllowed) {
		this.externalAllowed = externalAllowed;
	}

	public Time getDayStart() {
		return dayStart;
	}

	public void setDayStart(Time dayStart) {
		this.dayStart = dayStart;
	}

	public Time getDayEnd() {
		return dayEnd;
	}

	public void setDayEnd(Time dayEnd) {
		this.dayEnd = dayEnd;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Time getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Time timeCreated) {
		this.timeCreated = timeCreated;
	}

	public Date getDateLastUpdated() {
		return dateLastUpdated;
	}

	public void setDateLastUpdated(Date dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}

	public String getIsReservable() {
		return isReservable;
	}

	public void setIsReservable(String isReservable) {
		this.isReservable = isReservable;
	}

	public Rm getRm() {
		return rm;
	}

	public void setRm(Rm rm) {
		this.rm = rm;
	}

	public String getIsApprovalRequired() {
		return isApprovalRequired;
	}

	public void setIsApprovalRequired(String isApprovalRequired) {
		this.isApprovalRequired = isApprovalRequired;
	}

	public Integer getArrangementId() {
		return arrangementId;
	}


	/**
	 * @return the fl
	 */
	public Fl getFl() {
		return fl;
	}

	/**
	 * @param fl the fl to set
	 */
	public void setFl(Fl fl) {
		this.fl = fl;
	}

	/**
	 * @return the bl
	 */
	public Bl getBl() {
		return bl;
	}

	/**
	 * @param bl the bl to set
	 */
	public void setBl(Bl bl) {
		this.bl = bl;
	}

	/**
	 * @param arrangementId the arrangementId to set
	 */
	public void setArrangementId(Integer arrangementId) {
		this.arrangementId = arrangementId;
	}

	public Arrangement getArrangement() {
		return arrangement;
	}

	public void setArrangement(Arrangement arrangement) {
		this.arrangement = arrangement;
	}
	
}
