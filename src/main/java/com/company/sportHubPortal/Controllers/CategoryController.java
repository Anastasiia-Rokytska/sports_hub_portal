package com.company.sportHubPortal.Controllers;

import com.company.sportHubPortal.Database.Category;
import com.company.sportHubPortal.Services.CategoryServices.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping()
    public ResponseEntity<Category> saveCategory(@RequestBody Category category){
        if(category.getParentId() == null){
            category.setParentId((long)0);
        }
        return new ResponseEntity<Category>(categoryService.saveCategory(category), HttpStatus.CREATED);
    }

    @GetMapping()
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable("id") Long id){
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/parent/{id}")
    public List<Category> getCategoriesByParentId(@PathVariable("id") Long id){
        return categoryService.getCategoriesByParentId(id);
    }
}
