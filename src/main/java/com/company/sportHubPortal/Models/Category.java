package com.company.sportHubPortal.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "category")

public class Category {


  @Id
  private Long id;
  private String name;
  private Long parentId;
  private boolean hidden;

  @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
  @JsonIgnore
  private Set<Article> articles = new HashSet<>();

  public Set<Article> getArticles() {
    return articles;
  }

  public void setArticles(Set<Article> articles) {
    this.articles = articles;
  }

  public void addArticle(Article article) {
    this.articles.add(article);
  }


  public Category(@NonNull Long id, @NonNull String name, @NonNull Long parentId,
                  @NonNull boolean hidden) {
    this.id = id;
    this.name = name;
    this.parentId = parentId;
    this.hidden = hidden;
  }

  public Category(@NonNull Long id, @NonNull String name, @NonNull Long parentId,
                  @NonNull boolean hidden, Set<Article> articles) {
    this.id = id;
    this.name = name;
    this.parentId = parentId;
    this.hidden = hidden;
    LoggerFactory.getLogger(Category.class).info(articles.toString());
    this.articles = articles;
  }

  public Category() {
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public boolean isHidden() {
    return hidden;
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }


}
