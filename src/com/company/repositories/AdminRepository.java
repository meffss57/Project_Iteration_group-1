package com.company.repositories;

import com.company.data.interfaces.IDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminRepository {

    private final IDB db;

    public AdminRepository(IDB db) {
        this.db = db;
    }

    public boolean authenticate(String username, String password) {
        String sql = "SELECT id FROM admins WHERE username = ? AND password = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, username);
            st.setString(2, password);

            ResultSet rs = st.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("Admin auth error: " + e.getMessage());
        }

        return false;
    }
}
