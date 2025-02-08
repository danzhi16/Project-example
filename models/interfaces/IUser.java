package models.interfaces;

import models.User;
import java.util.List;

public interface IUser {
    User createUser(String username, String password, String email, String role);
    User getUserById(int id);
    List<User> getAllUsers();
    boolean updateUser(int id, String username, String password, String email, String role);
    boolean deleteUser(int id);
}
