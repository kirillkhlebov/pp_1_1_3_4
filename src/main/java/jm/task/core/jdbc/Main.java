package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("John", "Green", (byte) 34);
        userService.saveUser("Ivan", "Ivanov", (byte) 45);
        userService.saveUser("Anna", "Dow", (byte) 22);
        userService.saveUser("Monica", "Brown", (byte) 66);
        List<User> userList = userService.getAllUsers();
        for (User user :
                userList) {
            System.out.println(user.toString());
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
        try {
            Util.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
