/**
 * Licensed Materials - Property of VK
 * 
 * (C) Copyright VK Corp. 2020. All Rights Reserved.
 * 
 */
package com.api.charging.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.charging.algorithm.ChargingStationBST;
import com.api.charging.model.ChargingStationSummaryResponse;
import com.api.charging.service.ChargingStationSummaryService;

/**
 * 
 * Service implementation session summary in counts
 * 
 * @author VK
 * 
 * 
 *         Sep 6, 2020
 */
public class ChargingStationSummaryServiceImpl
		implements
			ChargingStationSummaryService {

	private static final Logger logger = LoggerFactory
			.getLogger(ChargingStationSummaryServiceImpl.class);
	private static final Duration MIN = Duration.ofMinutes(1L);
	private final ChargingStationBST chargingStationBST = ChargingStationBST
			.getInstance();

	@Override
	public ChargingStationSummaryResponse retrieveAllSummary() {
		LocalDateTime oneMinBefore = LocalDateTime.now().minus(MIN);
		logger.info("Inside summary ----------->");
		return chargingStationBST.retrieveAllSummary(oneMinBefore);
	}
}
