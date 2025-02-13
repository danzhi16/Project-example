package models.interfaces;

import models.Category;
import java.util.List;

public interface ICategory {
    Category createCategory(int id, String name);
    Category getCategoryById(int id);
    List<Category> getAllCategories();
    boolean updateCategory(int id, String name);
    boolean deleteCategory(int id);

    void setId(int anInt);
}
