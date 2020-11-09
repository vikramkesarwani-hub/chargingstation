/**
 * Licensed Materials - Property of VK
 * 
 * (C) Copyright VK Corp. 2020. All Rights Reserved.
 * 
 */
package com.api.charging.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 
 * CHarging station request for station id 
 * @author VK
 * 
 * 
 *         Sep 6, 2020
 */
@Data
public class ChargingStationRequest {

	@NotBlank
	private String stationId;

	/**
	 * @return the stationId
	 */
	public String getStationId() {
		return stationId;
	}

	/**
	 * @param stationId
	 *            the stationId to set
	 */
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

}
