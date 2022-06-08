package com.company.sportHubPortal.Services.CategoryServices;

import com.company.sportHubPortal.Database.Category;
import com.company.sportHubPortal.Repositories.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    private ObjectMapper mapper = new ObjectMapper();

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> getAllCategoriesByParentId(Long id) {
        return categoryRepository.findAll().stream().
                filter(category -> category.getParentId().equals(id)).collect(Collectors.toList());
    }

    @Override
    public List<Category> getVisibleCategoriesByParentId(Long id) {
        return categoryRepository.findAll().stream()
                .filter(category -> category.getParentId().equals(id) && !category.isHidden())
                .collect(Collectors.toList());
    }

    @Override
    public Category updateCategory(Category category, Long id) {

        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory != null) {
            existingCategory.setName(category.getName());
            existingCategory.setHidden(category.isHidden());
            categoryRepository.save(existingCategory);
            return existingCategory;
        }
        return null;
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresent(categoryRepository::delete);
    }
}
