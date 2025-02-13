package repositories.interfaces;

import models.Category;
import java.util.List;

public interface ICategoryRepository {
    boolean createCategory(Category category);
    Category getCategoryById(int id);
    List<Category> getAllCategories();
    boolean updateCategory(Category category);
    boolean deleteCategory(int id);
}
