package com.example.myrecipe1.model.createrecipes;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("id_category")
	private String idCategory;

	@SerializedName("name")
	private String name;

	@SerializedName("ingredients")
	private String ingredients;

	@SerializedName("time")
	private String time;

	@SerializedName("steps")
	private String steps;

	@SerializedName("id_recipe")
	private String idRecipe;

	@SerializedName("picture_recipe")
	private String pictureRecipe;

	public void setIdCategory(String idCategory){
		this.idCategory = idCategory;
	}

	public String getIdCategory(){
		return idCategory;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setIngredients(String ingredients){
		this.ingredients = ingredients;
	}

	public String getIngredients(){
		return ingredients;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	public void setSteps(String steps){
		this.steps = steps;
	}

	public String getSteps(){
		return steps;
	}

	public void setIdRecipe(String idRecipe){
		this.idRecipe = idRecipe;
	}

	public String getIdRecipe(){
		return idRecipe;
	}

	public void setPictureRecipe(String pictureRecipe){
		this.pictureRecipe = pictureRecipe;
	}

	public String getPictureRecipe(){
		return pictureRecipe;
	}
}