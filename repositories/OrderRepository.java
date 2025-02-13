package repositories;

import Data.Interfaces.IDB;
import models.Order;
import repositories.interfaces.IOrderRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements IOrderRepository {
    private final IDB db;

    public OrderRepository(IDB db) {
        this.db = db;
    }

    @Override
    public Object createOrder(Order order) {
        if (order == null || order.getUserId() <= 0 || order.getAddress().trim().isEmpty()) {
            System.out.println("❌ Error: Invalid order data.");
            return null;
        }

        String sql = "INSERT INTO orders (date, user_id, address) VALUES (?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, new java.sql.Date(order.getDate().getTime()));
            stmt.setInt(2, order.getUserId());
            stmt.setString(3, order.getAddress());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        System.out.println("✅ Order created with ID: " + generatedId);
                        return new Order(generatedId, order.getDate(), order.getUserId(), order.getAddress());  // ✅ Fix: Returning a new Order object
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while creating order: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Order getOrderById(int id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Order(
                        rs.getInt("id"),
                        rs.getDate("date"),
                        rs.getInt("user_id"),
                        rs.getString("address")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while fetching order: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY date DESC";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getDate("date"),
                        rs.getInt("user_id"),
                        rs.getString("address")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while fetching orders list: " + e.getMessage());
        }
        return orders;
    }

    @Override
    public boolean updateOrder(Order order) {
        String sql = "UPDATE orders SET date = ?, user_id = ?, address = ? WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(order.getDate().getTime()));
            stmt.setInt(2, order.getUserId());
            stmt.setString(3, order.getAddress());
            stmt.setInt(4, order.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while updating order: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteOrder(int id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while deleting order: " + e.getMessage());
        }
        return false;
    }

    public String getFullOrderDescription(int orderId) {
        String sql = "SELECT o.id AS order_id, o.date, u.username, u.email, " +
                "p.name AS product_name, oi.quantity, p.price " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.id " +
                "JOIN order_items oi ON o.id = oi.order_id " +
                "JOIN products p ON oi.product_id = p.id " +
                "WHERE o.id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            StringBuilder result = new StringBuilder("Order Details:\n");
            while (rs.next()) {
                result.append("Order ID: ").append(rs.getInt("order_id"))
                        .append("\nDate: ").append(rs.getDate("date"))
                        .append("\nCustomer: ").append(rs.getString("username"))
                        .append(" (").append(rs.getString("email")).append(")\n")
                        .append("Product: ").append(rs.getString("product_name"))
                        .append("\nQuantity: ").append(rs.getInt("quantity"))
                        .append("\nPrice: ").append(rs.getDouble("price"))
                        .append("\n------------------------\n");
            }
            return result.toString();
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while fetching order details: " + e.getMessage());
        }
        return "Order not found.";
    }
}
