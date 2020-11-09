/**
 * Licensed Materials - Property of VK
 * 
 * (C) Copyright VK Corp. 2020. All Rights Reserved.
 * 
 */
package com.api.charging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * Main class of charging station application
 * @author VK
 * 
 * 
 *         Sep 6, 2020
 */
@SpringBootApplication
public class ChargingStationApiApplication {

	/**
	 * Main method of charging station application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ChargingStationApiApplication.class, args);
	}

}
