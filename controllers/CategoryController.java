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
    public String createCategory(String name, String description) {
        Category category = new Category(name, description);
        boolean created = repo.createCategory(category);
        return (created) ? "Category was created" : "Category creation failed";
    }

    @Override
    public String getCategoryById(int id) {
        Category category = repo.getCategoryById(id);
        return (category == null) ? "Category was not found" : category.toString();
    }

    @Override
    public String getAllCategories() {
        List<Category> categories = repo.getAllCategories();
        StringBuilder response = new StringBuilder();
        for (Category category : categories) {
            response.append(category.toString()).append("\n");
        }
        return response.toString();
    }

    @Override
    public String deleteCategory(int id) {
        boolean deleted = repo.deleteCategory(id);
        return (deleted) ? "Category was deleted" : "Category deletion failed";
    }
}
