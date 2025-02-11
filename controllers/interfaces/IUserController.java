package controllers.interfaces;

import models.User;

public interface IUserController {
    String createUser(String username, String password, String gender, String role, String adminPassword);
    User getUserById(int id);
    String getAllUsers();
    String deleteUser(int id);
    boolean loginUser(String username, String password);

    String getUserRole(String username);

    boolean verifyAdminPassword(String adminPassword);
}