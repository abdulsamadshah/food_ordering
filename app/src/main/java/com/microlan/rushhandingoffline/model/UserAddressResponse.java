package com.microlan.rushhandingoffline.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserAddressResponse {

	@SerializedName("user_address")
	private List<UserAddressItem> userAddress;

	@SerializedName("status")
	private String status;

	public void setUserAddress(List<UserAddressItem> userAddress) {
		this.userAddress = userAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<UserAddressItem> getUserAddress(){
		return userAddress;


	}
}