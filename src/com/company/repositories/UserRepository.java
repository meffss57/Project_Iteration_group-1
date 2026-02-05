package com.company.repositories;

import com.company.Role;
import com.company.data.interfaces.IDB;
import com.company.models.AuthUser;
import com.company.repositories.interfaces.IUserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepository implements IUserRepository {

    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    @Override
    public AuthUser login(String username, String password) {
        String sql = "SELECT user_id, username, role FROM users WHERE username = ? AND password = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, username);
            st.setString(2, password);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("user_id");
                    String u = rs.getString("username");
                    String roleStr = rs.getString("role");

                    Role role = Role.valueOf(roleStr.toUpperCase()); // USER/ADMIN/MANAGER
                    return new AuthUser(id, u, role);
                }
            }

        } catch (Exception e) {
            System.out.println("User login error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean register(String username, String password) {
        String sql = "INSERT INTO users(username, password, role) VALUES (?, ?, 'USER')";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, username);
            st.setString(2, password);
            return st.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("User register error: " + e.getMessage());
        }
        return false;
    }
}
