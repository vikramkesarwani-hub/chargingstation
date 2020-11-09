/**
 * Licensed Materials - Property of VK
 * 
 * (C) Copyright VK Corp. 2020. All Rights Reserved.
 * 
 */
package com.api.charging.service;

import com.api.charging.model.ChargingStationSummaryResponse;


/**
 * 
 * Service interface
 * @author VK
 * 
 * 
 * Sep 6, 2020
 */
public interface ChargingStationSummaryService {

	/**
	 * @return
	 */
	ChargingStationSummaryResponse retrieveAllSummary();

}
