package com.example.myrecipe1.model.isbookmark;

import com.google.gson.annotations.SerializedName;

public class IsBookmarkedResponse {

	@SerializedName("isBookmarked")
	private boolean isBookmarked;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setIsBookmarked(boolean isBookmarked){
		this.isBookmarked = isBookmarked;
	}

	public boolean isIsBookmarked(){
		return isBookmarked;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}