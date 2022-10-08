package com.microlan.rushhandingoffline.model;

import com.google.gson.annotations.SerializedName;

public class NotiItem{

	@SerializedName("msg")
	private String msg;

	@SerializedName("date")
	private String date;

	@SerializedName("image")
	private String image;

	@SerializedName("seen_unseen")
	private String seenUnseen;

	@SerializedName("id")
	private String id;

	@SerializedName("version")
	private String version;

	@SerializedName("status")
	private String status;

	public String getMsg(){
		return msg;
	}

	public String getDate(){
		return date;
	}

	public String getImage(){
		return image;
	}

	public String getSeenUnseen(){
		return seenUnseen;
	}

	public String getId(){
		return id;
	}

	public String getVersion(){
		return version;
	}

	public String getStatus(){
		return status;
	}
}