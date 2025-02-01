package models;

public class Category {
    private int id;
    private String name;
    private String description;

    public Category() {
    }

    public Category(String name, String description) {
        setName(name);
        setDescription(description);
    }

    public Category(int id, String name, String description) {
        this(name, description);
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

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
