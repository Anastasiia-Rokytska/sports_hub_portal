package com.company.sportHubPortal.Repositories;

import com.company.sportHubPortal.Database.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
