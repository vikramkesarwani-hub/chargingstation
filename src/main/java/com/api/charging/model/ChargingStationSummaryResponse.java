/**
 * Licensed Materials - Property of VK
 * 
 * (C) Copyright VK Corp. 2020. All Rights Reserved.
 * 
 */
package com.api.charging.model;

/**
 * 
 * Charging station summary response attribute 
 * @author VK
 * 
 * 
 *         Sep 6, 2020
 */
public class ChargingStationSummaryResponse {

	private long totalCount;

	private long startedCount;

	private long stoppedCount;

	/**
	 * @param totalCount
	 * @param startedCount
	 * @param stoppedCount
	 */
	public ChargingStationSummaryResponse(long totalCount, long startedCount,
			long stoppedCount) {
		this.totalCount = totalCount;
		this.startedCount = startedCount;
		this.stoppedCount = stoppedCount;
	}

	/**
	 * @return the totalCount
	 */
	public long getTotalCount() {
		return this.totalCount;
	}

	/**
	 * @return the startedCount
	 */
	public long getStartedCount() {
		return this.startedCount;
	}

	/**
	 * @return the stoppedCount
	 */
	public long getStoppedCount() {
		return this.stoppedCount;
	}

}
