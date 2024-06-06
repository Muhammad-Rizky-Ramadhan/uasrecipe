package com.example.myrecipe1.model.findrecipebyid;

import com.google.gson.annotations.SerializedName;

public class Findrecipebyid{

	@SerializedName("data")
	private Data data;

	@SerializedName("message")
	private String message;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}