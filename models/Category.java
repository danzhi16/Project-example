package models;

import Data.PostgresDB;
import models.interfaces.ICategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
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
        Category category = new Category(id, name);  // Create new Category object
        return category;  // Return the created category
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