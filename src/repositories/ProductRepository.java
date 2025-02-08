package repositories;

import Data.Interfaces.IDB;
import models.Product;
import repositories.interfaces.IProductRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements IProductRepository {
    private final IDB db;

    public ProductRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createProduct(Product product) {
        if (!isValidProduct(product)) {
            System.out.println("Validation failed: Invalid product data.");
            return false;
        }

        String sql = "INSERT INTO products (name, description, category_id, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setInt(3, product.getCategoryId());
            stmt.setDouble(4, product.getPrice());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Product> getProductById(int id) {
        List<Product> products = new ArrayList<>();

        // Example logic to retrieve products based on category id
        // Assuming you query the database and get a result set
        String query = "SELECT id, name, description, price FROM products WHERE category_id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("id");
                String productName = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");

                // Add the product to the list
                products.add(new Product(productId, productName, description, (int) price, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.description, p.price, c.id AS category_id, c.name AS category_name " +
                "FROM products p " +
                "JOIN categories c ON p.category_id = c.id";

        try (Connection conn = db.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getDouble("price")
                        // Добавляем название категории
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return products;
    }

    @Override
    public boolean deleteProduct(int id) {
        if (id <= 0) {
            System.out.println("Validation failed: Product ID must be positive.");
            return false;
        }

        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    private boolean isValidProduct(Product product) {
        return product != null &&
                product.getName() != null && !product.getName().trim().isEmpty() &&
                product.getPrice() > 0 &&
                product.getCategoryId() > 0;
    }
}
