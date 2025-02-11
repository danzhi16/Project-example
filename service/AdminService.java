package service;

public class AdminService {
    private static final String ADMIN_PASSWORD = "artyomgenius"; // Пароль должен быть точным

    public static boolean verifyAdminPassword(String password) {
        return ADMIN_PASSWORD.equals(password.trim());
    }
}
