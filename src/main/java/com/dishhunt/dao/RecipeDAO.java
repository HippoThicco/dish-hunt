package com.dishhunt.dao;

import com.dishhunt.model.*;
import com.dishhunt.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO {

    public void insertRecipe(Recipe recipe) {
        String sql = """
            INSERT INTO recipes (user_id, dish_id, title, description, calories, instructions)
            VALUES (?, ?, ?, ?, ?, ?)
            RETURNING recipe_id
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recipe.getAuthor().getId());
            stmt.setInt(2, recipe.getDish() != null ? recipe.getDish().getId() : 0); // fallback 0
            stmt.setString(3, recipe.getTitle());
            stmt.setString(4, recipe.getDescription());
            stmt.setInt(5, recipe.getCalories());
            stmt.setString(6, recipe.getInstructions());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int recipeId = rs.getInt("recipe_id");
                recipe.setId(recipeId);
                insertIngredientsForRecipe(recipeId, recipe.getIngredients(), conn);
            }

            System.out.println("Recipe and ingredients inserted.");

        } catch (SQLException e) {
            System.err.println("Failed to insert recipe:");
            e.printStackTrace();
        }
    }

    private void insertIngredientsForRecipe(int recipeId, List<Ingredient> ingredients, Connection conn) throws SQLException {
        if (ingredients == null || ingredients.isEmpty()) return;

        String sql = "INSERT INTO ingredients (recipe_id, name, quantity, unit) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Ingredient ing : ingredients) {
                stmt.setInt(1, recipeId);
                stmt.setString(2, ing.getName());
                stmt.setDouble(3, ing.getQuantity());
                stmt.setString(4, ing.getUnit());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT * FROM recipes ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Recipe r = mapRecipeFromResultSet(rs);
                r.setIngredients(fetchIngredientsForRecipe(r.getId()));
                recipes.add(r);
            }

        } catch (SQLException e) {
            System.err.println("Failed to fetch recipes:");
            e.printStackTrace();
        }

        return recipes;
    }

    public List<Recipe> getRecipesByUser(int userId) {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT * FROM recipes WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Recipe r = mapRecipeFromResultSet(rs);
                r.setIngredients(fetchIngredientsForRecipe(r.getId()));
                recipes.add(r);
            }

        } catch (SQLException e) {
            System.err.println("Failed to fetch user's recipes:");
            e.printStackTrace();
        }

        return recipes;
    }

    private Recipe mapRecipeFromResultSet(ResultSet rs) throws SQLException {
        Recipe r = new Recipe();
        r.setId(rs.getInt("recipe_id"));
        r.setTitle(rs.getString("title"));
        r.setDescription(rs.getString("description"));
        r.setCalories(rs.getInt("calories"));
        r.setInstructions(rs.getString("instructions"));
        r.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        // Set author (basic user with just ID for now)
        User u = new User();
        u.setId(rs.getInt("user_id"));
        r.setAuthor(u);

        // Set dish (basic dish with just ID for now)
        Dish d = new Dish();
        d.setId(rs.getInt("dish_id"));
        r.setDish(d);

        return r;
    }

    public List<Ingredient> fetchIngredientsForRecipe(int recipeId) {
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = "SELECT * FROM ingredients WHERE recipe_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recipeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ingredient ing = new Ingredient(
                    rs.getString("name"),
                    rs.getDouble("quantity"),
                    rs.getString("unit")
                );
                ingredients.add(ing);
            }

        } catch (SQLException e) {
            System.err.println("Failed to fetch ingredients:");
            e.printStackTrace();
        }

        return ingredients;
    }
}
