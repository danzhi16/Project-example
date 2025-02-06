package repositories;

import Data.Interfaces.IDB;
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
    public boolean createUser(String username, String password, boolean gender){
        return createUser(new User(username, password, gender));
    }


    @Override
    public boolean createUser(User user) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            String sql = "INSERT INTO users(username, password, gender) VALUES (?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            st.setBoolean(3, user.isGender());
            st.execute();

            return true;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public User getUserById(int id) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getBoolean("gender")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        Connection conn = null;
        try {
            conn = db.getConnection();
            String sql = "SELECT * FROM users";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getBoolean("gender")
                ));
            }
            return users;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public boolean deleteUser(int id) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);

            int affectedRows = st.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, username);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getBoolean("gender")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }
}