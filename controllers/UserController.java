package controllers;

import controllers.interfaces.IUserController;
import models.User;
import repositories.interfaces.IUserRepository;
import models.Role;

import java.util.List;

public class UserController implements IUserController {
    private final IUserRepository repo;
    private static final String ADMIN_PASSWORD = "artyomgenius";

    public UserController(IUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public String createUser(String username, String password, String email, String role, String adminPassword) {
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                role == null || role.trim().isEmpty()) {
            return "❌ Error: All fields are required.";
        }

        if (repo.getUserByUsername(username) != null) {
            return "❌ Error: A user with this username already exists.";
        }

        try {
            Role userRole = Role.valueOf(role.toUpperCase());

            if (userRole == Role.ADMIN && !verifyAdminPassword(adminPassword)) {
                return "❌ Error: Incorrect admin password.";
            }

            User user = new User(username, password, email, userRole);
            boolean created = repo.createUser(user);
            return created ? "✅ User successfully created: " + username : "❌ Error creating user.";
        } catch (IllegalArgumentException e) {
            return "❌ Error: Invalid role. Use CUSTOMER, SELLER, or ADMIN.";
        }
    }

    @Override
    public User getUserById(int id) {
        return repo.getUserById(id);
    }

    @Override
    public String getAllUsers() {
        List<User> users = repo.getAllUsers();
        if (users.isEmpty()) return "No users found.";

        StringBuilder sb = new StringBuilder("User list:\n");
        for (User user : users) {
            sb.append(user).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String deleteUser(int id) {
        User user = repo.getUserById(id);
        if (user == null) return "Error: User not found.";

        boolean deleted = repo.deleteUser(id);
        return deleted ? "✅ User deleted." : "❌ Error deleting user.";
    }

    @Override
    public boolean loginUser(String username, String password) {
        User user = repo.getUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public String getUserRole(String username) {
        User user = repo.getUserByUsername(username);
        return (user != null) ? user.getRole().name() : "Error: User not found.";
    }

    @Override
    public boolean verifyAdminPassword(String adminPassword) {
        return ADMIN_PASSWORD.equals(adminPassword.trim());
    }

    public String updateUser(int id, String newUsername, String newEmail) {
        User user = repo.getUserById(id);
        if (user == null) {
            return "Error: User not found.";
        }

        if (newUsername != null && !newUsername.trim().isEmpty()) {
            user.setUsername(newUsername);
        }

        if (newEmail != null && !newEmail.trim().isEmpty()) {
            user.setEmail(newEmail);
        }

        return repo.updateUser(user) ? "✅ User data updated." : "❌ Error updating user data.";
    }

    public String viewProfile(String username) {
        User user = repo.getUserByUsername(username);
        return (user != null) ? "Profile:\n" + user : "Error: User not found.";
    }
}
