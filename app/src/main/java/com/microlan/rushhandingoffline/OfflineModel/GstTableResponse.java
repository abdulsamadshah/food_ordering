package com.microlan.rushhandingoffline.OfflineModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GstTableResponse {

	@SerializedName("gst_settings")
	private List<GstSettingsItem> gstSettings;

	public List<GstSettingsItem> getGstSettings(){
		return gstSettings;
	}
}