package models;

import java.io.Serializable;

public class User implements Serializable { // Добавили Serializable
    private static final long serialVersionUID = 1L; // Версия класса для сериализации

    private int id;
    private String username;
    private String password;
    private String email;
    private Role role;

    public User(int username, String password, String email, String role) {
        this.username = String.valueOf(username);
        this.password = password;
        this.email = email;
        this.role = Role.valueOf(role);
    }

    public User(String username, String password, String email, Role role) {

    }

    private static int generateUserId() {
        return (int) (Math.random() * 10000); // Генерация случайного ID
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

    @Override
    public String toString() {
        return "[ID: " + id + ", Username: " + username + ", Email: " + email + ", Role: " + role + "]";
    }

    public void setId(int anInt) {
    }
}
