package com.wp.whatsapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse {

	@JsonProperty("message")
	private String message;
	
	@JsonProperty("status")
	private boolean status;
	
	
	public ApiResponse(String message, boolean status) {
		super();
		this.message = message;
		this.status = status;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
	
}
