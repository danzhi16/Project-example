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
        return (created) ? "Product was created" : "Product creation failed";
    }

    @Override
    public String getProductById(int id) {
        Product product = (Product) repo.getProductById(id);
        return (product == null) ? "Product was not found" : product.toString();
    }

    @Override
    public String getAllProducts() {
        List<Product> products = repo.getAllProducts();
        StringBuilder response = new StringBuilder();
        for (Product product : products) {
            response.append(product.toString()).append("\n");
        }
        return response.toString();
    }

    @Override
    public String deleteProduct(int id) {
        boolean deleted = repo.deleteProduct(id);
        return (deleted) ? "Product was deleted" : "Product deletion failed";
    }

    @Override
    public List<Product> getSellerProducts() {
        int sellerId = getCurrentSellerId();
        return repo.getProductsBySellerId(sellerId);
    }

    private int getCurrentSellerId() {
        return 1;
    }
}