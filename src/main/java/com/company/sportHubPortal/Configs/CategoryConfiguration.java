package com.company.sportHubPortal.Configs;

import com.company.sportHubPortal.Models.Article;
import com.company.sportHubPortal.Models.Category;
import com.company.sportHubPortal.Services.ArticleServices.ArticleService;
import com.company.sportHubPortal.Services.CategoryServices.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class CategoryConfiguration {

    private final CategoryService categoryService;
    private final ArticleService articleService;

    @Autowired
    public CategoryConfiguration(CategoryService categoryService, ArticleService articleService) {
        this.categoryService = categoryService;
        this.articleService = articleService;
    }

    @Bean
    public void categoryConfig() {
        categoryService.saveCategory(new Category(0L, "Home", null, false, null, false));
        categoryService.saveCategory(new Category(1L, "NBA", Set.of(categoryService.getCategoryById(0L)), false, null, false));
        categoryService.saveCategory(new Category(2L, "NHL", Set.of(categoryService.getCategoryById(0L)), false, null, false));
        categoryService.saveCategory(new Category(3L, "NFL", Set.of(categoryService.getCategoryById(0L)), false, null, false));
        categoryService.saveCategory(new Category(4L, "UFC", Set.of(categoryService.getCategoryById(0L)), false, null, false));
        categoryService.saveCategory(new Category(5L, "AFC1", Set.of(categoryService.getCategoryById(1L)), false, null, false));
        categoryService.saveCategory(new Category(6L, "AFC2", Set.of(categoryService.getCategoryById(1L)), false, null, false));
        categoryService.saveCategory(new Category(7L, "AFC3", Set.of(categoryService.getCategoryById(1L)), false, null, false));
        categoryService.saveCategory(new Category(8L, "AFC4", Set.of(categoryService.getCategoryById(1L)), false, null, false));
        categoryService.saveCategory(new Category(9L, "Lifestyle", Set.of(categoryService.getCategoryById(0L)), false, null, false));
        categoryService.saveCategory(new Category(10L, "Dealbook", Set.of(categoryService.getCategoryById(0L)), false, null, false));
        categoryService.saveCategory(new Category(11L, "Video", Set.of(categoryService.getCategoryById(0L)), false, null, false));
        categoryService.saveCategory(new Category(12L, "Team hub", Set.of(categoryService.getCategoryById(0L)), false, null, false));
        categoryService.saveCategory(new Category(13L, "All Teams", new HashSet<>(categoryService.forCategoryEditor()), false, null, false));
    }

    @Bean
    public void addCategoriesToArticles(){
        Article article = articleService.getArticleById(1L);
        article.setCategories(new HashSet<>(Arrays.asList(
                categoryService.getCategoryById(1L), categoryService.getCategoryById(5L))));
        articleService.saveArticle(article);
        article = articleService.getArticleById(2L);
        article.setCategories(new HashSet<>(Arrays.asList(
                categoryService.getCategoryById(1L), categoryService.getCategoryById(5L))));
        articleService.saveArticle(article);
        article = articleService.getArticleById(3L);
        article.setCategories(new HashSet<>(Arrays.asList(
                categoryService.getCategoryById(1L), categoryService.getCategoryById(6L))));
        articleService.saveArticle(article);
        article = articleService.getArticleById(4L);
        article.setCategories(new HashSet<>(Arrays.asList(
                categoryService.getCategoryById(1L), categoryService.getCategoryById(7L))));
        articleService.saveArticle(article);
        article = articleService.getArticleById(5L);
        article.setCategories(new HashSet<>(Arrays.asList(
                categoryService.getCategoryById(1L), categoryService.getCategoryById(8L))));
        articleService.saveArticle(article);
        article = articleService.getArticleById(6L);
        article.setCategories(new HashSet<>(Arrays.asList(
                categoryService.getCategoryById(1L), categoryService.getCategoryById(5L))));
        articleService.saveArticle(article);
        article = articleService.getArticleById(7L);
        article.setCategories(new HashSet<>(Arrays.asList(
                categoryService.getCategoryById(1L), categoryService.getCategoryById(6L))));
        articleService.saveArticle(article);
        article = articleService.getArticleById(8L);
        article.setCategories(new HashSet<>(Arrays.asList(
                categoryService.getCategoryById(1L), categoryService.getCategoryById(5L))));
        articleService.saveArticle(article);
        article = articleService.getArticleById(9L);
        article.setCategories(new HashSet<>(Arrays.asList(
                categoryService.getCategoryById(1L), categoryService.getCategoryById(7L))));
        articleService.saveArticle(article);
        article = articleService.getArticleById(10L);
        article.setCategories(new HashSet<>(Arrays.asList(
                categoryService.getCategoryById(1L), categoryService.getCategoryById(8L))));
        articleService.saveArticle(article);
        article = articleService.getArticleById(11L);
        article.setCategories(new HashSet<>(Arrays.asList(
                categoryService.getCategoryById(1L), categoryService.getCategoryById(8L))));
        articleService.saveArticle(article);
    }
}
