package com.dishhunt.controller;

import com.dishhunt.service.AuthService;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController {
	private final AuthService authService = new AuthService();
	
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
	@FXML private Label messageLabel;
	
	@FXML
	public void handleRegister() {
		String username = usernameField.getText();
		String password = passwordField.getText();
		
		boolean success = authService.register(username, password);
		
		if (success) {
			messageLabel.setText("Registered successfully!");
		} else {
			messageLabel.setText("Username already exists or invalid.");
		}
	}
	
	@FXML
	public void goToLogin() {
		com.dishhunt.util.SceneManager.switchScene("login.fxml");
	}
}
