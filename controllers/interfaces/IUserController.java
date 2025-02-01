package controllers.interfaces;

import models.User;

public interface IUserController {
    String createUser(String username, String password, String name, String surname, String gender);
    String getUserById(int id);
    String getAllUsers();
    String deleteUser(int id);

    boolean loginUser(String username, String password);
}