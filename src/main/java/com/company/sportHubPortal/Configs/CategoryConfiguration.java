package com.company.sportHubPortal.Configs;

import com.company.sportHubPortal.Database.Category;
import com.company.sportHubPortal.Services.CategoryServices.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryConfiguration {

  private CategoryService categoryService;

  @Autowired
  public CategoryConfiguration(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @Bean
  public void categoryConfig() {
    categoryService.saveCategory(new Category(1L, "NBA", 0L, false));
    categoryService.saveCategory(new Category(2L, "NHL", 0L, false));
    categoryService.saveCategory(new Category(3L, "NFL", 0L, false));
    categoryService.saveCategory(new Category(4L, "UFC", 0L, false));
    categoryService.saveCategory(new Category(5L, "AFC1", 1L, false));
    categoryService.saveCategory(new Category(6L, "AFC2", 1L, false));
    categoryService.saveCategory(new Category(7L, "AFC3", 1L, false));
    categoryService.saveCategory(new Category(8L, "AFC4", 1L, false));
    categoryService.saveCategory(new Category(9L, "LA", 2L, false));
  }

}
