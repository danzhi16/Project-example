package service;

import java.io.*;
import java.util.Scanner;
import models.User;
import models.Role;
import java.util.ArrayList;
import java.util.List;

public class RegisterService {
    private static final String FILE_NAME = "users.txt";
    private static List<User> users = new ArrayList<>();

    static {
        loadUsers(); // Загружаем пользователей при старте
    }

    public static void registerUser(Scanner scanner) {
        System.out.print("Enter role (CUSTOMER, SELLER, or ADMIN): ");
        String roleInput = scanner.next().toUpperCase();

        Role role;
        try {
            role = Role.valueOf(roleInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role! Registration failed.");
            return;
        }

        if (role == Role.ADMIN) {
            System.out.print("Enter admin password: ");
            String adminPassword = scanner.next();

            if (!AdminService.verifyAdminPassword(adminPassword)) {
                System.out.println("Incorrect admin password! Registration failed.");
                return;
            }
        }

        scanner.nextLine(); // Чистим буфер

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        if (findUserByUsername(username) != null) {
            System.out.println("Username already taken! Registration failed.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        User newUser = new User(username, password, email, role);
        users.add(newUser);
        saveUsers(); // Сохраняем всех пользователей в файл
        System.out.println("Registration successful! User ID: " + newUser.getId());
    }

    public static User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public static List<User> getAllUsers() {
        return users;
    }

    private static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private static void loadUsers() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            users = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }
}
