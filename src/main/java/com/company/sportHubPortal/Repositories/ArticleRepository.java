package com.company.sportHubPortal.Repositories;

import com.company.sportHubPortal.Models.Article;
import com.company.sportHubPortal.Models.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByTeamOrderByPublishedDate(Team team, Pageable pageable);

    @Query("SELECT a FROM Article a " +
            "LEFT JOIN FETCH a.team " +
            "WHERE a = :#{#article}")
    Article findFullArticle(Article article);
}
