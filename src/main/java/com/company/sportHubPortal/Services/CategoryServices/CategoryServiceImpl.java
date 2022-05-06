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
    public List<Category> getCategoriesByParentId(Long id) {
        return categoryRepository.findAll().stream().
                filter(category -> category.getParentId().equals(id)).collect(Collectors.toList());
    }
}
