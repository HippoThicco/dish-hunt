package com.dishhunt.model;

import java.time.LocalDateTime;
import java.util.List;

public class Recipe {
    private int id;
    private Dish dish;               // optional
    private User author;            // references users table
    private String title;
    private String description;
    private int calories;
    private List<Ingredient> ingredients;
    private String instructions;
    private LocalDateTime createdAt;

    // Constructors
    public Recipe() {}

    public Recipe(int id, User author, Dish dish, String title, String description,
                  int calories, List<Ingredient> ingredients, String instructions,
                  LocalDateTime createdAt) {
        this.id = id;
        this.author = author;
        this.dish = dish;
        this.title = title;
        this.description = description;
        this.calories = calories;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
