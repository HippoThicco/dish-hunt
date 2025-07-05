package com.dishhunt;

import com.dishhunt.util.SceneManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DishHuntApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
    	SceneManager.setStage(primaryStage);
    	SceneManager.switchScene("login.fxml");
    	primaryStage.setTitle("Dish Hunt");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
