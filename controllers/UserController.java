package controllers;

import controllers.interfaces.IUserController;
import models.Role;
import models.User;
import repositories.interfaces.IUserRepository;

import java.util.List;

public class UserController implements IUserController {
    private final IUserRepository repo;
    private static final String ADMIN_PASSWORD = "artyomgenius"; // Пароль админа

    public UserController(IUserRepository repo) {
        this.repo = repo;
    }

    // Метод для создания пользователя с возможностью регистрировать ADMIN
    @Override
    public String createUser(String username, String password, String email, String role, String adminPassword) {
        try {
            Role userRole = Role.valueOf(role.toUpperCase()); // Приводим строку к enum

            // Проверка пароля при регистрации ADMIN
            if (userRole == Role.ADMIN) {
                if (!verifyAdminPassword(adminPassword)) {
                    return "Incorrect admin password! Registration failed.";
                }
            }

            // Создаём пользователя без ID, потому что ID присваивается в БД
            User user = new User(username, password, email, userRole);

            boolean created = repo.createUser(user); // Записываем в БД

            return created ? "User was created successfully" : "User creation failed";
        } catch (IllegalArgumentException e) {
            return "Invalid role! Please use CUSTOMER, SELLER, or ADMIN.";
        }
    }

    // Получение пользователя по ID
    @Override
    public User getUserById(int id) {
        return repo.getUserById(id);
    }

    // Получение всех пользователей
    @Override
    public String getAllUsers() {
        List<User> users = repo.getAllUsers();
        if (users.isEmpty()) return "No users found.";

        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            sb.append("ID: ").append(user.getId())
                    .append(", Username: ").append(user.getUsername())
                    .append(", Email: ").append(user.getEmail())
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
            System.out.println("DEBUG: User not found - " + username);
            return false;
        }

        System.out.println("DEBUG: Stored password = " + user.getPassword());
        System.out.println("DEBUG: Entered password = " + password);

        if (user.getPassword().equals(password)) {
            return true;
        } else {
            System.out.println("DEBUG: Password mismatch!");
            return false;
        }
    }


    // Получение роли пользователя по имени
    @Override
    public String getUserRole(String username) {
        User user = repo.getUserByUsername(username);
        return (user != null) ? user.getRole().name() : "User not found.";
    }

    // Проверка пароля админа
    @Override
    public boolean verifyAdminPassword(String adminPassword) {
        return ADMIN_PASSWORD.equals(adminPassword.trim());
    }
}
