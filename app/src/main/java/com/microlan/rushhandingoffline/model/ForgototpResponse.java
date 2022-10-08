package com.microlan.rushhandingoffline.model;

import com.google.gson.annotations.SerializedName;

public class ForgototpResponse {


	@SerializedName("user_id")
	private String userId;

	@SerializedName("otp_code")
	private int otpCode;

	@SerializedName("status")
		private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setOtpCode(int otpCode){
		this.otpCode = otpCode;
	}

	public int getOtpCode(){
		return otpCode;
	}
}