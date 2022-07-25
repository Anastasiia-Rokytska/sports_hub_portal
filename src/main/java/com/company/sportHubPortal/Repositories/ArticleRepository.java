package com.company.sportHubPortal.Repositories;

import com.company.sportHubPortal.Models.Article;
import com.company.sportHubPortal.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
  List<Article> getArticlesByPublishedIs(boolean published);
}
