/**
 * Licensed Materials - Property of VK
 * 
 * (C) Copyright VK Corp. 2020. All Rights Reserved.
 * 
 */
package com.api.charging.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.charging.algorithm.ChargingStationBST;
import com.api.charging.constants.StatusEnum;
import com.api.charging.entity.ChargingStationSession;
import com.api.charging.exception.DataNotFoundException;
import com.api.charging.model.ChargingStationResponse;
import com.api.charging.service.ChargingStationService;

/**
 * 
 * Service implementation of create, end and all session
 * 
 * @author VK
 * 
 * 
 *         Sep 6, 2020
 */
public class ChargingStationServiceImpl implements ChargingStationService {

	private static final Logger logger = LoggerFactory
			.getLogger(ChargingStationServiceImpl.class);

	private final ChargingStationBST chargingStationBST = ChargingStationBST
			.getInstance();

	@Override
	public ChargingStationResponse createSession(String stationId) {

		final LocalDateTime startedAt = LocalDateTime.now();
		final UUID uuid = UUID.randomUUID();
		final ChargingStationSession chargingSession = new ChargingStationSession();
		chargingSession.setId(uuid);
		chargingSession.setStationId(stationId);
		chargingSession.setStartedAt(startedAt);
		chargingSession.setUpdatedAt(startedAt);
		chargingSession.setStatus(StatusEnum.IN_PROGRESS);

		// BST
		chargingStationBST.insert(chargingSession);
		// BST
		return ChargingStationResponse.mapper(chargingSession);

	}

	@Override
	public ChargingStationResponse endSession(String id) {
		final UUID uuid = UUID.fromString(id);
		final LocalDateTime stoppedAt = LocalDateTime.now();

		// BST
		//Search record
		ChargingStationSession chargingSessionBST = chargingStationBST
				.searchNode(uuid);
		if (chargingSessionBST.getId() == null)
			throw new DataNotFoundException(
					"There is no in progress session with id: " + id);
		//Delete in-progress record
		chargingStationBST.deleteKey(chargingSessionBST);
		chargingSessionBST.setStatus(StatusEnum.FINISHED);
		chargingSessionBST.setUpdatedAt(stoppedAt);
		chargingSessionBST.setStoppedAt(stoppedAt);
		//Insert finished record 
		chargingStationBST.insert(chargingSessionBST);
		// BST

		logger.info("Session stopped with id " + id);
		return ChargingStationResponse.mapper(chargingSessionBST);
	}

	@Override
	public List<ChargingStationResponse> retrieveAll() {
		return chargingStationBST.retrieveAll();
	}

}
