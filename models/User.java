package models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static int idCounter = 1;
    private int id;
    private String username;
    private String password;
    private String email;
    private Role role;

    public static List<User> users = new ArrayList<>();

    // Конструктор без id (для создания нового пользователя)
    public User(String username, String password, String email, Role role) {
        this.id = idCounter++;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        users.add(this);
    }

    // Новый конструктор с id (чтобы загружать пользователей из БД)
    public User(int id, String username, String password, String email, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    @Override
    public String toString() {
        return "ID: " + id + ", Username: " + username + ", Email: " + email + ", Role: " + role;
    }

}
