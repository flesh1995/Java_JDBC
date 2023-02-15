package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            String sqlQuery = "CREATE TABLE IF NOT EXISTS User (" +
                    " ID INT PRIMARY KEY AUTO_INCREMENT, " +
                    " FIRSTNAME VARCHAR(50) NOT NULL, " +
                    " LASTNAME VARCHAR(30) NOT NULL, " +
                    " AGE INT(3) NOT NULL)";
            statement.executeUpdate(sqlQuery);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException el) {
                el.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            String sqlQuery = "DROP TABLE IF EXISTS User";
            statement.executeUpdate(sqlQuery);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException el) {
                el.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO User (FIRSTNAME, LASTNAME, AGE) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preStatement = connection.prepareStatement(sql);
            preStatement.setString(1, name);
            preStatement.setString(2, lastName);
            preStatement.setByte(3, age);
            preStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException el) {
                el.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM User WHERE ID =" + id;
            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException el) {
                el.printStackTrace();
            }
            e.printStackTrace();e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        User user = new User();
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            String query = "SELECT * from User";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                user.setId(result.getLong(1));
                user.setName(result.getString(2));
                user.setLastName(result.getString(3));
                user.setAge(result.getByte(4));
                users.add(user);
                System.out.println(user);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException el) {
                el.printStackTrace();
            }
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            String sqlQuery = "DELETE FROM User";
            statement.execute(sqlQuery);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException el) {
                el.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
