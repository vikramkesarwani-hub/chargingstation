/**
 * Licensed Materials - Property of VK
 * 
 * (C) Copyright VK Corp. 2020. All Rights Reserved.
 * 
 */
package com.api.charging.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.charging.exception.DataNotFoundException;
import com.api.charging.model.ChargingStationRequest;
import com.api.charging.model.ChargingStationResponse;
import com.api.charging.model.ChargingStationSummaryResponse;
import com.api.charging.service.ChargingStationService;
import com.api.charging.service.ChargingStationSummaryService;
import com.api.charging.service.impl.ChargingStationServiceImpl;
import com.api.charging.service.impl.ChargingStationSummaryServiceImpl;

/**
 * 
 * Controller class of charging station
 * @author VK
 * 
 * 
 *         Sep 6, 2020
 */
@RestController
public class ChargingStationRestController {

	/**
	 * logger 
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ChargingStationRestController.class);

	/**
	 * service offering for create, end and all session
	 */
	private final ChargingStationService chargingStationService = new ChargingStationServiceImpl();

	/**
	 * service offering for all session summary
	 */
	private final ChargingStationSummaryService chargingStationSummaryService = new ChargingStationSummaryServiceImpl();

	/**
	 * create charging session POST request for stationId
	 * @param request
	 * @return
	 */
	@PostMapping("/chargingSessions")
	public ResponseEntity<ChargingStationResponse> createChargingStationSession(
			@RequestBody @Valid ChargingStationRequest request) {
		logger.info(
				"Inside create------------------------------------------------!!");
		return ResponseEntity.ok().body(
				chargingStationService.createSession(request.getStationId()));

	}

	/**
	 * Stop charging station PUT request for given id
	 * @param id
	 * @return
	 */
	@PutMapping("/chargingSessions/{id}")
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ChargingStationResponse> endChargingStationSession(
			@PathVariable String id) throws DataNotFoundException {
		logger.debug("Inside end!!");
		return ResponseEntity.ok().body(chargingStationService.endSession(id));

	}

	/**
	 * GET request of get all running and finished sessions of charging station
	 * @return
	 */
	@GetMapping("/chargingSessions")
	public ResponseEntity<List<ChargingStationResponse>> getAllSessions() {
		logger.debug("Inside all session!!");
		return ResponseEntity.ok().body(chargingStationService.retrieveAll());
	}

	/**
	 * GET request of counts related to summary of all sessions
	 * @return
	 */
	@GetMapping("/chargingSessions/summary")
	public ResponseEntity<ChargingStationSummaryResponse> getAllSessionsSummary() {
		return ResponseEntity.ok()
				.body(chargingStationSummaryService.retrieveAllSummary());
	}

}
