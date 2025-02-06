package repositories.interfaces;

import models.User;

import java.util.List;

public interface IUserRepository {

    boolean createUser(String username, String password, String email);

    boolean createUser(User user);

    User getUserById(int id);

    List<User> getAllUsers();

    boolean deleteUser(int id);

    User getUserByUsername(String username);
}

