package repositories.interfaces;

import models.User;
import java.util.List;

public interface IUserRepository {
    boolean createUser(String username, String password, String email, String role);

    boolean createUser(User user);
    User getUserById(int id);
    User getUserByUsername(String username);
    List<User> getAllUsers();

    boolean deleteUser(int id, User currentUser);

    boolean updateUser(User user);
    boolean deleteUser(int id);
}
