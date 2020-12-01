package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl extends Util implements UserService {

    Connection connection = getConnection();

    public void createUsersTable() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (id INTEGER AUTO_INCREMENT PRIMARY KEY, \n" +
                "    name VARCHAR(30), \n" +
                "    lastName VARCHAR(30),\n" +
                "    age INTEGER\n" +
                ");";
        try (PreparedStatement preparedStatement = connection.prepareStatement(createUsersTable)) {
            preparedStatement.execute();
            System.out.println("Таблица в базе данных успешно создана.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String drop = "DROP TABLE IF EXISTS users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(drop)) {
            preparedStatement.execute();
            System.out.println("Таблица успешно удалена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUser = "INSERT INTO users (name, lastName, age) Values (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(saveUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            String removeUserById = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(removeUserById);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            System.out.println("User с id: " + id + " успешно удален с базы данных.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            String getAllUsers = "SELECT * FROM users;";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getAllUsers);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
                System.out.println(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String cleanUsersTable = "TRUNCATE TABLE users";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(cleanUsersTable);
            preparedStatement.execute();
            System.out.println("Таблица очищена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
