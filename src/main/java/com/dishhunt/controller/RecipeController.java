package com.dishhunt.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.dishhunt.model.Recipe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RecipeController {
	@FXML private Label recipeTitle;
	@FXML private Label recipeDescription;
	@FXML private Label recipeCalories;
	@FXML private ListView<String> recipeIngredients;
	@FXML private Label recipeInstructions;
	@FXML private TextField commentBox;
	@FXML private Label ingredientsLabel;
	@FXML private Label instructionsLabel;
	@FXML private ScrollPane scrollPane;
	
	@FXML private VBox ingredientsBox;

	public void setRecipe(Recipe recipe) {
	    recipeTitle.setText(recipe.getTitle());
	    recipeDescription.setText(recipe.getDescription());
	    recipeCalories.setText("Calories: " + recipe.getCalories());
	    
	    ingredientsBox.getChildren().clear();  // Clear old ingredients

	    List<String> ingredientStrings = recipe.getIngredients().stream()
	        .map(ingredient -> String.format("%.2f %s %s",
	                                         ingredient.getQuantity(),
	                                         ingredient.getUnit(),
	                                         ingredient.getName()))
	        .collect(Collectors.toList());

	    for (String ingredient : ingredientStrings) {
	        Label label = new Label(ingredient);
	        label.setStyle("-fx-font-size: 15px;");
	        ingredientsBox.getChildren().add(label);
	    }

	    recipeInstructions.setText(recipe.getInstructions());
	    recipeInstructions.setMouseTransparent(true);
	    recipeInstructions.setFocusTraversable(false);
	    
	    Platform.runLater(() -> scrollPane.setVvalue(0.0));
	}


	@FXML
	public void likeRecipe() {}
	
	@FXML
	public void favouriteRecipe() {};
	
	@FXML
	public void comment() {};
}
