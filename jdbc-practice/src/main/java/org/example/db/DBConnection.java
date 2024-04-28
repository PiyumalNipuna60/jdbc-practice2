package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection con;
    private static DBConnection dbConnection;

    public DBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBConnection getInstance() {
        if (dbConnection == null) {
            return dbConnection = new DBConnection();
        } else {
            return dbConnection;
        }
    }

    public Connection getConnection() {
        return con;
    }

}
