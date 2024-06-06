package com.example.myrecipe1.model.updatedata;

import com.google.gson.annotations.SerializedName;

public class Update{

	@SerializedName("updated_data")
	private UpdatedData updatedData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setUpdatedData(UpdatedData updatedData){
		this.updatedData = updatedData;
	}

	public UpdatedData getUpdatedData(){
		return updatedData;
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