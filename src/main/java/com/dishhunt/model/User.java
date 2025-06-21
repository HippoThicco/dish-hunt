package com.dishhunt.model;

import java.time.LocalDate;
import java.util.List;

public class User {
	int id;
	String username;
	String password;
	String name;
	String bio;
	String nationality;
	LocalDate joinDate;
	List<Recipe> contributions;
	List<Recipe> favourites;
	String profilePicturePath;
}
