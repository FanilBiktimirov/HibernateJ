package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main{

    public static void main(String[] args) {
       UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan","Ivanov", (byte) 15);
        userService.saveUser("Alex", "Stone", (byte) 20);
        userService.saveUser("Banson", "Pain", (byte) 35);
        userService.saveUser("Piter", "Parker", (byte) 22);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
