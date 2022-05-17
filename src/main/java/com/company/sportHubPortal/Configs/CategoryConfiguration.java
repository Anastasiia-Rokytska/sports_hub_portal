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
    public void categoryConfig(){
        categoryService.saveCategory(new Category("NBA", 0L,false));
        categoryService.saveCategory(new Category("NHL", 0L,false));
        categoryService.saveCategory(new Category("NFL",0L,false));
        categoryService.saveCategory(new Category("UFC",0L,false));
        categoryService.saveCategory(new Category("AFC1",0L,false));
        categoryService.saveCategory(new Category("AFC2",0L,false));
        categoryService.saveCategory(new Category("AFC3",0L,false));
        categoryService.saveCategory(new Category("AFC4",0L,false));
        categoryService.saveCategory(new Category("LA",0L,false));
    }

}
