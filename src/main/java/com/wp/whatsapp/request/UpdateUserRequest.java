package com.wp.whatsapp.request;

public class UpdateUserRequest {

	private String full_name;
	private String profile__picture;
	
	public UpdateUserRequest() {
		
	}

	public UpdateUserRequest(String full_name, String profile__picture) {
		super();
		this.full_name = full_name;
		this.profile__picture = profile__picture;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getProfile__picture() {
		return profile__picture;
	}

	public void setProfile__picture(String profile__picture) {
		this.profile__picture = profile__picture;
	}
	
	
}
