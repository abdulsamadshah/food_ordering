package com.microlan.rushhandingoffline.OfflineModel;

import java.util.List;

public class RegisterUserResponse {
	private List<UserDetailsItem> userDetails;
	private String status;

	public List<UserDetailsItem> getUserDetails(){
		return userDetails;
	}

	public String getStatus(){
		return status;
	}
}