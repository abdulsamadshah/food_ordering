package com.microlan.rushhandingoffline.model;

import com.google.gson.annotations.SerializedName;

public class CompanyLogoItem{

	@SerializedName("company_logo")
	private String companyLogo;

	@SerializedName("gst_no")
	private String gstNo;

	@SerializedName("billing_address")
	private String billingAddress;

	@SerializedName("state_code")
	private String stateCode;

	public String getCompanyLogo(){
		return companyLogo;
	}

	public String getGstNo(){
		return gstNo;
	}

	public String getBillingAddress(){
		return billingAddress;
	}

	public String getStateCode(){
		return stateCode;
	}
}