package controllers;

import controllers.interfaces.IProductController;
import models.Product;
import repositories.interfaces.IProductRepository;

import java.util.List;

public class ProductController implements IProductController {

    private final IProductRepository repo;

    public ProductController(IProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public String createProduct(Product product) {
        boolean created = repo.createProduct(product);
        return (created) ? "Product was created successfully" : "Product creation failed";
    }

    @Override
    public String getProductById(int id) {
        List<Product> products = repo.getProductById(id); // Получаем список продуктов

        if (products.isEmpty()) {
            return "No products found for this category.";
        }

        StringBuilder response = new StringBuilder("Products in category:\n");
        for (Product product : products) {
            response.append(product.toString()).append("\n");
        }

        return response.toString();
    }

    @Override
    public String getAllProducts() {
        List<Product> products = repo.getAllProducts();
        if (products.isEmpty()) {
            return "No products available.";
        }

        StringBuilder response = new StringBuilder("All Products:\n");
        for (Product product : products) {
            response.append(product.toString()).append("\n");
        }
        return response.toString();
    }

    @Override
    public String deleteProduct(int id) {
        boolean deleted = repo.deleteProduct(id);
        return (deleted) ? "Product was deleted successfully" : "Product deletion failed";
    }

    @Override
    public boolean getSellerProducts() {
        return false; // TODO: Implement seller-specific product fetching logic
    }
}