package com.microlan.rushhandingoffline.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CompanySetting {

	@SerializedName("company_logo")
	private List<CompanyLogoItem> companyLogo;

	public List<CompanyLogoItem> getCompanyLogo(){
		return companyLogo;
	}
}