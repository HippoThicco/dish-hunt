package com.dishhunt.controller;

import java.io.IOException;
import java.util.List;

import com.dishhunt.model.Recipe;
import com.dishhunt.service.RecipeService;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeController {
	@FXML private TextField searchField;
	@FXML private TableView<Recipe> recipesTable;
	@FXML private TableColumn<Recipe, String> titleColumn;
	@FXML private TableColumn<Recipe, String> descriptionColumn;
	@FXML private TableColumn<Recipe, Integer> caloriesColumn;
	@FXML private Label searchStatusLabel;
	
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
			recipeData.clear();
			searchStatusLabel.setText("Please enter a dish name.");
			return;
		}
		
		List<Recipe> recipes = recipeService.getRecipesByDishName(query);
		recipeData.setAll(recipes);
		
		if (recipes.isEmpty()) {
			searchStatusLabel.setText("No recipes found for \"" + query + "\".");
		} else {
			searchStatusLabel.setText("Found " + recipes.size() + " recipe(s) for \"" + query + "\".");
		}
	}
	
	@FXML
	private void handleLogout() {
		com.dishhunt.util.SessionManager.setCurrentUser(null);
		com.dishhunt.util.SceneManager.switchScene("login.fxml");
	}
	
	@FXML
	private void handleProfile() {
		com.dishhunt.util.SceneManager.switchScene("profile.fxml");
	}
	@FXML
	private void handleRecipeDoubleClick(MouseEvent event) {
		if (event.getClickCount() == 2 && !recipesTable.getSelectionModel().isEmpty()) {
			Recipe selectedRecipe = recipesTable.getSelectionModel().getSelectedItem();
			
			if (selectedRecipe != null) {
				openRecipePage(selectedRecipe);
			}
		}
	}
	
	private void openRecipePage(Recipe recipe) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/recipe.fxml"));
			Parent root = loader.load();
			
			RecipeController controller = loader.getController();
			controller.setRecipe(recipe);
			
			Stage stage = new Stage();
			stage.setTitle("Recipe Details");
			stage.setScene(new Scene(root, 800, 600));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
