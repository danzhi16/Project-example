package models.interfaces;

import models.Product;
import java.util.List;

public interface IProduct {
    Product createProduct(String name, String description, int categoryId, double price);

    Product createProduct(String name, String description, double price, int categoryId);
    Product getProductById(int id);
    List<Product> getAllProducts();
    boolean updateProduct(int id, String name, String description, double price, int categoryId);
    boolean deleteProduct(int id);

    void setId(int anInt);
}
