package controllers;

import controllers.interfaces.IUserController;
import models.Role;
import models.User;
import repositories.interfaces.IUserRepository;

import java.util.List;

public class UserController implements IUserController {
    private final IUserRepository repo;
    private static final String ADMIN_PASSWORD = "secure_admin_123";

    public UserController(IUserRepository repo) {
        this.repo = repo;
    }

    // Метод для создания пользователя с проверкой пароля админа
    @Override
    public String createUser(String username, String password, String email, String role) {
        try {
            Role userRole = Role.valueOf(role.toUpperCase()); // Приводим строку к enum

            // Если создаем администратора, проверяем пароль
            if (userRole == Role.ADMIN) {
                return "Access denied: admin password required!";
            }

            User user = new User(username, password, email, userRole);
            boolean created = repo.createUser(user);
            return created ? "User was created successfully" : "User creation failed";
        } catch (IllegalArgumentException e) {
            return "Invalid role! Please use CUSTOMER, SELLER, or ADMIN.";
        }
    }

    // Перегруженный метод для создания администратора с паролем админа
    public String createAdminUser(String username, String password, String email, String adminPassword) {
        if (!adminPassword.equals(ADMIN_PASSWORD)) {
            return "Access denied: incorrect admin password!";
        }

        User user = new User(username, password, email, Role.ADMIN);
        boolean created = repo.createUser(user);
        return created ? "Admin user created successfully" : "Admin user creation failed";
    }

    // Получение пользователя по ID
    @Override
    public User getUserById(int id) {
        return repo.getUserById(id);
    }

    // Получение списка всех пользователей
    @Override
    public String getAllUsers() {
        List<User> users = repo.getAllUsers();
        if (users.isEmpty()) return "No users found.";

        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            sb.append("ID: ").append(user.getId())
                    .append(", Username: ").append(user.getUsername())
                    .append(", Role: ").append(user.getRole()).append("\n");
        }
        return sb.toString();
    }

    // Удаление пользователя (только для ADMIN)
    @Override
    public String deleteUser(int id) {
        User user = repo.getUserById(id);
        if (user == null) return "User not found.";

        boolean deleted = repo.deleteUser(id);
        return deleted ? "User deleted successfully." : "Failed to delete user.";
    }

    // Логин пользователя
    @Override
    public boolean loginUser(String username, String password) {
        User user = repo.getUserByUsername(username);
        if (user == null) {
            System.out.println("Login failed: user not found.");
            return false;
        }

        System.out.println("Stored password: " + user.getPassword());
        System.out.println("Entered password: " + password);

        return user.getPassword().equals(password);
    }

    // Получение роли пользователя по имени
    @Override
    public String getUserRole(String username) {
        User user = repo.getUserByUsername(username);
        if (user == null) return "User not found.";
        return user.getRole().name();
    }
}
