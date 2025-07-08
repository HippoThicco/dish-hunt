package com.dishhunt.controller;

import java.util.List;

import com.dishhunt.model.Recipe;
import com.dishhunt.service.RecipeService;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class HomeController {
	@FXML private TextField searchField;
	@FXML private VBox recipesContainer;
	
	private final RecipeService recipeService = new RecipeService();
	
	@FXML
	private void handleSearch() {
		String query = searchField.getText().trim().toLowerCase();
		
		recipesContainer.getChildren().clear();
		
		if (query.isEmpty() ) {
			recipesContainer.getChildren().add(new Label("Please enter a dish name."));
			return;
		}
		
		List<Recipe> recipes = recipeService.getRecipesByDishName(query);
		
		if (recipes.isEmpty()) {
			recipesContainer.getChildren().add(new Label("No recipes found for \"" + query + "\"."));
		} else {
			for (Recipe recipe : recipes) {
				Label recipeLabel = new Label(recipe.getTitle());
				recipesContainer.getChildren().add(recipeLabel);
			}
		}
	}
	
	@FXML
	private void handleLogout() {
		com.dishhunt.util.SessionManager.setCurrentUser(null);
		com.dishhunt.util.SceneManager.switchScene("login.fxml");
	}
}
