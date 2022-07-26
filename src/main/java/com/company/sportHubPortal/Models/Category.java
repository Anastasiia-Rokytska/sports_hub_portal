package com.company.sportHubPortal.Models;

import com.fasterxml.jackson.annotation.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")

public class Category {
    @Id
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "parents", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Category> children = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "category_parent_children",
            joinColumns = @JoinColumn(name = "parents_id"),
            inverseJoinColumns = @JoinColumn(name = "children_id"))
    private Set<Category> parents = new HashSet<>();

    private boolean hidden;

    private boolean isTeam;

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


    public Category(@NonNull Long id, @NonNull String name, Set<Category> parents, @NonNull boolean hidden, Set<Category> children, boolean isTeam) {
        this.id = id;
        this.name = name;
        if(parents != null) {
            this.parents = parents;
        }
        this.hidden = hidden;
        if(children != null) {
            this.children = children;
        }
        this.isTeam = isTeam;
    }

    public Category(Long id, String name, boolean hidden){
        this.id = id;
        this.name = name;
        this.hidden = hidden;
    }

    public Category(@NonNull Long id, @NonNull String name, Set<Category> parents, @NonNull boolean hidden, Set<Article> articles, Set<Category> children, boolean isTeam) {
        this.id = id;
        this.name = name;
        if(parents != null) {
            this.parents = parents;
        }
        this.hidden = hidden;
        this.articles = articles;
        if(children != null) {
            this.children = children;
        }
        this.isTeam = isTeam;
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

    public Set<Category> getParents() {
        return parents;
    }

    public void setParents(Set<Category> parents) {
        this.parents = parents;
    }

    public void addParent(Category parent) {
        this.parents.add(parent);
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isTeam() {
        return isTeam;
    }

    public void setTeam(boolean team) {
        isTeam = team;
    }
}
