package com.example.exception.restapi.booking;

import lombok.Data;

@Data
public class ApiBookingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private StackTraceElement[] stackTrace;
	private String userFriendlyMessage;

	public ApiBookingException(
			StackTraceElement[] stackTrace, 
			String userFriendlyMessage) {
		super(userFriendlyMessage);
		this.stackTrace = stackTrace;
		this.userFriendlyMessage = userFriendlyMessage;
	}
}
