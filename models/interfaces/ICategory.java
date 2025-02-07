package models.interfaces;

import models.Category;
import java.util.List;

public interface ICategory {
    Category createCategory(String name, String description);
    Category getCategoryById(int id);
    List<Category> getAllCategories();
    boolean updateCategory(int id, String name, String description);
    boolean deleteCategory(int id);
}
