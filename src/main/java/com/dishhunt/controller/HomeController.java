package com.dishhunt.controller;

import java.util.List;

import com.dishhunt.model.Recipe;
import com.dishhunt.service.RecipeService;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class HomeController {
	@FXML private TextField searchField;
	@FXML private TableView<Recipe> recipesTable;
	@FXML private TableColumn<Recipe, String> titleColumn;
	@FXML private TableColumn<Recipe, String> descriptionColumn;
	@FXML private TableColumn<Recipe, Integer> caloriesColumn;
	
	private final RecipeService recipeService = new RecipeService();
	private final ObservableList<Recipe> recipeData = FXCollections.observableArrayList();
	
	@FXML
	public void initialize() {
		titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        caloriesColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCalories()).asObject());
        
        recipesTable.setItems(recipeData);
        loadAllRecipes();
	}
	
	private void loadAllRecipes() {
		List<Recipe> allRecipes = recipeService.getAllRecipes();
		recipeData.setAll(allRecipes);
	}
	
	@FXML
	private void handleSearch() {
		String query = searchField.getText().trim().toLowerCase();
		
		if (query.isEmpty() ) {
			recipesContainer.getChildren().add(new Label("Please enter a dish name."));
			return;
		}
		
		List<Recipe> recipes = recipeService.getRecipesByDishName(query);
		
		if (recipes.isEmpty()) {
			recipesContainer.getChildren().add(new Label("No recipes found for \"" + query + "\"."));
		} else {
			for (Recipe recipe : recipes) {
				Label recipeLabel = new Label(recipe.getTitle() + " - Calories: " + recipe.getCalories() + " - D");
				recipesContainer.getChildren().add(recipeLabel);
			}
		}
	}
	
	@FXML
	private void handleLogout() {
		com.dishhunt.util.SessionManager.setCurrentUser(null);
		com.dishhunt.util.SceneManager.switchScene("login.fxml");
	}
	
	@FXML
	private void handleRecipeDoubleClick(MouseEvent event) {
		if (event.getClickCount() == 2 && !recipesTable.getSelectionModel().isEmpty()) {
			Recipe selectedRecipe = recipesTable.getSelectionModel().getSelectedItem();
			
			
		}
	}
}
