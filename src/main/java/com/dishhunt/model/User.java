package com.dishhunt.model;

import java.time.LocalDate;
import java.util.List;

public class User {
	private int id;
	private String username;
	private String password;
	private String name;
	private String bio;
	private String nationality;
	private LocalDate joinDate;
	private List<Recipe> contributions;
	private List<Recipe> favourites;
	private String profilePicturePath;
}
