/**
 * Licensed Materials - Property of VK
 * 
 * (C) Copyright VK Corp. 2020. All Rights Reserved.
 * 
 */package com.api.charging.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.api.charging.constants.StatusEnum;

import lombok.Data;


/**
 * 
 * Charging station session entity 
 * @author VK
 * 
 * 
 * Sep 6, 2020
 */
@Data
public class ChargingStationSession {
	

	private UUID id;

	private String stationId;

	private LocalDateTime startedAt;

	private LocalDateTime stoppedAt;

	private LocalDateTime updatedAt;

	private StatusEnum status;

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the stationId
	 */
	public String getStationId() {
		return stationId;
	}

	/**
	 * @param stationId the stationId to set
	 */
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	/**
	 * @return the startedAt
	 */
	public LocalDateTime getStartedAt() {
		return startedAt;
	}

	/**
	 * @param startedAt the startedAt to set
	 */
	public void setStartedAt(LocalDateTime startedAt) {
		this.startedAt = startedAt;
	}

	/**
	 * @return the stoppedAt
	 */
	public LocalDateTime getStoppedAt() {
		return stoppedAt;
	}

	/**
	 * @param stoppedAt the stoppedAt to set
	 */
	public void setStoppedAt(LocalDateTime stoppedAt) {
		this.stoppedAt = stoppedAt;
	}

	/**
	 * @return the updatedAt
	 */
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the status
	 */
	public StatusEnum getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	
	
	
}
