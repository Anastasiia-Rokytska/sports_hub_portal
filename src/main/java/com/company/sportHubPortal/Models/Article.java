package com.company.sportHubPortal.Database;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Lob
    private String content;

    /*    @ManyToOne
        @JoinColumn(name = "author_id", nullable = false, referencedColumnName = "id")*/
    private String author;

    private boolean commentable;

    private boolean published = false;

    private String language;

    private Date publishedDate;

    private String caption;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "article_category",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();


    public Article(String title, String content, String author, boolean commentable, String language, Date publishedDate, String caption, Set<Category> categories) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.commentable = commentable;
        this.language = language;
        this.publishedDate = publishedDate;
        this.caption = caption;
        this.categories = categories;
    }

    public Article(String title, String content, String user, boolean commentable) {
        this.title = title;
        this.content = content;
        this.author = user;
        this.commentable = commentable;
    }

    public Article() {

    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String user) {
        this.author = user;
    }

    public boolean isCommentable() {
        return commentable;
    }

    public void setCommentable(boolean commentable) {
        this.commentable = commentable;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }
}