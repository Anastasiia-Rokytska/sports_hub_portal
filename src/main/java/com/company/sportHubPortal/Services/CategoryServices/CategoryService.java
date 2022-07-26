package com.company.sportHubPortal.Services.CategoryServices;

import com.company.sportHubPortal.Models.Category;
import com.company.sportHubPortal.POJO.CategoryPOJO;

import java.util.List;

public interface CategoryService {
  Category saveCategory(Category category);

  List<Category> getAllCategories();

  Category getCategoryById(Long id);

  List<Category> getAllCategoriesByParentId(Long id);

  List<Category> getVisibleCategoriesByParentId(Long id);

  Category updateCategory(CategoryPOJO category, Long id);

  void deleteCategory(Long id);

  List<Category> getCategories();

  List<Category> getSubCategories();

  Category getCategoryByName(String name);

  List<Category> getSubCategoriesByCategoryName(String name);

  List<Category> forCategoryEditor();

  List<Category> allCategoriesWithoutTeams();

  List<Category> getTeamsByCategoryId(Long id);

  List<Category> getVisibleTeamsByCategoryId(Long id);

}
