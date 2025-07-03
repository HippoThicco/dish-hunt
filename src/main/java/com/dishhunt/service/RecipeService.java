package com.dishhunt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dishhunt.dao.RecipeDAO;
import com.dishhunt.model.Dish;
import com.dishhunt.model.Recipe;
import com.dishhunt.model.User;

public class RecipeService {
	private final RecipeDAO recipeDAO = new RecipeDAO();
	
	public boolean uploadRecipe(Recipe recipe) {
		if (recipe.getIngredients().isEmpty()) return false;
		String recipeTitle = recipe.getTitle();
		if (recipeDAO.getRecipeByTitle(recipeTitle) != null) return false;

		recipeDAO.uploadRecipe(recipe);
		
		return true;
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
	
	public List<Recipe> getRecipesByDishName(String dishName) {
		List<Recipe> list = recipeDAO.getRecipesByDishName(dishName);
		
		return list;
	}
	
	public Recipe getRecipeByTitle(String recipeTitle) {
		return recipeDAO.getRecipeByTitle(recipeTitle);
	}
}
