package jm.task.core.jdbc.util;
import java.sql.*;
import com.mysql.cj.jdbc.Driver;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/mytest?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Afybkm06061998";

    public static Connection getConnection() {

        Connection connection = null;
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("_______________________");
                System.out.println("Соединение установлено");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Connection failed");
        }
        return connection;
    }
}
