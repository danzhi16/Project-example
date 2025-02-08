package repositories.interfaces;

import models.Product;

import java.util.List;

public interface IProductRepository {
    boolean createProduct(Product product);
    List<Product> getProductById(int id);
    List<Product> getAllProducts();
    boolean deleteProduct(int id);

    List<Product> getProductsBySellerId(int sellerId);
}

