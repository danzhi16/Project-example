package service;

import models.User;
import models.Role;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static List<User> users = new ArrayList<>();

    public static void addUser(User user) {
        users.add(user);
    }

    public static User getUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public static void deleteUser(int id) {
        users.removeIf(user -> user.getId() == id);
    }

    public static List<User> getAllUsers() {
        return users;
    }
}
