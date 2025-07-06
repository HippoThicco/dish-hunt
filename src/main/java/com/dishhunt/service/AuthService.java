package com.dishhunt.service;

import com.dishhunt.dao.UserDAO;
import com.dishhunt.model.User;
import com.dishhunt.util.PasswordUtil;

import java.time.LocalDate;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    /**
     * Registers a new user by hashing the password and saving the user to the DB.
     */
    public boolean register(String username, String password) {
        if (userDAO.findByUsername(username) != null) {
            System.out.println("Username already exists.");
            return false;
        }

        String hashedPassword = PasswordUtil.hash(password);
        User newUser = new User(username, hashedPassword);
        userDAO.insertUser(newUser);

        System.out.println("User registered.");
        return true;
    }

    /**
     * Logs in a user by checking their credentials.
     */
    public User login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            System.out.println("User not found.");
            return null;
        }

        if (PasswordUtil.verify(password, user.getHashedPassword())) {
            System.out.println("Login successful.");
            return user;
        } else {
            System.out.println("Incorrect password.");
            return null;
        }
    }
}
