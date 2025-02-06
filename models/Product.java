package models;

public class Product {
    private int id;
    private String name;
    private String description;
    private int category_id;
    private double price;

    public Product(int id, String name, String description, String category_id, double price) {
    }

    public Product(String name, String description, int category_id, double price) {
        setName(name);
        setDescription(description);
        setCategory(category_id);
        setPrice(price);
    }

    public Product(int id, String name, String description, int category_id, double price) {
        this(name, description, category_id, price);
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
        return category_id;
    }

    public void setCategory(int category) {
        this.category_id = category_id;
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
                ", categoryId=" + category_id +
                ", price=" + price +
                '}';
    }
}
