package models;

import models.interfaces.ICategory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Category implements ICategory {
    private static List<Category> categories = new ArrayList<>();
    private static int idCounter = 1;

    private int id;
    private String name;
    private String description;

    public Category() {}

    public Category(String name, String description) {
        this.id = idCounter++;
        this.name = name;
        this.description = description;
        categories.add(this);
    }

    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public Category createCategory(String name, String description) {
        return new Category(name, description);
    }

    @Override
    public Category getCategoryById(int id) {
        Optional<Category> category = categories.stream().filter(c -> c.getId() == id).findFirst();
        return category.orElse(null);
    }

    @Override
    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }

    @Override
    public boolean updateCategory(int id, String name, String description) {
        for (Category category : categories) {
            if (category.getId() == id) {
                category.setName(name);
                category.setDescription(description);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteCategory(int id) {
        return categories.removeIf(category -> category.getId() == id);
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