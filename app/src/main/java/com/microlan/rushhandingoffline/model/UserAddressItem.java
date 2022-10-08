package com.microlan.rushhandingoffline.model;

import com.google.gson.annotations.SerializedName;

public class UserAddressItem{

	@SerializedName("town_city")
	private String townCity;

	@SerializedName("pincode")
	private String pincode;

	@SerializedName("landmark_nearest_area")
	private String landmarkNearestArea;

	@SerializedName("full_name")
	private String fullName;

	@SerializedName("email_address")
	private String emailAddress;

	@SerializedName("address_type")
	private String addressType;

	@SerializedName("address2")
	private String address2;

	@SerializedName("address1")
	private String address1;

	@SerializedName("state")
	private String state;

	@SerializedName("mobile_number")
	private String mobileNumber;

	@SerializedName("state_code")
	private String stateCode;

	@SerializedName("user_id")
	private String user_id;

	@SerializedName("address_id")
	private String address_id;
	@SerializedName("unique_id")
	private String unique_id;

	public String getUnique_id() {
		return unique_id;
	}

	public String getAddress_id() {
		return address_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public String getTownCity(){
		return townCity;
	}

	public String getPincode(){
		return pincode;
	}

	public String getLandmarkNearestArea(){
		return landmarkNearestArea;
	}

	public String getFullName(){
		return fullName;
	}

	public String getEmailAddress(){
		return emailAddress;
	}

	public String getAddressType(){
		return addressType;
	}

	public String getAddress2(){
		return address2;
	}

	public String getAddress1(){
		return address1;
	}

	public String getState(){
		return state;
	}

	public String getMobileNumber(){
		return mobileNumber;
	}

	public String getStateCode(){
		return stateCode;
	}
}