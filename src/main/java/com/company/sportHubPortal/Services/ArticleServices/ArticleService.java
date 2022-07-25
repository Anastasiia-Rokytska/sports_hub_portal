package com.company.sportHubPortal.Services.ArticleServices;

import com.company.sportHubPortal.Models.Article;

import java.util.List;

public interface ArticleService {
    Article saveArticle(Article article);

    List<Article> getAllArticles();

    List<Article> getPublishedArticles();

    Article getArticleById(Long id);

    List<Article> getArticlesByCategoryId(Long id);

    List<Article> getAllArticlesByTeam(Integer id, Integer page);

    Article getFullArticle(Article article);
}
