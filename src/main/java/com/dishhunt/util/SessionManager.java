package com.dishhunt.util;

import com.dishhunt.model.User;

public class SessionManager {
	private static User currentUser;
	
	public static User getCurrentUser() {
		return currentUser;
	}
	
	public static void setCurrentUser(User user) {
		currentUser = user;
	}
	
	public static void clearSession() {
		currentUser = null;
	}
}
