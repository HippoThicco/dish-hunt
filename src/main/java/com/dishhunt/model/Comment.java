package com.dishhunt.model;

import java.time.LocalDateTime;

public class Comment {
	private int id;
	private User author;
	private Recipe recipe;
	private LocalDateTime timestamp;
	private String content;
	
	public Comment(String content, User author, Recipe recipe) {
		this.content = content;
		this.timestamp = LocalDateTime.now();
		this.author = author;
		this.recipe = recipe;
	}
}
