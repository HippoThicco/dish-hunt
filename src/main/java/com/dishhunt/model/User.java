package com.dishhunt.model;

import java.time.LocalDate;
import java.util.List;

public class User {
	private int id;
	private String username;
	private String hashedPassword;
	private String name;
	private String bio;
	private String nationality;
	private LocalDate joinDate;
	private List<Recipe> contributions;
	private List<Recipe> favourites;
	private String profilePicturePath;
	
	public String getHashedPassword() {
		return hashedPassword;
	}
	
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public String getUsername() {
		return this.username;
	}
}
