package com.dishhunt.controller;
import java.io.IOException;
import java.util.List;

import com.dishhunt.model.Recipe;
import com.dishhunt.service.RecipeService;
import com.dishhunt.util.SessionManager;

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

public class ProfileController {
	@FXML private Label usernameLabel;
	@FXML private Label nameLabel;
	@FXML private Label nationalityLabel;
	@FXML private Label joinDateLabel;
	@FXML private Label bioLabel;
	@FXML private TableView<Recipe> contributedRecipesTable;
	@FXML private TableColumn<Recipe, String> contributedtitleColumn;
	@FXML private TableColumn<Recipe, String> contributeddescriptionColumn;
	@FXML private TableColumn<Recipe, Integer> contributedcaloriesColumn;
	@FXML private TableView<Recipe> favouriteRecipesTable;
	@FXML private TableColumn<Recipe, String> titleColumn;
	@FXML private TableColumn<Recipe, String> descriptionColumn;
	@FXML private TableColumn<Recipe, Integer> caloriesColumn;
	
	private final RecipeService recipeService = new RecipeService();
	private final ObservableList<Recipe> recipeData = FXCollections.observableArrayList();
	
	@FXML
	public void initialize() {
		contributedtitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
		contributeddescriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
		contributedcaloriesColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCalories()).asObject());
        
		contributedRecipesTable.setItems(recipeData);
		loadContributedRecipes();
		
		titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        caloriesColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCalories()).asObject());
        
        favouriteRecipesTable.setItems(recipeData);
        loadFavouriteRecipes();
	}
	
	private void loadContributedRecipes() {
		List<Recipe> userRecipes = recipeService.getRecipesByUser(SessionManager.getCurrentUser());
		recipeData.setAll(userRecipes);
	}
	
	private void loadFavouriteRecipes() {
		List<Recipe> favouriteRecipes = recipeService.getAllRecipes();
		recipeData.setAll(favouriteRecipes);
	}
}
