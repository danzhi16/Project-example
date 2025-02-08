package service;

import controllers.interfaces.ICategoryController;
import repositories.CategoryRepository;
import repositories.ProductRepository;

public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }


}
