package com.company.sportHubPortal.Services.ArticleServices;

import com.company.sportHubPortal.Database.Article;
import com.company.sportHubPortal.Database.Category;
import com.company.sportHubPortal.Repositories.ArticleRepository;
import com.company.sportHubPortal.Services.CategoryServices.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryService categoryService;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article saveArticle(Article article) {
        System.out.println(article.getCategories());
        return articleRepository.save(article);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Article> getArticlesByCategoryId(Long id) {
        return articleRepository.findAll().stream()
                .filter(article -> article.getCategories().stream()
                        .anyMatch(category -> category.getId().equals(id)))
                .collect(Collectors.toList());
    }
}
