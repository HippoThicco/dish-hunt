package com.dishhunt.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
	
	public User(int id, String username, String hashedPassword, LocalDate joinDate) {
		this.id = id;
		this.username = username;
		this.hashedPassword = hashedPassword;
		this.joinDate = joinDate;
	}
	
	public User(String username, String hashedPassword) {
		this(0, username, hashedPassword, null);
	}
	
	public User() {
	}

	public String getHashedPassword() {
		return hashedPassword;
	}
	
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public String getUsername() {
		return this.username;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public List<Recipe> getContributions() {
		return contributions;
	}

	public void setContributions(List<Recipe> contributions) {
		this.contributions = contributions;
	}

	public List<Recipe> getFavourites() {
		return favourites;
	}

	public void setFavourites(List<Recipe> favourites) {
		this.favourites = favourites;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
