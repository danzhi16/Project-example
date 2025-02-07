package models;

import models.interfaces.IProduct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Product implements IProduct {
    private static List<Product> products = new ArrayList<>();
    private static int idCounter = 1;

    private int id;
    private String name;
    private String description;
    private int categoryId;
    private double price;

    public Product(int id, String name, String description, String categoryId, double price) {}

    public Product(String name, String description, int categoryId, double price) {
        this.id = idCounter++;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
        products.add(this);
    }

    public Product(int id, String name, String description, int categoryId, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
    }

    @Override
    public Product createProduct(String name, String description, int categoryId, double price) {
        return new Product(name, description, categoryId, price);
    }

    @Override
    public Product getProductById(int id) {
        Optional<Product> product = products.stream().filter(p -> p.getId() == id).findFirst();
        return product.orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public boolean updateProduct(int id, String name, String description, int categoryId, double price) {
        for (Product product : products) {
            if (product.getId() == id) {
                product.setName(name);
                product.setDescription(description);
                product.setCategoryId(categoryId);
                product.setPrice(price);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteProduct(int id) {
        return products.removeIf(product -> product.getId() == id);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", price=" + price +
                '}';
    }
}