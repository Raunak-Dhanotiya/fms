package com.fms.app.response.output;

public class HelpdeskDashboardCardCount {

	private Long requestCount;
	private Long assignedCount;
	private Long overDueCount;
	private Long completeCount;
	private Long unassignedCount;

	/**
	 * @return the requestCount
	 */
	public Long getRequestCount() {
		return requestCount;
	}

	/**
	 * @param requestCount the requestCount to set
	 */
	public void setRequestCount(Long requestCount) {
		this.requestCount = requestCount;
	}

	/**
	 * @return the assignedCount
	 */
	public Long getAssignedCount() {
		return assignedCount;
	}

	/**
	 * @param assignedCount the assignedCount to set
	 */
	public void setAssignedCount(Long assignedCount) {
		this.assignedCount = assignedCount;
	}

	/**
	 * @return the overDueCount
	 */
	public Long getOverDueCount() {
		return overDueCount;
	}

	/**
	 * @param overDueCount the overDueCount to set
	 */
	public void setOverDueCount(Long overDueCount) {
		this.overDueCount = overDueCount;
	}

	/**
	 * @return the completeCount
	 */
	public Long getCompleteCount() {
		return completeCount;
	}

	/**
	 * @param completeCount the completeCount to set
	 */
	public void setCompleteCount(Long completeCount) {
		this.completeCount = completeCount;
	}

	/**
	 * @return the unassignedCount
	 */
	public Long getUnassignedCount() {
		return unassignedCount;
	}

	/**
	 * @param unassignedCount the unassignedCount to set
	 */
	public void setUnassignedCount(Long unassignedCount) {
		this.unassignedCount = unassignedCount;
	}

	public HelpdeskDashboardCardCount() {
		// TODO Auto-generated constructor stub
	}

	public HelpdeskDashboardCardCount(Long requestCount, Long assignedCount, Long overDueCount, Long completeCount,
			Long unassignedCount) {
		super();
		this.requestCount = requestCount;
		this.assignedCount = assignedCount;
		this.overDueCount = overDueCount;
		this.completeCount = completeCount;
		this.unassignedCount = unassignedCount;
	}

}
