package com.company.sportHubPortal.Controllers;

import com.company.sportHubPortal.Models.Category;
import com.company.sportHubPortal.Services.CategoryServices.CategoryService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
  private final CategoryService categoryService;
  Logger logger = LoggerFactory.getLogger(CategoryController.class);

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping()
  public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
    if (category.getParentId() == null) {
      category.setParentId((long) 0);
    }
    logger.info(category.toString());

    categoryService.saveCategory(category);
    logger.info(new Object() {
    }.getClass().getEnclosingMethod().getName() + "() " + " New category: " + category.getName());
    return ResponseEntity.ok(category);
  }

  @GetMapping()
  public List<Category> getAllCategories() {
    return categoryService.getAllCategories();
  }

  @GetMapping("/{id}")
  public Category getCategoryById(@PathVariable("id") Long id) {
    return categoryService.getCategoryById(id);
  }

  @GetMapping("/parent/all/{id}")
  public List<Category> getAllCategoriesByParentId(@PathVariable("id") Long id) {
    return categoryService.getAllCategoriesByParentId(id);
  }

  @GetMapping("/parent/visible/{id}")
  public List<Category> getVisibleCategoriesByParentId(@PathVariable("id") Long id) {
    return categoryService.getVisibleCategoriesByParentId(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id,
                                                 @RequestBody Category category) {
    if (categoryService.updateCategory(category, id) == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    logger.info(new Object() {
    }.getClass().getEnclosingMethod().getName() + "() " + " Updated category: "
        + category.getName());
    return ResponseEntity.ok(category);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
    categoryService.deleteCategory(id);
    logger.info(new Object() {
    }.getClass().getEnclosingMethod().getName() + "() " + " Deleted category: " + id);
    return ResponseEntity.ok("Category deleted successfully");
  }

  @GetMapping("/category")
  public ResponseEntity<Object> getCategories() {
    return ResponseEntity.ok().body(categoryService.getCategories());
  }

  @GetMapping("/subcategory")
  public ResponseEntity<Object> getSubCategories() {
    return ResponseEntity.ok().body(categoryService.getSubCategories());
  }

  @GetMapping("/subcategory/{category}")
  public ResponseEntity<Object> getSubCategoriesByCategory(@PathVariable String category) {
    return ResponseEntity.ok().body(categoryService.getSubCategoriesByCategoryName(category));
  }


}
