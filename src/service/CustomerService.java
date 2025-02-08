package service;

import service.interfaces.ICustomerService;
import Data.Interfaces.IDB;

import java.sql.*;

public class CustomerService implements ICustomerService {
    private final IDB db;

    public CustomerService(IDB db) {
        this.db = db;
    }

    @Override
    public int createCustomer(String name, String email) {
        String sql = "INSERT INTO customers (first_name, email) VALUES (?, ?) RETURNING customer_id;";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("customer_id"); // Возвращаем ID нового клиента
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Ошибка
    }

    @Override
    public int createCustomer(Connection conn, String name, String email) {
        return 0;
    }
}
