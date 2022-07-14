package com.company.sportHubPortal.Controllers;

import com.company.sportHubPortal.Models.Article;
import com.company.sportHubPortal.Services.ArticleServices.ArticleService;
import com.company.sportHubPortal.Services.NotificationService;
import com.company.sportHubPortal.Services.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    private final ArticleService articleService;
    private final TeamService teamService;
    private final NotificationService notificationService;


    public ArticleController(ArticleService articleService,
                             TeamService teamService,
                             NotificationService notificationService) {
        this.articleService = articleService;
        this.teamService = teamService;
        this.notificationService = notificationService;
    }

    @PostMapping()
    public ResponseEntity<Article> saveArticle(@RequestBody Article article) {
        article.setTeam(teamService.teamById(article.getTeam().getId()));
        articleService.saveArticle(article);
        notificationService.notifySubscribers(article);
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

    @GetMapping("/team/{id}/{page}")
    public ResponseEntity<Object> getArticlesByTeam(@PathVariable Integer id, @PathVariable Integer page) {
        return ResponseEntity.ok(articleService.getAllArticlesByTeam(id, page));
    }
}
