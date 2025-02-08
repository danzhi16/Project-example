package controllers.interfaces;

import models.Product;

public interface IProductController {
    String createProduct(Product product);
    String getProductById(int id);
    String getAllProducts();
    String deleteProduct(int id);
}
