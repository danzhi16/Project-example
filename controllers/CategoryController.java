package controllers;

import controllers.interfaces.ICategoryController;
import models.Category;
import repositories.interfaces.ICategoryRepository;

import java.util.List;

public class CategoryController implements ICategoryController {

    private final ICategoryRepository repo;

    public CategoryController(ICategoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public String createCategory(int id, String name) {
        if (name == null || name.trim().isEmpty()) {
            return "❌ Error: Category name cannot be empty.";
        }

        Category category = new Category(id, name);
        boolean created = repo.createCategory(category);
        return created ? "✅ Category created: " + name : "❌ Error creating category.";
    }

    @Override
    public String getCategoryById(int id) {
        Category category = repo.getCategoryById(id);
        return (category != null) ? "Category found:\n" + category : "Category not found.";
    }

    @Override
    public String getAllCategories() {
        List<Category> categories = repo.getAllCategories();
        if (categories.isEmpty()) {
            return "No categories available.";
        }

        StringBuilder response = new StringBuilder("Available categories:\n");
        for (Category category : categories) {
            response.append("ID: ").append(category.getId())
                    .append(", Name: ").append(category.getName()).append("\n");
        }
        return response.toString();
    }

    @Override
    public String deleteCategory(int id) {
        boolean deleted = repo.deleteCategory(id);
        return deleted ? "✅ Category deleted." : "❌ Error deleting category.";
    }
}
