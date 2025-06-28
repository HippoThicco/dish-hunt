package com.dishhunt.dao;

import com.dishhunt.model.User;
import com.dishhunt.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;

public class UserDAO {

    public void insertUser(User user) {
        String sql = """
            INSERT INTO users (
                username, hashed_password, name, bio,
                nationality, join_date, profile_picture_path
            ) VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getHashedPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getBio());
            stmt.setString(5, user.getNationality());
            stmt.setDate(6, Date.valueOf(user.getJoinDate()));
            stmt.setString(7, user.getProfilePicturePath());

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
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("hashed_password"),
                    rs.getDate("join_date").toLocalDate()
                );

                user.setName(rs.getString("name"));
                user.setBio(rs.getString("bio"));
                user.setNationality(rs.getString("nationality"));
                user.setProfilePicturePath(rs.getString("profile_picture_path"));

                return user;
            }

        } catch (SQLException e) {
            System.err.println("❌ Failed to fetch user:");
            e.printStackTrace();
        }

        return null;
    }
}
