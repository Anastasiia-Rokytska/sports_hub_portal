package com.company.sportHubPortal.Controllers;

import com.company.sportHubPortal.Database.Category;
import com.company.sportHubPortal.Services.CategoryServices.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class CategoryController {
    private CategoryService categoryService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping()
    public ResponseEntity<Category> saveCategory(@RequestBody Category category){
        if(category.getParentId() == null){
            category.setParentId((long)0);
        }
        System.out.println(category);
        categoryService.saveCategory(category);
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + "() " + " New category: " + category.getName());
        return ResponseEntity.ok(category);
    }

    @GetMapping("/category")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/category/{id}")
    public Category getCategoryById(@PathVariable("id") Long id){
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/category/parent/{id}")
    public List<Category> getCategoriesByParentId(@PathVariable("id") Long id){
        return categoryService.getCategoriesByParentId(id);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id,@RequestBody Category category){
        if(categoryService.updateCategory(category, id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + "() " + " Updated category: " + category.getName());
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + "() " + " Deleted category: " + id);
        return ResponseEntity.ok("Category deleted successfully");
    }

}
