package com.company.sportHubPortal.Services.CategoryServices;

import com.company.sportHubPortal.Database.Category;
import java.util.List;

public interface CategoryService {
  Category saveCategory(Category category);

  List<Category> getAllCategories();

  Category getCategoryById(Long id);

  List<Category> getAllCategoriesByParentId(Long id);

  List<Category> getVisibleCategoriesByParentId(Long id);

  Category updateCategory(Category category, Long id);

  void deleteCategory(Long id);
}
