package repositories;

import Data.Interfaces.IDB;
import models.Product;
import models.User;
import repositories.interfaces.IProductRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements IProductRepository {
    private final IDB db;

    public ProductRepository(IDB db){
        this.db = db;
    }
    @Override
    public boolean createProduct(Product product) {
        return true;
    }

    @Override
    public Product getProductById(int id) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        Connection conn = null;
        try{
            conn = db.getConnection();
            String sql = "SELECT * FROM products";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<Product> products = new ArrayList<>();
            while (rs.next()){
                Product product = new Product(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getDouble("price"));
                products.add(product);
            }
            return products;
        }catch (SQLException e){
            System.out.println("sql error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteProduct(int id) {
        return true;
    }
}
