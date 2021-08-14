package com.epam.esm.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {
    static final String url = "jdbc:postgresql://localhost:5432/RestApi";
    static final String dbUser = "postgres";
    static final String dbPassword = "123";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
        return connection;
    }
}
