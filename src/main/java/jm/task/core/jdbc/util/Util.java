package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/usersdatabase", "root", "root");
        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }
}
