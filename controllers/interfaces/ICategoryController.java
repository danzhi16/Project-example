package controllers.interfaces;

import models.Category;

public interface ICategoryController {
    String createCategory(String name, String description);
    String getCategoryById(int id);
    String getAllCategories();
    String deleteCategory(int id);
}
