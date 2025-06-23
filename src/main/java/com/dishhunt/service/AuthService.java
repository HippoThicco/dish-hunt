package com.dishhunt.service;

import com.dishhunt.dao.UserDAO;
import com.dishhunt.model.User;
import com.dishhunt.util.PasswordUtil;

/**
 * @param username The entered username.
 * @param password The entered plaintext password.
 * @return The authenticated User object if successful; null otherwise;
 * 
 */
public class AuthService {
	private final UserDAO userDAO;
	
	public AuthService() {
		this.userDAO = new UserDAO();
	}
	
	public User login(String username, String password) {
		User user = userDAO.findByUsername(username);
		
		if (user != null && PasswordUtil.verify(password, user.getHashedPassword())) {
			return user;
		}
		return null;
	}
	public boolean register(User newUser) {
		if (userDAO.existsbyUsername(newUser.getUsername())) {
			return false;
		}
		
		String hashedPassword = PasswordUtil.hash(newUser.getHashedPassword());
		newUser.setHashedPassword(hashedPassword);
		
		return userDAO.save(newUser);
	}
}
