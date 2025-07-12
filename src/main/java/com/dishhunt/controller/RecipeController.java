package com.dishhunt.controller;

import com.dishhunt.model.Recipe;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RecipeController {
	@FXML private Label recipeTitle;
	@FXML private Label recipeDescription;
	@FXML private Label recipeCalories;
	@FXML private Label recipeIngredients;
	@FXML private Label recipeInstructions;
	@FXML private TextField commentBox;
	
	public void setRecipe(Recipe recipe) {
		recipeTitle.setText(recipe.getTitle());
		recipeDescription.setText(recipe.getDescription());
		recipeCalories.setText("Calories: " + recipe.getCalories());
		recipeIngredients.setText("" + recipe.getIngredients());
		recipeInstructions.setText(recipe.getInstructions());
	}

	@FXML
	public void likeRecipe() {}
	
	@FXML
	public void favouriteRecipe() {};
	
	@FXML
	public void comment() {};
}
