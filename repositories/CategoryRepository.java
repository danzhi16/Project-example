package repositories;

import Data.Interfaces.IDB;
import models.Category;
import models.Product;
import repositories.interfaces.ICategoryRepository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CategoryRepository implements ICategoryRepository {
    private final IDB db;
    private final List<Category> categories = new ArrayList<>();

    public CategoryRepository(IDB db){
        this.db = db;
    }

        @Override
        public boolean createCategory(Category category) {
            return categories.add(category);
        }

        @Override
        public Category getCategoryById(int id) {
            return categories.stream()
                    .filter(category -> category.getId() == id)
                    .findFirst()
                    .orElse(null);
        }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name FROM categories";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int categoryId = rs.getInt("id");
                String categoryName = rs.getString("name");

                // Создаем объект Category
                Category category = new Category(categoryId, categoryName);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public List<Product> getProductById(int categoryId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, description, price, category_id FROM products WHERE category_id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("id");
                String productName = rs.getString("name");
                String description = rs.getString("description");
                int price = rs.getInt("price");

                Product product = new Product(productId, productName, description, price, categoryId);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

        @Override
        public boolean deleteCategory(int id) {
            return categories.removeIf(category -> category.getId() == id);
        }


public List<Product> getProducts() {
    List<Product> products = new ArrayList<>();
    String query = "SELECT p.id AS product_id, p.name AS product_name, p.category_id, c.name AS category_name " +
            "FROM products p LEFT JOIN categories c ON p.category_id = c.id";

    try (Connection conn = db.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int productId = rs.getInt("product_id");
            String productName = rs.getString("product_name");
            int categoryId = rs.getInt("category_id");
            String categoryName = rs.getString("category_name");


        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return products;
}

}