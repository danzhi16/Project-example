package controllers.interfaces;

import models.Product;
import java.util.List;

public interface IProductController {
    String createProduct(Product product);
    String getProductById(int id);
    String getAllProducts();
    String deleteProduct(int id);
    List<Product> getSellerProducts();
}