package repositories;

import Data.Interfaces.IDB;
import models.Role;
import models.User;
import repositories.interfaces.IUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createUser(String username, String password, String email, String role) {
        String sql = "INSERT INTO users(username, password, email, role) VALUES (?, ?, ? ,?)";

        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            st.setString(1, username);
            st.setString(2, password);
            st.setString(3, email);
            st.setString(4, role.toUpperCase()); // ✅ Приводим роль к верхнему регистру

            int affectedRows = st.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        int userId = rs.getInt(1); // ✅ Получаем сгенерированный ID
                        System.out.println("✅ User created with ID: " + userId); // Логирование
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL error in createUser: " + e.getMessage());
        }
        return false;
    }
    @Override
    public boolean createUser(User user) {
        String sql = "INSERT INTO users(username, password, email, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            st.setString(3, user.getEmail());
            st.setString(4, user.getRole().name());

            int affectedRows = st.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getInt(1));
                    }
                }
                return true;
            }
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
                return mapUser(rs);
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
                return mapUser(rs);
            }
        } catch (SQLException e) {
            System.out.println("SQL error in getUserByUsername: " + e.getMessage());
        }
        return null;
    }

    // Проверка, существует ли пользователь
    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, username);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("SQL error in userExists: " + e.getMessage());
        }
        return false;
    }

    // Получение всех пользователей
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, email, role FROM users ORDER BY id ASC"; // ДОбавили ORDER BY id ASC

        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            System.out.println("SQL error in getAllUsers: " + e.getMessage());
        }
        return users;
    }


    // Обновление информации о пользователе
    public boolean updateUser(int id, String newUsername, String newPassword, String newEmail, String newRole) {
        String sql = "UPDATE users SET username = ?, password = ?, email = ?, role = ? WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, newUsername);
            st.setString(2, newPassword);
            st.setString(3, newEmail);
            st.setString(4, newRole);
            st.setInt(5, id);

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error in updateUser: " + e.getMessage());
        }
        return false;
    }

    // Удаление пользователя по ID
    @Override
    public boolean deleteUser(int id) {
        if (!userExistsById(id)) {
            System.out.println("Error: User with ID " + id + " does not exist.");
            return false;
        }

        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error in deleteUser: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteUser(int id, User currentUser) {
        // Проверяем, является ли текущий пользователь администратором
        if (currentUser.getRole() != Role.ADMIN) {
            System.out.println("Access denied: Only ADMIN can delete users!");
            return false;
        }

        // Проверяем, существует ли пользователь с указанным ID
        if (!userExistsById(id)) {
            System.out.println("Error: User with ID " + id + " does not exist.");
            return false;
        }

        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, id);
            return st.executeUpdate() > 0; // Возвращает true, если удаление успешно
        } catch (SQLException e) {
            System.out.println("SQL error in deleteUser (Admin check): " + e.getMessage());
        }
        return false;
    }


    // Проверка, существует ли пользователь по ID
    private boolean userExistsById(int id) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("SQL error in userExistsById: " + e.getMessage());
        }
        return false;
    }

    // Вспомогательный метод для маппинга User из ResultSet
    private User mapUser(ResultSet rs) throws SQLException {
        return new User(
                // Добавлен ID, которого не было в старом коде
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                Role.valueOf(rs.getString("role"))
        );
    }
}
