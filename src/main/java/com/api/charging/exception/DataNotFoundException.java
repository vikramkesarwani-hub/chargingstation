/**
 * Licensed Materials - Property of VK
 * 
 * (C) Copyright VK Corp. 2020. All Rights Reserved.
 * 
 *
 */
package com.api.charging.exception;


/**
 * 
 * Custom exception for end session call if id is not found
 * @author VK
 * 
 * 
 * Sep 6, 2020
 */
public class DataNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = -9054724507398243555L;

	public DataNotFoundException() {
		super();
	}

	public DataNotFoundException(String arg0) {
		super(arg0);
	}

}
