package com.example.myrecipe1.model.bookmark;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("id_bookmark")
	private String idBookmark;

	@SerializedName("id_recipe")
	private String idRecipe;

	public void setIdBookmark(String idBookmark){
		this.idBookmark = idBookmark;
	}

	public String getIdBookmark(){
		return idBookmark;
	}

	public void setIdRecipe(String idRecipe){
		this.idRecipe = idRecipe;
	}

	public String getIdRecipe(){
		return idRecipe;
	}
}