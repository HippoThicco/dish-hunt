package com.dishhunt.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.dishhunt.model.Recipe;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RecipeController {
	@FXML private Label recipeTitle;
	@FXML private Label recipeDescription;
	@FXML private Label recipeCalories;
	@FXML private ListView<String> recipeIngredients;
	@FXML private TextArea recipeInstructions;
	@FXML private TextField commentBox;
	
	public void setRecipe(Recipe recipe) {
		recipeTitle.setText(recipe.getTitle());
		recipeDescription.setText(recipe.getDescription());
		recipeCalories.setText("Calories: " + recipe.getCalories());
		recipeIngredients.getItems().clear();
		List<String> ingredientStrings = recipe.getIngredients().stream()
			    .map(ingredient -> String.format("%.2f %s %s", 
			                                     ingredient.getQuantity(), 
			                                     ingredient.getUnit(), 
			                                     ingredient.getName()))
			    .collect(Collectors.toList());

			recipeIngredients.getItems().setAll(ingredientStrings);

		recipeInstructions.setText(recipe.getInstructions());
	}

	@FXML
	public void likeRecipe() {}
	
	@FXML
	public void favouriteRecipe() {};
	
	@FXML
	public void comment() {};
}
