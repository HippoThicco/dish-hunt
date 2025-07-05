package com.dishhunt.util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

	private static Stage primaryStage;
	
	public static void setStage(Stage stage) {
		primaryStage = stage;
	}
	
	/**
	 * Switches the current scene to the specified FXML file.
	 * 
	 * @param fxmlFile The FXML file name (e.g., "login.fxml", "register.fxml")
	 */

	public static void switchScene(String fxmlFile) {
		if (primaryStage == null) {
			System.err.println("Primary stage not set in SceneManager");
			return;
		}
		
		try {
			Parent root = FXMLLoader.load(SceneManager.class.getResource("/fxml/" + fxmlFile));
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch (IOException e) {
			System.err.println("Failed to load scene: " + fxmlFile);
			e.printStackTrace();
		}
	}
}
