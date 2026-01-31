package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.repositories.interfaces.IUserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepository implements IUserRepository {

    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    // returns success if user login exits , else null (r)
    public Integer login(String username, String password) {
        String sql = "SELECT user_id FROM users WHERE username = ? AND password = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, username);
            st.setString(2, password);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            }

        } catch (Exception e) {
            System.out.println("User login error: " + e.getMessage());
        }

        return null;
    }

    public boolean register(String username, String password) {
        String sql = "INSERT INTO users(username, password) VALUES (?, ?)";
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
