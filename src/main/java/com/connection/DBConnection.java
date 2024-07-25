package com.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static Connection con = null;
    public static Connection getConnection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer_app?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false","root","root");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return con;
    }
}
