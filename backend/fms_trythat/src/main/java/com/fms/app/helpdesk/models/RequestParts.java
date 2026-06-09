package com.fms.app.helpdesk.models;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "request_parts")
@Table(name = "request_parts")
public class RequestParts {
	
    @Id
    @Column(name = "request_part_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestPartId;
    
    @Column(name = "request_id")
    private Integer requestId;
    
    @Column(name = "part_id")
    private Integer partId;

    @Column(name = "req_quantity")
    private Integer reqQuantity;
    
    @Column(name = "actual_quantity_used")
    private Integer actualQuantityUsed;
    
    @Column(name = "date_assigned")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateAssigned;

    @Column(name = "time_assigned")
    @JsonFormat(pattern="HH:mm:ss")
    private Time timeAssigned;
    
    @Column(name = "added_by")
	private Integer addedBy;
    
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "part_id",referencedColumnName = "part_id", insertable = false, updatable = false)
	private Parts part;
    
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "request_id", referencedColumnName = "wr_id", insertable = false, updatable = false)
	private Wr request;

    
    public RequestParts() {
        super();
        // TODO Auto-generated constructor stub
    }

    public RequestParts(Integer requestPartId, Integer requestId, Integer partId, Integer reqQuantity,
            Date dateAssigned, Time timeAssigned,Integer actualQuantityUsed,Integer addedBy) {
        super();
        this.requestPartId = requestPartId;
        this.requestId = requestId;
        this.partId = partId;
        this.reqQuantity = reqQuantity;
        this.dateAssigned = dateAssigned;
        this.timeAssigned = timeAssigned;
        this.actualQuantityUsed = actualQuantityUsed;
        this.addedBy = addedBy;
    }

    public Integer getRequestPartId() {
        return requestPartId;
    }

    public void setRequestPartId(Integer requestPartId) {
        this.requestPartId = requestPartId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getPartId() {
        return partId;
    }

    public void setPartId(Integer partId) {
        this.partId = partId;
    }

    public Integer getReqQuantity() {
        return reqQuantity;
    }

    public void setReqQuantity(Integer reqQuantity) {
        this.reqQuantity = reqQuantity;
    }

    public Date getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(Date dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    public Time getTimeAssigned() {
        return timeAssigned;
    }

    public void setTimeAssigned(Time timeAssigned) {
        this.timeAssigned = timeAssigned;
    }

	public Integer getActualQuantityUsed() {
		return actualQuantityUsed;
	}

	public void setActualQuantityUsed(Integer actualQuantityUsed) {
		this.actualQuantityUsed = actualQuantityUsed;
	}

	public Integer getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(Integer addedBy) {
		this.addedBy = addedBy;
	}

	public Parts getPart() {
		return part;
	}

	public Wr getRequest() {
		return request;
	}

	public void setRequest(Wr request) {
		this.request = request;
	}

	public void setPart(Parts part) {
		this.part = part;
	}
	
}
