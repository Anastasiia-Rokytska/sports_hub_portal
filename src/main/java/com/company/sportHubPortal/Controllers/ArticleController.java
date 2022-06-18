package com.company.sportHubPortal.Controllers;

import com.company.sportHubPortal.Database.Article;
import com.company.sportHubPortal.Database.Category;
import com.company.sportHubPortal.Services.ArticleServices.ArticleService;
import com.company.sportHubPortal.Services.CategoryServices.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    private ArticleService articleService;


    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping()
    public ResponseEntity<Article> saveArticle(@RequestBody Article article) {
        articleService.saveArticle(article);
        return ResponseEntity.ok(article);
    }

    @GetMapping()
    public List<Article> getAllCategories() {
        return articleService.getAllArticles();
    }

    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    @GetMapping("/category/{id}")
    public List<Article> getArticlesByCategoryId(@PathVariable Long id) {
        return articleService.getArticlesByCategoryId(id);
    }
}
