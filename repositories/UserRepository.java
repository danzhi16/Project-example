package repositories;

import Data.Interfaces.IDB;
import models.Role;
import models.User;
import repositories.interfaces.IUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepository implements IUserRepository {
    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    // Метод для создания пользователя по строковым параметрам
    @Override
    public boolean createUser(String username, String password, String email, String role) {
        try {
            Role userRole = Role.valueOf(role.toUpperCase()); // Приведение к ENUM
            return createUser(new User(username, password, email, userRole));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid role provided: " + role);
            return false;
        }
    }

    // Метод для создания пользователя по объекту User
    @Override
    public boolean createUser(User user) {
        String sql = "INSERT INTO users(username, password, email, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            st.setString(3, user.getEmail());
            st.setString(4, user.getRole().name()); // ENUM сохраняем как строку

            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error in createUser: " + e.getMessage());
        }
        return false;
    }

    // Получение пользователя по ID
    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"), // Теперь загружаем ID
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        Role.valueOf(rs.getString("role")) // Приведение строки к ENUM
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL error in getUserById: " + e.getMessage());
        }
        return null;
    }

    // Получение пользователя по имени
    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, username);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"), // Загружаем ID
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        Role.valueOf(rs.getString("role")) // Приведение строки к ENUM
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL error in getUserByUsername: " + e.getMessage());
        }
        return null;
    }

    // Получение всех пользователей
    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users ORDER BY id ASC"; // Сортировка прямо в SQL
        List<User> users = new ArrayList<>();

        try (Connection conn = db.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        Role.valueOf(rs.getString("role"))
                ));
            }
        } catch (SQLException e) {
            System.out.println("SQL error in getAllUsers: " + e.getMessage());
        }

        return users; // SQL уже отсортировал, так что stream().sorted() не нужен
    }


    // Удаление пользователя по ID (без проверки прав)
    @Override
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, id);
            int affectedRows = st.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("SQL error in deleteUser: " + e.getMessage());
        }
        return false;
    }

    // Удаление пользователя только для ADMIN
    @Override
    public boolean deleteUser(int id, User currentUser) {
        if (currentUser.getRole() != Role.ADMIN) {
            System.out.println("Access denied: Only ADMIN can delete users!");
            return false;
        }

        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, id);
            int affectedRows = st.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("SQL error in deleteUser (Admin check): " + e.getMessage());
        }
        return false;
    }
}
