package controllers;

import controllers.interfaces.IProductController;
import models.Product;
import repositories.interfaces.IProductRepository;
import controllers.interfaces.ICategoryController;

import java.util.List;

public class ProductController implements IProductController {

    private final IProductRepository repo;
    private ICategoryController categoryController = null;

    public ProductController(IProductRepository repo) {
        this.repo = repo;
        this.categoryController = categoryController;
    }

    @Override
    public String createProduct(Product product) {
        if (product == null || product.getName().trim().isEmpty()) {
            return "❌ Error: Product name cannot be empty.";
        }
        boolean created = repo.createProduct(product);
        return created ? "✅ Product successfully created: " + product.getName() : "❌ Error creating product.";
    }

    @Override
    public String getProductById(int id) {
        Product product = (Product) repo.getProductById(id);
        return (product != null) ? "Product found:\n" + product : "Product not found.";
    }

    @Override
    public String getAllProducts() {
        List<Product> products = repo.getAllProducts();
        if (products.isEmpty()) {
            return "⚠ No products available.";
        }

        StringBuilder response = new StringBuilder("All products:\n");
        for (Product product : products) {
            response.append(product).append("\n");
        }
        return response.toString();
    }

    @Override
    public String deleteProduct(int id) {
        boolean deleted = repo.deleteProduct(id);
        return deleted ? "✅ Product successfully deleted." : "❌ Error deleting product.";
    }

    @Override
    public String getSellerProducts() {
        List<Product> products = repo.getSellerProducts();
        if (products.isEmpty()) {
            return "⚠ The seller has no products.";
        }

        StringBuilder response = new StringBuilder("Seller's products:\n");
        for (Product product : products) {
            response.append(product).append("\n");
        }
        return response.toString();
    }

    @Override
    public String addProduct(String name, double price, String categoryName) {
        int categoryId = getCategoryIdByName(categoryName);
        if (categoryId == -1) {
            return "❌ Error: Category '" + categoryName + "' not found.";
        }

        Product product = new Product(name, price, categoryId);
        boolean created = repo.createProduct(product);
        return created ? "✅ Product added: " + product : "❌ Error adding product.";
    }

    @Override
    public String getProductsByCategory(int categoryId) {
        List<Product> products = repo.getProductsByCategory(categoryId);
        if (products.isEmpty()) {
            return "⚠ No products in this category.";
        }

        StringBuilder response = new StringBuilder("Products in category ID " + categoryId + ":\n");
        for (Product product : products) {
            response.append(product).append("\n");
        }
        return response.toString();
    }

    private int getCategoryIdByName(String categoryName) {
        List<String> categories = categoryController.getAllCategories().lines().toList();
        for (String category : categories) {
            if (category.toLowerCase().contains(categoryName.toLowerCase())) {
                try {
                    return Integer.parseInt(category.split("ID: ")[1].split(",")[0]);
                } catch (Exception e) {
                    return -1;
                }
            }
        }
        return -1;
    }
}
