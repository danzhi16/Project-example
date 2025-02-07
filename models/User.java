package models;

import models.interfaces.IUser;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class User implements IUser {
    private static List<User> users = new ArrayList<>();
    private static int idCounter = 1;

    private int id;
    private String username;
    private String password;
    private String email;
    private String role;

    public User() {}

    public User(String username, String password, String email, String role) {
        this.id = idCounter++;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        users.add(this);
    }

    public User(int id, String username, String password, String email, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @Override
    public User createUser(String username, String password, String email, String role) {
        return new User(username, password, email, role);
    }

    @Override
    public User getUserById(int id) {
        Optional<User> user = users.stream().filter(u -> u.getId() == id).findFirst();
        return user.orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public boolean updateUser(int id, String username, String password, String email, String role) {
        for (User user : users) {
            if (user.getId() == id) {
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                user.setRole(role);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteUser(int id) {
        return users.removeIf(user -> user.getId() == id);
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
