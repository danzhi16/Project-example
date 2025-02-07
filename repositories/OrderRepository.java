package repositories;

import Data.Interfaces.IDB;
import models.Order;
import repositories.interfaces.IOrderRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements IOrderRepository {
    private final IDB db;

    public OrderRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createOrder(Order order) {
        return true; // Должна быть реализация SQL-запроса
    }

    @Override
    public Order getOrderById(int id) {
        return null; // Должна быть реализация SQL-запроса
    }

    @Override
    public List<Order> getAllOrders() {
        return new ArrayList<>(); // Должна быть реализация SQL-запроса
    }

    @Override
    public boolean deleteOrder(int id) {
        return true; // Должна быть реализация SQL-запроса
    }

    public String getFullOrderDescription(int orderId) {
        String sql = "SELECT o.id AS order_id, o.date, u.username, u.email, p.name AS product_name, oi.quantity, p.price " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.id " +
                "JOIN order_items oi ON o.id = oi.order_id " +
                "JOIN products p ON oi.product_id = p.id " +
                "WHERE o.id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, orderId);
            ResultSet rs = st.executeQuery();

            StringBuilder result = new StringBuilder("Order Details:\n");
            while (rs.next()) {
                result.append("Order ID: ").append(rs.getInt("order_id"))
                        .append(", Date: ").append(rs.getDate("date"))
                        .append(", User: ").append(rs.getString("username"))
                        .append(" (").append(rs.getString("email")).append(")\n")
                        .append("Product: ").append(rs.getString("product_name"))
                        .append(", Quantity: ").append(rs.getInt("quantity"))
                        .append(", Price: ").append(rs.getDouble("price"))
                        .append("\n");
            }
            return result.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Order not found.";
    }
}
