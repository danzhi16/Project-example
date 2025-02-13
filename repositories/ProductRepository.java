package repositories;

import Data.Interfaces.IDB;
import models.Product;
import repositories.interfaces.IProductRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductRepository implements IProductRepository {
    private final IDB db;

    public ProductRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createProduct(Product product) {
        if (!isValidProduct(product)) {
            System.out.println("❌ Error: Invalid product data.");
            return false;
        }

        String sql = "INSERT INTO products (name, description, category_id, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setInt(3, product.getCategoryId());
            stmt.setDouble(4, product.getPrice());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        product.setId(rs.getInt(1));
                        System.out.println("✅ Product created: " + product.getName());
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while creating product: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Product> getProductById(int id) {
        String query = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Collections.singletonList(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while fetching product: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id ASC";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while fetching products list: " + e.getMessage());
        }
        return products;
    }

    @Override
    public boolean updateProduct(Product product) {
        return false;
    }

    @Override
    public boolean deleteProduct(int id) {
        if (id <= 0) {
            System.out.println("❌ Error: Invalid product ID.");
            return false;
        }

        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while deleting product: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Product> getProductsBySellerId(int sellerId) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category_id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while fetching products by category: " + e.getMessage());
        }
        return products;
    }

    @Override
    public List<Product> getSellerProducts() {
        return List.of();
    }

    private boolean isValidProduct(Product product) {
        return product != null &&
                product.getName() != null && !product.getName().trim().isEmpty() &&
                product.getPrice() > 0 &&
                product.getCategoryId() > 0;
    }
}
