/**
 * Licensed Materials - Property of VK
 * 
 * (C) Copyright VK Corp. 2020. All Rights Reserved.
 * 
 */
package com.api.charging.service;

import java.util.List;

import com.api.charging.model.ChargingStationResponse;


/**
 * 
 * Service interface
 * @author VK
 * 
 * 
 * Sep 6, 2020
 */
public interface ChargingStationService {

	/**
	 * @param stationId
	 * @return
	 */
	ChargingStationResponse createSession(String stationId);

	/**
	 * @param id
	 * @return
	 */
	ChargingStationResponse endSession(String id);

	/**
	 * @return
	 */
	List<ChargingStationResponse> retrieveAll();

}
