package service;

import controllers.interfaces.ICategoryController;
import models.Category;
import models.Product;
import repositories.CategoryRepository;
import repositories.ProductRepository;
import service.interfaces.IConsoleApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }


}
