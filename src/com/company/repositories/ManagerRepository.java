package com.company.repositories;

import com.company.data.interfaces.IDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManagerRepository {

    private final IDB db;

    public ManagerRepository(IDB db) {
        this.db = db;
    }

    public boolean authenticate(String username, String password) {

        String sql =
                "SELECT manager_id FROM managers WHERE username=? AND password=?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, username);
            st.setString(2, password);

            ResultSet rs = st.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Manager auth error: " + e.getMessage());
        }

        return false;
    }
}