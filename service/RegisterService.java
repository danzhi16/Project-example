package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import models.User;
import models.Role;
import service.UserService;

public class RegisterService {
    private static String ADMIN_PASSWORD;

    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("config.properties"));
            ADMIN_PASSWORD = properties.getProperty("admin.password");
            System.out.println("DEBUG: Admin password loaded: " + ADMIN_PASSWORD); // Проверка загрузки пароля
        } catch (IOException e) {
            System.out.println("Error loading config file: " + e.getMessage());
            ADMIN_PASSWORD = "defaultAdminPassword"; // Запасной пароль
        }
    }

    public static void registerUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter role (CUSTOMER, SELLER, or ADMIN): ");
        String roleInput = scanner.nextLine().toUpperCase();

        Role role;
        try {
            role = Role.valueOf(roleInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role! Registration failed.");
            return;
        }

        // **Исправленная проверка**
        if (role == Role.ADMIN) {
            System.out.print("Enter admin password: "); // Теперь реально запрашивает
            String adminPassword = scanner.nextLine();

            System.out.println("DEBUG: Entered admin password: " + adminPassword);
            System.out.println("DEBUG: Correct admin password: " + ADMIN_PASSWORD);

            if (!adminPassword.equals(ADMIN_PASSWORD)) {
                System.out.println("Incorrect admin password! Registration failed.");
                return;
            }
        }

        // **Создание пользователя после успешной проверки**
        User newUser = new User(username, password, email, role);
        UserService.addUser(newUser);
        System.out.println("Registration successful! User ID: " + newUser.getId());
    }
}
