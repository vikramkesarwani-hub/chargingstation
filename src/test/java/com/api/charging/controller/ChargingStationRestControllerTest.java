/**
 * Licensed Materials - Property of VK
 * 
 * (C) Copyright VK Corp. 2020. All Rights Reserved.
 * 
 */
package com.api.charging.controller;

import static java.time.LocalDateTime.now;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.api.charging.constants.StatusEnum;
import com.api.charging.exception.DataNotFoundException;
import com.api.charging.model.ChargingStationRequest;
import com.api.charging.model.ChargingStationResponse;
import com.api.charging.model.ChargingStationSummaryResponse;
import com.api.charging.service.ChargingStationService;
import com.api.charging.service.ChargingStationSummaryService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Charging station API test class to check fail/success case of create/stop
 * charging station and retrieve request of charing station
 * 
 * @author VK
 * 
 * 
 *         Sep 6, 2020
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ChargingStationRestControllerTest {

	private static final String stationId1 = "ABC1234";
	private static final String stationId2 = "ABC123";
	private static final String stationIdBlank = "";

	@MockBean
	private ChargingStationService chargingStationService;

	@MockBean
	private ChargingStationSummaryService chargingStationSummaryService;

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Test function to in-progress status create charging session
	 * 
	 * @throws Exception
	 */
	@Test
	@DisplayName("POST /chargingSessions - Success")
	void testCreateChargingSession() throws Exception {

		// given
		ChargingStationRequest chargingStationRequest = new ChargingStationRequest();
		chargingStationRequest.setStationId(stationId1);

		// when
		ChargingStationResponse chargingSession = new ChargingStationResponse(
				UUID.randomUUID(), stationId1, now(), StatusEnum.IN_PROGRESS);

		doReturn(chargingSession).when(chargingStationService)
				.createSession(stationId1);

		// Execute the POST request
		mockMvc.perform(post("/chargingSessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(chargingStationRequest)))

				// Validate the response code
				.andExpect(status().isOk())

				// Validate the response
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id",
						is(notNullValue())))
				.andExpect(jsonPath("$.stationId",
						equalTo(chargingSession.getStationId())))
				.andExpect(jsonPath("$.status",
						equalTo(chargingSession.getStatus().toString())));

	}

	/**
	 * Test function for null stationId request test of create charging session
	 * 
	 * @throws Exception
	 */
	@Test
	@DisplayName("POST /chargingSessions - Fail")
	void testCreateChargingSessionWithNullRequest() throws Exception {

		// given
		ChargingStationRequest chargingStationRequest = new ChargingStationRequest();
		chargingStationRequest.setStationId(null);

		// when
		mockMvc.perform(post("/chargingSessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(chargingStationRequest)))
				// then
				.andExpect(status().isBadRequest());
	}

	 /**
	 * Test function for blank stationId request test of create charging session
	 * @throws Exception
	 */
	@Test
	 @DisplayName("POST /chargingSessions - Fail")
	 void testCreateChargingSessionWithBlankRequest() throws Exception {
	
	 // given
	 ChargingStationRequest chargingStationRequest = new
	 ChargingStationRequest();
	 chargingStationRequest.setStationId(stationIdBlank);
	
	 // when
	 mockMvc.perform(post("/chargingSessions").contentType(MediaType.APPLICATION_JSON)
	 .content(asJsonString(chargingStationRequest)))
	 // then
	 .andExpect(status().isBadRequest());
	 }

	/**
	 * Test function to Stop charging session for given stationId if No data found
	 * 
	 * @throws Exception
	 */
	@Test
	@DisplayName("PUT /chargingSessions - Success")
	void testStopChargingSessionDataNotFound() throws Exception {

		// given
		UUID uuid = UUID.randomUUID();

		// when
		ChargingStationResponse chargingSession = new ChargingStationResponse(
				uuid, stationId1, now(), StatusEnum.FINISHED);

		doReturn(chargingSession).doThrow(new DataNotFoundException("There is no in progress session with id: " + uuid.toString())).when(chargingStationService).endSession(uuid.toString());

		// Execute the PUT request
		mockMvc.perform(put("/chargingSessions/{id}", uuid.toString())
				.contentType(MediaType.APPLICATION_JSON))

				// Validate the response code
				.andExpect(status().isBadRequest())

				// Validate the response
				.andExpect(jsonPath("$.error", is("There is no in progress session with id: " + uuid.toString())));
				//.andExpect(jsonPath("$.error").value("There is no in progress session with id: " + uuid.toString()));

	}

	/**
	 * Test function to Retrieve all charging sessions
	 * 
	 * @throws Exception
	 */
	@Test
	@DisplayName("GET /chargingSessions - Success")
	void testGetChargingSessions() throws Exception {

		// given
		List<ChargingStationResponse> chargingStation = new ArrayList<>();

		chargingStation.add(new ChargingStationResponse(UUID.randomUUID(),
				stationId1, now(), StatusEnum.IN_PROGRESS));
		chargingStation.add(new ChargingStationResponse(UUID.randomUUID(),
				stationId2, now(), StatusEnum.IN_PROGRESS));

		doReturn(chargingStation).when(chargingStationService).retrieveAll();

		// Execute the GET request
		mockMvc.perform(get("/chargingSessions"))

				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))

				// Validate the returned fields
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id",
						is(notNullValue())))
				.andExpect(jsonPath("$[0].stationId",
						equalTo(chargingStation.get(0).getStationId())))
				// .andExpect(jsonPath("$[0].updatedAt",
				// equalTo(chargingStation.get(0).getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
				.andExpect(jsonPath("$[0].status", equalTo(
						chargingStation.get(0).getStatus().toString())));
		// .andExpect(jsonPath("$[1].id",
		// equalTo(chargingStation.get(1).getId().toString())))
		// .andExpect(jsonPath("$[1].stationId",
		// equalTo(chargingStation.get(1).getStationId())))
		// .andExpect(jsonPath("$[1].updatedAt",
		// equalTo(chargingStation.get(1).getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
		// .andExpect(jsonPath("$[1].status",
		// equalTo(chargingStation.get(1).getStatus().toString())));

	}

	/**
	 * Test function for Retrieve a summary of submitted charging sessions
	 * (TotalCount, startedCount, stoppedCount)
	 * 
	 * @throws Exception
	 */
	@Test
	@DisplayName("GET /chargingSessions/summary - Success")
	void testGetChargingSessionsSummary() throws Exception {

		// given
		ChargingStationSummaryResponse chargingStationSummary = new ChargingStationSummaryResponse(
				0L, 0L, 0L);

		// when
		doReturn(chargingStationSummary).when(chargingStationSummaryService)
				.retrieveAllSummary();

		// Execute the GET request
		mockMvc.perform(get("/chargingSessions/summary"))

				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))

				// Validate the returned fields
				.andExpect(jsonPath("$.totalCount",
						equalTo((int) chargingStationSummary.getTotalCount())))
				.andExpect(jsonPath("$.startedCount", equalTo(
						(int) chargingStationSummary.getStartedCount())))
				.andExpect(jsonPath("$.stoppedCount", equalTo(
						(int) chargingStationSummary.getStoppedCount())));

	}

	/**
	 * Common method to convert json object to string
	 * 
	 * @param obj
	 * @return
	 */
	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
