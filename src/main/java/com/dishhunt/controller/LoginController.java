package com.dishhunt.controller;

import com.dishhunt.model.User;
import com.dishhunt.service.AuthService;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
	@FXML private Label messageLabel;
	
	private final AuthService authService = new AuthService();
	
	@FXML
	public void handleLogin() {
		String username = usernameField.getText();
		String password = passwordField.getText();
		
		User user = authService.login(username, password);
		if (user != null) {
			com.dishhunt.util.SessionManager.setCurrentUser(user);
			messageLabel.setText("Welcome, " + username + "!");
			com.dishhunt.util.SceneManager.switchScene("home.fxml");
		} else {
			messageLabel.setText("Invalid credentials.");
		}
	}
	
	@FXML
	public void goToRegister() {
		com.dishhunt.util.SceneManager.switchScene("register.fxml");
	}
}
