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
	
	public Dish() {
	}

	public void addRecipe(Recipe recipe) {
		recipes.add(recipe);
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}
}
