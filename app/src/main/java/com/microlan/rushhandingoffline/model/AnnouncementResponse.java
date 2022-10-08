package com.microlan.rushhandingoffline.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AnnouncementResponse {

	@SerializedName("noti")
	private List<NotiItem> noti;

	@SerializedName("announcement")
	private List<AnnouncementItem> announcement;

	@SerializedName("status")
	private int status;

	public List<NotiItem> getNoti(){
		return noti;
	}

	public List<AnnouncementItem> getAnnouncement(){
		return announcement;
	}

	public int getStatus(){
		return status;
	}
}