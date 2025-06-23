package com.dishhunt.model;

import java.util.List;

public class Recipe {
	private int id;
	private Dish dish;
	private User author;
	private String title;
	private String description;
	private int calories;
	private List<Ingredient> ingredients;
	private String instructions;
	
	public void setDish(Dish dish) {
		this.dish = dish;
	}
	
	public void setAuthor(User user) {
		this.author = user;
	}
}
