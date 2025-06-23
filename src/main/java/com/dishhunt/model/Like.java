package com.dishhunt.model;

public class Like {
	private int id;
	private User user;
	private Recipe recipe;

	public Like(User user, Recipe recipe) {
		this.user = user;
		this.recipe = recipe;
	}
}
