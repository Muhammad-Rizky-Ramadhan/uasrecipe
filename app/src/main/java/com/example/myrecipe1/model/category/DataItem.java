package com.example.myrecipe1.model.category;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("id_category")
	private String idCategory;

	@SerializedName("name_category")
	private String nameCategory;

	public void setIdCategory(String idCategory){
		this.idCategory = idCategory;
	}

	public String getIdCategory(){
		return idCategory;
	}

	public void setNameCategory(String nameCategory){
		this.nameCategory = nameCategory;
	}

	public String getNameCategory(){
		return nameCategory;
	}
}