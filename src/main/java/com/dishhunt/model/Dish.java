package com.dishhunt.model;

import java.util.List;

public class Dish {
	private int id;
	private String name;
	private String cuisine;
	private String description;
	private List<Recipe> recipes;
	
	public Dish(String dishName, String cuisine, String description) {
		this.name = dishName;
	}
	
	public void addRecipe(Recipe recipe) {
		recipes.add(recipe);
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}
}
