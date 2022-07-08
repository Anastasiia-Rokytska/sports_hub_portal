package com.company.sportHubPortal.Controllers;

import com.company.sportHubPortal.Models.Article;
import com.company.sportHubPortal.Services.ArticleServices.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
