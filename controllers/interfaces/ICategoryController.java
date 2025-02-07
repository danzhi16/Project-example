package controllers.interfaces;

import models.Category;

public interface ICategoryController {
    String createCategory(int id, String name);
    String getCategoryById(int id);
    String getAllCategories();
    String deleteCategory(int id);
}
