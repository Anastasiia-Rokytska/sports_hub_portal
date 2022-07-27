package com.company.sportHubPortal.Services.CategoryServices;

import com.company.sportHubPortal.Models.Category;
import com.company.sportHubPortal.POJO.CategoryPOJO;
import com.company.sportHubPortal.Repositories.CategoryRepository;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final String ALL_TEAMS = "All Teams";
    private final Long ROOT_CATEGORY_ID = 0L;

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
        Optional<Category> category = categoryRepository.findAll().stream().filter(c -> Objects.equals(c.getId(), id)).findFirst();
        return category.orElse(null);
    }

    @Override
    public List<Category> getAllCategoriesByParentId(Long id) {
        return categoryRepository.findAll().stream()
                .filter(category -> !category.isTeam() && category.getParents().stream()
                        .anyMatch(parent -> parent.getId().equals(id))).collect(Collectors.toList());
    }

    @Override
    public List<Category> getVisibleCategoriesByParentId(Long id) {
        List<Category> temp = getAllCategoriesByParentId(id);
        return temp.stream().filter(category -> !category.isHidden()).collect(Collectors.toList());
    }

    @Override
    public Category updateCategory(CategoryPOJO category, Long id) {

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

    @Override
    public List<Category> getSubCategories() {
        return categoryRepository.findAll().stream()
                .filter(category -> !category.getParents().isEmpty()
                        && !category.isTeam()
                        && category.getParents().stream()
                        .anyMatch(parent -> !parent.getId().equals(ROOT_CATEGORY_ID))).collect(Collectors.toList());
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getSubCategoriesByCategoryName(String name) {
        List<Category> result = new ArrayList<>(categoryRepository.findByName(name).getChildren().stream()
                .filter(category -> !category.isTeam() && !category.getName().equals(ALL_TEAMS)).toList());
        result.sort(Comparator.comparingLong(Category::getId));
        return result;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll().stream()
                .filter((category -> category.getParents().isEmpty() || category.getParents().stream()
                        .anyMatch(parent -> parent.getId().equals(ROOT_CATEGORY_ID)) && !category.isHidden())).collect(Collectors.toList());
    }

    @Override
    public List<Category> forCategoryEditor() {
        List<Category> result = new ArrayList<>(getCategoryById(ROOT_CATEGORY_ID).getChildren().stream().filter(category -> !category.isTeam()).toList());
        result.sort(Comparator.comparing(Category::getId));
        return result;
    }

    @Override
    public List<Category> allCategoriesWithoutTeams() {
        return categoryRepository.findAll().stream()
                .filter(category -> !category.isTeam() && !category.getName().equals(ALL_TEAMS)).toList();
    }

    @Override
    public List<Category> getTeamsByCategoryId(Long id) {
        return categoryRepository.findAll().stream()
                .filter(category -> category.isTeam() && category.getParents().stream()
                        .anyMatch(parent -> parent.getId().equals(id))).collect(Collectors.toList());
    }

    @Override
    public List<Category> getVisibleTeamsByCategoryId(Long id) {
        return categoryRepository.findAll().stream()
                .filter(category -> category.isTeam() && !category.isHidden() && category.getParents().stream()
                        .anyMatch(parent -> parent.getId().equals(id))).collect(Collectors.toList());
    }
}
