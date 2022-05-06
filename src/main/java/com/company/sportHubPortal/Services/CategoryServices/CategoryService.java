package com.company.sportHubPortal.Services.CategoryServices;

import com.company.sportHubPortal.Database.Category;
import com.google.gson.JsonElement;

import java.util.List;

public interface CategoryService {
    Category saveCategory(Category category);
    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    List<Category> getCategoriesByParentId(Long id);
}
