package com.company.sportHubPortal.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "article")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;


    @ManyToOne()
    @JoinColumn(name = "parent")
    private Comment parent;

    private Date publishedDate;

    private boolean edited;


    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comment> children = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Likes> likes = new ArrayList<>();


    public Comment(Long id, String content, Article article, User user, Comment parent, List<Comment> children, List<Likes> likes, Date date, boolean edited) {
        this.id = id;
        this.content = content;
        this.article = article;
        this.user = user;
        this.parent = parent;
        this.children = children;
        this.likes = likes;
        this.publishedDate = date;
        this.edited = edited;
    }

    public Comment() {

    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @JsonBackReference(value = "replies")
    public Comment getParent() {
        return parent;
    }


    public void setParent(Comment parent) {
        this.parent = parent;
    }

    @JsonManagedReference(value = "replies")
    public List<Comment> getChildren() {
        return children;
    }

    public void setChildren(List<Comment> children) {
        this.children = children;
    }

    @JsonManagedReference(value = "likes")
    public List<Likes> getLikes() {
        return likes;
    }

    public void setLikes(List<Likes> likes) {
        this.likes = likes;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", article=" + article +
                ", user=" + user +
                ", parent=" + parent +
                '}';
    }
}
