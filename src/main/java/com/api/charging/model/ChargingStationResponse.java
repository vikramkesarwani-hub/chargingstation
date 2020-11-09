/**
 * Licensed Materials - Property of VK
 * 
 * (C) Copyright VK Corp. 2020. All Rights Reserved.
 * 
 */
package com.api.charging.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.api.charging.constants.StatusEnum;
import com.api.charging.entity.ChargingStationSession;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;



/**
 * 
 * Charging station response for attribute of station session
 * @author VK
 * 
 * 
 * Sep 6, 2020
 */
@Data
public class ChargingStationResponse {

	
	@Getter
	@Setter
	 	private UUID id;
	@Getter
	@Setter
	    private String stationId;
	@Getter
	@Setter
	    private LocalDateTime updatedAt;
	@Getter
	@Setter
	    private StatusEnum status;
	
	
    /**
	 * @param id2
	 * @param stationId2
	 * @param updatedAt2
	 * @param status2
	 */
	public ChargingStationResponse(UUID id2, String stationId2, LocalDateTime updatedAt2, StatusEnum status2) {
		this.id = id2;
		this.stationId = stationId2;
		this.updatedAt = updatedAt2;
		this.status = status2;
	}
	public static ChargingStationResponse mapper(final ChargingStationSession chargingSession) {
        return new ChargingStationResponse(chargingSession.getId(),
                chargingSession.getStationId(),
                chargingSession.getUpdatedAt(),
                chargingSession.getStatus());
    }
	/**
	 * @return the id
	 */
	public UUID getId() {
		return this.id;
	}

	/**
	 * @return the stationId
	 */
	public String getStationId() {
		return this.stationId;
	}

	/**
	 * @return the updatedAt
	 */
	public LocalDateTime getUpdatedAt() {
		return this.updatedAt;
	}

	/**
	 * @return the status
	 */
	public StatusEnum getStatus() {
		return this.status;
	}

	
	
}
