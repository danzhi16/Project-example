package repositories;

import Data.Interfaces.IDB;
import models.Category;
import models.Product;
import repositories.interfaces.ICategoryRepository;
import repositories.interfaces.IProductRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        @Override
        public List<Category> getAllCategories() {
        Connection conn = null;
        try{
            conn = db.getConnection();
            String sql = "SELECT * FROM categories";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<Category> categories = new ArrayList<>();
            while (rs.next()){
                Category category = new Category(rs.getInt("id"),
                        rs.getString("name"));
                categories.add(category);
            }
            return categories;
        }catch (SQLException e){
            System.out.println("sql error: " + e.getMessage());
        }
        return null;
    }


        @Override
        public boolean deleteCategory(int id) {
            return categories.removeIf(category -> category.getId() == id);
        }

}