package controllers.interfaces;

import models.User;

public interface IUserController {
    String createUser(String username, String password, String gender, String role);
    User getUserById(int id);
    String getAllUsers();
    String deleteUser(int id);
    boolean loginUser(String username, String password);

    String getUserRole(String username);
}