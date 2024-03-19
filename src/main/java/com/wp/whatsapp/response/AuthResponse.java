package com.wp.whatsapp.response;

public class AuthResponse {

	private String jwt;
	private boolean isAuth;
	public AuthResponse(String jwt, boolean isAuth) {
		super();
		this.jwt = jwt;
		this.isAuth = isAuth;
	}
	
	
}
