package controllers;

import controllers.interfaces.IProductController;
import controllers.interfaces.ICategoryController;
import models.Product;
import repositories.interfaces.IProductRepository;

import java.util.List;

public class ProductController implements IProductController {

    private final IProductRepository repo;
    private ICategoryController categoryController;

    public ProductController(IProductRepository repo) {
        this.repo = repo;
    }

    public void setCategoryController(ICategoryController categoryController) {
        this.categoryController = categoryController;
    }

    @Override
    public String addProduct(String name, double price, String categoryName) {
        if (categoryController == null) {
            return "❌ Error: CategoryController is not initialized.";
        }

        int categoryId = getCategoryIdByName(categoryName);
        if (categoryId == -1) {
            return "❌ Error: Category '" + categoryName + "' not found.";
        }

        Product product = new Product(name, price, categoryId);
        boolean created = repo.createProduct(product);
        return created ? "✅ Product added: " + product : "❌ Error adding product.";
    }

    @Override
    public String createProduct(Product product) {
        if (product == null) {
            return "❌ Error: Invalid product data.";
        }

        boolean created = repo.createProduct(product);
        return created ? "✅ Product added: " + formatProductToString(product) : "❌ Error adding product.";
    }

    @Override
    public String getProductById(int id) {
        Product product = (Product) repo.getProductById(id);
        return (product != null) ? formatProductToString(product) : "❌ Product not found.";
    }

    @Override
    public String getAllProducts() {
        List<Product> products = repo.getAllProducts();
        if (products.isEmpty()) {
            return "⚠ No products available.";
        }

        StringBuilder response = new StringBuilder("All products:\n");
        for (Product product : products) {
            response.append(formatProductToString(product)).append("\n");
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
            response.append(formatProductToString(product)).append("\n");
        }
        return response.toString();
    }

    @Override
    public String getProductsByCategory(int categoryId) {
        List<Product> products = repo.getProductsByCategory(categoryId);
        if (products.isEmpty()) {
            return "No products in this category.";
        }

        StringBuilder response = new StringBuilder("Products in category ID " + categoryId + ":\n");
        for (Product product : products) {
            response.append(formatProductToString(product)).append("\n");
        }
        return response.toString();
    }

    private int getCategoryIdByName(String categoryName) {
        if (categoryController == null) {
            return -1;
        }

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

    private String formatProductToString(Product product) {
        return "ID: " + product.getId() +
                ", Name: " + product.getName() +
                ", Price: $" + product.getPrice() +
                ", Category ID: " + product.getCategoryId();
    }
}
