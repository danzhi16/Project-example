package controllers;

import controllers.interfaces.IUserController;
import models.User;
import repositories.interfaces.IUserRepository;

import java.util.List;

public class UserController implements IUserController {

    private final IUserRepository repo;

    public UserController(IUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public String createUser(String username, String password, String email, String role) {
        User user = new User(username, password, email, role);
        boolean created = repo.createUser(user);
        return (created) ? "User was created successfully" : "User creation failed";
    }


    @Override
    public String getUserById(int id) {
        User user = repo.getUserById(id);
        return (user == null) ? "User was not found" : user.toString();
    }

    @Override
    public String getAllUsers() {
        List<User> users = repo.getAllUsers();
        if (users.isEmpty()) {
            return "No users found.";
        }

        StringBuilder response = new StringBuilder();
        for (User user : users) {
            response.append(user.toString()).append("\n");
        }
        return response.toString();
    }

    @Override
    public String deleteUser(int id) {
        boolean deleted = repo.deleteUser(id);
        return (deleted) ? "User was deleted successfully" : "User deletion failed";
    }

    @Override
    public boolean loginUser(String username, String password) {
        User user = repo.getUserByUsername(username);
        if (user == null) {
            return false; // User not found
        }
        return user.getPassword().equals(password);
    }

    @Override
    public String getUserRole(String username) {
        return "";
    }
}