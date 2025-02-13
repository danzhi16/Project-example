package repositories;

import Data.Interfaces.IDB;
import models.Category;
import repositories.interfaces.ICategoryRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements ICategoryRepository {
    private final IDB db;

    public CategoryRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createCategory(Category category) {
        if (category == null || category.getName().trim().isEmpty()) {
            System.out.println("❌ Error: Category name cannot be empty.");
            return false;
        }

        String sql = "INSERT INTO categories (name) VALUES (?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, category.getName());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        Category newCategory = new Category(generatedId, category.getName());  // ✅ Creating a new Category object
                        System.out.println("✅ Category created: " + newCategory.getName() + " (ID: " + generatedId + ")");
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while creating category: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM categories WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Category(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while fetching category: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories ORDER BY id ASC";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while fetching categories list: " + e.getMessage());
        }
        return categories;
    }

    @Override
    public boolean updateCategory(Category category) {
        return false;
    }

    @Override
    public boolean deleteCategory(int id) {
        if (!categoryExists(id)) {
            System.out.println("❌ Error: Category with ID " + id + " not found.");
            return false;
        }

        String sql = "DELETE FROM categories WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while deleting category: " + e.getMessage());
        }
        return false;
    }

    private boolean categoryExists(int id) {
        String sql = "SELECT COUNT(*) FROM categories WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while checking category existence: " + e.getMessage());
        }
        return false;
    }
}
