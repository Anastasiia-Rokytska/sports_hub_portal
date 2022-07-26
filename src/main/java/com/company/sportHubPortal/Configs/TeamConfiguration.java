package com.company.sportHubPortal.Configs;

import com.company.sportHubPortal.Models.Article;
import com.company.sportHubPortal.Models.Team;
import com.company.sportHubPortal.Services.ArticleServices.ArticleService;
import com.company.sportHubPortal.Services.CategoryServices.CategoryService;
import com.company.sportHubPortal.Services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;

@Configuration
public class TeamConfiguration {

    private final TeamService teamService;
    private final ResourceLoader resourceLoader;
    private final CategoryService categoryService;
    private final ArticleService articleService;

    @Autowired
    public TeamConfiguration(TeamService teamService, ResourceLoader resourceLoader, CategoryService categoryService, ArticleService articleService) {
        this.teamService = teamService;
        this.resourceLoader = resourceLoader;
        this.categoryService = categoryService;
        this.articleService = articleService;
    }

    @Bean
    public void addTeams() throws IOException, SQLException {
        String pathToConfigImages = "classpath:/static/assets/images/configs/team/";
        teamService.saveTeam(new Team("Los Angeles Lakers", "Los Angeles, California",
                34.052235, -118.243683, categoryService.getCategoryById(1L), categoryService.getCategoryById(5L),
                new SerialBlob(resourceLoader.getResource(pathToConfigImages + "LosAngelesLakers.png").getInputStream().readAllBytes())));
        teamService.saveTeam(new Team("Houston Rockets", "Houston, Texas",
                29.749907, -95.358421, categoryService.getCategoryById(1L), categoryService.getCategoryById(6L),
                new SerialBlob(resourceLoader.getResource(pathToConfigImages + "HoustonRockets.png").getInputStream().readAllBytes())));
        teamService.saveTeam(new Team("Memphis Grizzlies", "Memphis, Tennessee",
                35.117500, -89.971107, categoryService.getCategoryById(1L), categoryService.getCategoryById(5L),
                new SerialBlob(resourceLoader.getResource(pathToConfigImages + "MemphisGrizzlies.png").getInputStream().readAllBytes())));
        teamService.saveTeam(new Team("Utah Jazz", "Salt Lake City, Utah",
                40.758701, -111.876183, categoryService.getCategoryById(1L), categoryService.getCategoryById(5L),
                new SerialBlob(resourceLoader.getResource(pathToConfigImages + "UtahJazz.png").getInputStream().readAllBytes())));
        teamService.saveTeam(new Team("Los Angeles Lakers1", "Los Angeles, California",
                25.0, 44.0, categoryService.getCategoryById(1L), categoryService.getCategoryById(6L),
                new SerialBlob(resourceLoader.getResource(pathToConfigImages + "LosAngelesLakers.png").getInputStream().readAllBytes())));
        teamService.saveTeam(new Team("Houston Rockets1", "Houston, Texas",
                22.0, 18.0,
                new SerialBlob(resourceLoader.getResource(pathToConfigImages + "HoustonRockets.png").getInputStream().readAllBytes())));
        teamService.saveTeam(new Team("Memphis Grizzlies1", "Memphis, Tennessee",
                59.0, 67.0,
                new SerialBlob(resourceLoader.getResource(pathToConfigImages + "MemphisGrizzlies.png").getInputStream().readAllBytes())));
        teamService.saveTeam(new Team("Utah Jazz1", "Salt Lake City, Utah",
                -23.0, -55.0,
                new SerialBlob(resourceLoader.getResource(pathToConfigImages + "UtahJazz.png").getInputStream().readAllBytes())));
        teamService.saveTeam(new Team("Los Angeles Lakers0", "Los Angeles, California",
                34.435, -118.8983,
                new SerialBlob(resourceLoader.getResource(pathToConfigImages + "LosAngelesLakers.png").getInputStream().readAllBytes())));
        teamService.saveTeam(new Team("Houston Rockets0", "Houston, Texas",
                -29.35907, 95.8421,
                new SerialBlob(resourceLoader.getResource(pathToConfigImages + "HoustonRockets.png").getInputStream().readAllBytes())));
        teamService.saveTeam(new Team("Memphis Grizzlies0", "Memphis, Tennessee",
                -35.0, 89.7,
                new SerialBlob(resourceLoader.getResource(pathToConfigImages + "MemphisGrizzlies.png").getInputStream().readAllBytes())));
        teamService.saveTeam(new Team("Utah Jazz0", "Salt Lake City, Utah",
                30.701, 111.7883,
                new SerialBlob(resourceLoader.getResource(pathToConfigImages + "UtahJazz.png").getInputStream().readAllBytes())));
        teamService.saveTeam(new Team("Los Angeles Lakers10", "Los Angeles, California",
                33.0, 55.0,
                new SerialBlob(resourceLoader.getResource(pathToConfigImages + "LosAngelesLakers.png").getInputStream().readAllBytes())));
        teamService.saveTeam(new Team("Houston Rockets10", "Houston, Texas",
                12.0, 67.0,
                new SerialBlob(resourceLoader.getResource(pathToConfigImages + "HoustonRockets.png").getInputStream().readAllBytes())));
        teamService.saveTeam(new Team("Memphis Grizzlies10", "Memphis, Tennessee",
                74.0, 34.0,
                new SerialBlob(resourceLoader.getResource(pathToConfigImages + "MemphisGrizzlies.png").getInputStream().readAllBytes())));
        teamService.saveTeam(new Team("Utah Jazz10", "Salt Lake City, Utah",
                -23.124, -45.666,
                new SerialBlob(resourceLoader.getResource(pathToConfigImages + "UtahJazz.png").getInputStream().readAllBytes())));
    }

    @Bean
    public void addTeamToArticle(){
        Article article = articleService.getArticleById(1L);
        article.setTeam(teamService.teamById(1));
        articleService.saveArticle(article);
        article = articleService.getArticleById(2L);
        article.setTeam(teamService.teamById(1));
        articleService.saveArticle(article);
        article = articleService.getArticleById(3L);
        article.setTeam(teamService.teamById(1));
        articleService.saveArticle(article);
        article = articleService.getArticleById(4L);
        article.setTeam(teamService.teamById(1));
        articleService.saveArticle(article);
        article = articleService.getArticleById(5L);
        article.setTeam(teamService.teamById(1));
        articleService.saveArticle(article);
        article = articleService.getArticleById(6L);
        article.setTeam(teamService.teamById(1));
        articleService.saveArticle(article);
        article = articleService.getArticleById(7L);
        article.setTeam(teamService.teamById(1));
        articleService.saveArticle(article);
        article = articleService.getArticleById(8L);
        article.setTeam(teamService.teamById(2));
        articleService.saveArticle(article);
        article = articleService.getArticleById(9L);
        article.setTeam(teamService.teamById(4));
        articleService.saveArticle(article);
        article = articleService.getArticleById(10L);
        article.setTeam(teamService.teamById(6));
        articleService.saveArticle(article);
        article = articleService.getArticleById(11L);
        article.setTeam(teamService.teamById(6));
        articleService.saveArticle(article);
    }
}
