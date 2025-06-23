package com.dishhunt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dishhunt.model.Dish;
import com.dishhunt.model.Recipe;
import com.dishhunt.model.User;

public class RecipeService {
	private final Map<String, Dish> dishMap = new HashMap<>();
	private final List<Recipe> recipeList = new ArrayList<>();
	
	public boolean uploadRecipe(User user, Recipe recipe, String dishName) {
		if (user == null || recipe == null || dishName.isEmpty()) return false;
		
		Dish dish = dishMap.get(dishName.toLowerCase());
		
		if (dish == null) {
			dish = new Dish(dishName, "Uncategorized", "");
			dishMap.put(dishName.toLowerCase(), dish);
		}
		
		recipe.setDish(dish);
		recipe.setAuthor(user);
		dish.addRecipe(recipe);
		recipeList.add(recipe);
		
		return true;
	}
	
	public List<Recipe> getRecipesByDish(String dishName) {
		Dish dish = dishMap.get(dishName);
		return (dish != null) ? dish.getRecipes() : new ArrayList<>();
	}
	
	public List<Recipe> getRecipesByUser(User user) {
		List<Recipe> result = new ArrayList<>();
		
		for (Recipe r : recipeList) {
			if (r.getAuthor() != null && r.getAuthor().getId() == user.getId()) {
				result.add(r);
			}
		}

		return result;
	}
}
