package com.DAO;

import com.connection.DBConnection;
import com.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public String authenticateUser(String login_id) {
        String sql = "SELECT * FROM users1 WHERE login_id = ?";
        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            stmt.setString(1, login_id);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                return res.getString("password");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
