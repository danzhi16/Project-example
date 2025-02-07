package models;

import models.interfaces.ICategory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Category implements ICategory {
    private static List<Category> categories = new ArrayList<>();

    private int id;
    private String name;


    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        categories.add(this);
    }


    @Override
    public Category createCategory(int id, String name) {
        return new Category(id, name);
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
    public boolean updateCategory(int id, String name) {
        for (Category category : categories) {
            if (category.getId() == id) {
                category.setName(name);
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


    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}