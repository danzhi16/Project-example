package repositories;

import models.Category;
import repositories.interfaces.ICategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements ICategoryRepository {
    private final List<Category> categories = new ArrayList<>();

    @Override
    public boolean createCategory(Category category) {
        return categories.add(category);
    }

    @Override
    public Category getCategoryById(int id) {
        return categories.stream()
                .filter(category -> category.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }

    @Override
    public boolean deleteCategory(int id) {
        return categories.removeIf(category -> category.getId() == id);
    }
}
