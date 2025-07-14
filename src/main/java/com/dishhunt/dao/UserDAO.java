package com.dishhunt.dao;

import com.dishhunt.model.User;
import com.dishhunt.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;

public class UserDAO {

    public void insertUser(User user) {
        String sql = """
            INSERT INTO users (
                username, hashed_password
            ) VALUES (?, ?)
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getHashedPassword());

            stmt.executeUpdate();

            System.out.println("✅ User inserted.");
            
        } catch (SQLException e) {
            System.err.println("❌ Failed to insert user:");
            e.printStackTrace();
        }
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setHashedPassword(rs.getString("hashed_password"));
                user.setName(rs.getString("name"));
                user.setBio(rs.getString("bio"));
                user.setNationality(rs.getString("nationality"));

                return user;
            }

        } catch (SQLException e) {
            System.err.println("❌ Failed to fetch user:");
            e.printStackTrace();
        }

        return null;
    }
}
