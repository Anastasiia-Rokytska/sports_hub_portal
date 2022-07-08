package com.company.sportHubPortal.Services.ArticleServices;

import com.company.sportHubPortal.Database.Article;

import java.util.List;

public interface ArticleService {
    Article saveArticle(Article article);

    List<Article> getAllArticles();

    Article getArticleById(Long id);

    List<Article> getArticlesByCategoryId(Long id);
}
