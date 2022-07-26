package com.company.sportHubPortal.Controllers;

import com.company.sportHubPortal.Models.Category;
import com.company.sportHubPortal.POJO.CategoryPOJO;
import com.company.sportHubPortal.Services.CategoryServices.CategoryService;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<Category> saveCategory(@ModelAttribute CategoryPOJO categoryPOJO,
                                               @RequestPart String parentCategoryId) {
    Category category;
    category = new Category(categoryPOJO.getId(), categoryPOJO.getName(), categoryPOJO.isHidden());
    if (categoryService.getCategoryById(Long.parseLong(parentCategoryId)) != null) {
      category.setParents(Set.of(categoryService.getCategoryById(Long.parseLong(parentCategoryId))));
    }
    System.out.println(category);
    categoryService.saveCategory(category);
    if (Long.parseLong(parentCategoryId) == 0L) {
      Category allTeams = categoryService.getCategoryByName("All Teams");
      allTeams.addParent(category);
      categoryService.saveCategory(allTeams);
    }
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

  @GetMapping("/teams/visible/{id}")
  public List<Category> getVisibleTeamsByCategoryId(@PathVariable("id") Long id) {
    return categoryService.getVisibleTeamsByCategoryId(id);
  }

  @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id,
                                                 @ModelAttribute CategoryPOJO categoryPOJO) {
    Category category = categoryService.updateCategory(categoryPOJO, id);
    if (category == null) {
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

  @GetMapping("/editor")
  public ResponseEntity<Object> forCategoryEditor() {
    return ResponseEntity.ok().body(categoryService.forCategoryEditor());
  }

  @GetMapping("/teams/{id}")
  public ResponseEntity<Object> getTeamsByCategoryId(@PathVariable Long id) {
    return ResponseEntity.ok().body(categoryService.getTeamsByCategoryId(id));
  }

}
