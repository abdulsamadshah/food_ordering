package com.microlan.rushhandingoffline.OfflineModel;

import com.google.gson.annotations.SerializedName;

public class GstSettingsItem{

	@SerializedName("SGST")
	private String sGST;

	@SerializedName("category_id")
	private Object categoryId;

	@SerializedName("sub_category_id")
	private String subCategoryId;

	@SerializedName("gst_type")
	private String gstType;

	@SerializedName("description")
	private String description;

	@SerializedName("gst_set_id")
	private String gstSetId;

	@SerializedName("CGST")
	private String cGST;

	@SerializedName("hsn_code")
	private String hsnCode;

	@SerializedName("IGST")
	private String iGST;

	@SerializedName("CESS")
	private String cESS;

	public String getSGST(){
		return sGST;
	}

	public Object getCategoryId(){
		return categoryId;
	}

	public String getSubCategoryId(){
		return subCategoryId;
	}

	public String getGstType(){
		return gstType;
	}

	public String getDescription(){
		return description;
	}

	public String getGstSetId(){
		return gstSetId;
	}

	public String getCGST(){
		return cGST;
	}

	public String getHsnCode(){
		return hsnCode;
	}

	public String getIGST(){
		return iGST;
	}

	public String getCESS(){
		return cESS;
	}
}