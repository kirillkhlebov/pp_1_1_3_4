package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection cnct;

    {
        try {
            cnct = Util.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Statement stmt = cnct.createStatement()) {
            String sql = "CREATE TABLE `usersdatabase`.`users` (\n" +
                    "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NULL,\n" +
                    "  `lastname` VARCHAR(45) NULL,\n" +
                    "  `age` TINYINT NULL,\n" +
                    "  PRIMARY KEY (`id`));";
            stmt.executeUpdate(sql);
            System.out.println("Users table is created");
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Users table is already exist");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement stmt = cnct.createStatement()) {
            String sql = "DROP TABLE `usersdatabase`.`users`;";
            stmt.executeUpdate(sql);
            System.out.println("Users table is deleted");
        } catch (SQLSyntaxErrorException e) {
            System.out.println("There is no Users table in database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement stmt = cnct
                .prepareStatement("INSERT INTO `usersdatabase`.`users` (`name`, `lastname`, `age`) VALUES (?, ?, ?);")) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.executeUpdate();
            System.out.println(String.format("User with name - %s is added to database", name));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement stmt = cnct
                .prepareStatement("DELETE FROM `usersdatabase`.`users` WHERE `id` = ?;")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            System.out.printf(String.format("User with id = %d is deleted", id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (PreparedStatement stmt = cnct
                .prepareStatement("SELECT * FROM `usersdatabase`.`users`;");) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        try (PreparedStatement stmt = cnct
                .prepareStatement("DELETE FROM `usersdatabase`.`users`;")) {
            stmt.executeUpdate();
            System.out.println("All rows from Users table are removed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
