package com.company.sportHubPortal.Controllers;

import com.company.sportHubPortal.Models.Article;
import com.company.sportHubPortal.Models.Category;
import com.company.sportHubPortal.POJO.ArticlePOJO;
import com.company.sportHubPortal.Services.ArticleServices.ArticleService;
import com.company.sportHubPortal.Services.CategoryServices.CategoryService;
import com.company.sportHubPortal.Services.NotificationService;
import com.company.sportHubPortal.Services.TeamService;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
  private final ArticleService articleService;
  private final TeamService teamService;
  private final NotificationService notificationService;
  private final CategoryService categoryService;

  Logger logger = LoggerFactory.getLogger(ArticleController.class);

  public ArticleController(ArticleService articleService,
                           TeamService teamService,
                           NotificationService notificationService,
                           CategoryService categoryService) {
    this.articleService = articleService;
    this.teamService = teamService;
    this.notificationService = notificationService;
    this.categoryService = categoryService;
  }

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<Object> saveArticle(@ModelAttribute ArticlePOJO articlePOJO,
                                            @RequestPart String selectedCategory,
                                            @RequestPart String selectedSubCategory,
                                            @RequestPart String selectedTeam) {
    Article article;
    try {
      article =
          new Article(articlePOJO.getTitle(), articlePOJO.getContent(), articlePOJO.getAuthor(),
              articlePOJO.isCommentable(), articlePOJO.getLanguage(), articlePOJO.getCaption());
      if (!(articlePOJO.getIcon() == null)) {
        article.setIcon(articlePOJO.getIcon());
      }
    } catch (Exception exception) {
      logger.info(exception.getLocalizedMessage());
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
          .body("Incorrect input data");
    }
    article.setPublishedDate(new Date(System.currentTimeMillis()));
    Set<Category> categories = new HashSet<>();
    if (!selectedCategory.equals("-1")) {
      categories.add(categoryService.getCategoryById(Long.parseLong(selectedCategory)));
    }
    if (!selectedSubCategory.equals("-1")) {
      categories.add(categoryService.getCategoryById(Long.parseLong(selectedSubCategory)));
    }
    if (!selectedTeam.equals("-1")) {
      article.setTeam(teamService.teamById(Integer.parseInt(selectedTeam)));
    }
    article.setCategories(categories);
    articleService.saveArticle(article);
    notificationService.notifySubscribers(article);
    logger.info("Article saved");
    return ResponseEntity.status(HttpStatus.OK).body(null);
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
  public ResponseEntity<Object> getArticlesByTeam(@PathVariable Integer id,
                                                  @PathVariable Integer page) {
    return ResponseEntity.ok(articleService.getAllArticlesByTeam(id, page));
  }
}
