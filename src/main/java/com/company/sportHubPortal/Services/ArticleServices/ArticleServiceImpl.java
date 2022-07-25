package com.company.sportHubPortal.Services.ArticleServices;

import com.company.sportHubPortal.Models.Article;
import com.company.sportHubPortal.Models.Team;
import com.company.sportHubPortal.Repositories.ArticleRepository;
import com.company.sportHubPortal.Services.CategoryServices.CategoryService;
import com.company.sportHubPortal.Services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final TeamService teamService;

    public ArticleServiceImpl(ArticleRepository articleRepository, TeamService teamService) {
        this.articleRepository = articleRepository;
        this.teamService = teamService;
    }

    @Override
    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public List<Article> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        Collections.reverse(articles);
        return articles;
    }

    @Override
    public List<Article> getPublishedArticles() {
        List<Article> articles = articleRepository.getArticlesByPublishedIs(true);
        Collections.reverse(articles);
        return articles;
    }

    @Override
    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Article> getArticlesByCategoryId(Long id) {
        List<Article> articles = new ArrayList<>(articleRepository.findAll().stream()
                .filter(article -> article.getCategories().stream()
                        .anyMatch(category -> category.getId().equals(id))).toList());
        Collections.reverse(articles);
        return articles;
    }

    @Override
    public List<Article> getAllArticlesByTeam(Integer id, Integer page) {
        Team team = teamService.teamById(id);
        if (team == null) return null;
        return articleRepository.findAllByTeamOrderByPublishedDate(team, PageRequest.of(page - 1, 5));
    }

    @Override
    public Article getFullArticle(Article article) {
        return articleRepository.findFullArticle(article);

    }
}
