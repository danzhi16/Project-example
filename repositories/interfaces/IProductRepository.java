package repositories.interfaces;

import models.Product;
import java.util.List;

public interface IProductRepository {
    boolean createProduct(Product product);
    List<Product> getProductById(int id);
    List<Product> getAllProducts();
    boolean updateProduct(Product product);
    boolean deleteProduct(int id);
    List<Product> getProductsBySellerId(int sellerId);
    List<Product> getProductsByCategory(int categoryId);

    List<Product> getSellerProducts();
}
