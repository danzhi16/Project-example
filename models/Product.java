package models;

public class Product {
    private int id;
    private String name;
    private String description;
    private int category;
    private double price;

    public Product(int id, String name, String description, String category, double price) {
    }

    public Product(String name, String description, int category, double price) {
        setName(name);
        setDescription(description);
        setCategory(category);
        setPrice(price);
    }

    public Product(int id, String name, String description, int category, double price) {
        this(name, description, category, price);
        this.id = id;
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
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
                ", categoryId=" + category +
                ", price=" + price +
                '}';
    }
}
