package com.company.sportHubPortal.Models;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashSet;
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

    private String author;

    private boolean commentable;

    private boolean published = false;

    private String language;

    private Date publishedDate;

    private String caption;

    @Lob
    private Blob icon;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "article_category",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @ManyToOne
    private Team team;

    public Article(String title, String content, String author, boolean commentable, String language, Date publishedDate, String caption, Set<Category> categories, MultipartFile icon) throws IOException, SQLException {
        this.title = title;
        this.content = content;
        this.author = author;
        this.commentable = commentable;
        this.language = language;
        this.publishedDate = publishedDate;
        this.caption = caption;
        this.categories = categories;
        this.icon = new SerialBlob(icon.getBytes());
    }

    public Article(String title, String content, String author, boolean commentable, String language, Date publishedDate, String caption, Set<Category> categories, Blob icon){
        this.title = title;
        this.content = content;
        this.author = author;
        this.commentable = commentable;
        this.language = language;
        this.publishedDate = publishedDate;
        this.caption = caption;
        this.categories = categories;
        this.icon = icon;
    }

    public Article(String title, String content, String user, boolean commentable, String language, String caption) {
        this.title = title;
        this.content = content;
        this.author = user;
        this.commentable = commentable;
        this.language = language;
        this.caption = caption;
    }

    public Article(String title, String content, String author,
                   boolean commentable, boolean published,
                   String language, Date publishedDate, String caption,
                   MultipartFile icon, Set<Category> categories) throws IOException, SQLException {
        this.title = title;
        this.content = content;
        this.author = author;
        this.commentable = commentable;
        this.published = published;
        this.language = language;
        this.publishedDate = publishedDate;
        this.caption = caption;
        this.icon = new SerialBlob(icon.getBytes());
        this.categories = categories;
    }

    public Article(String title, String content, String author,
                   boolean commentable, boolean published,
                   String language, Date publishedDate, String caption,
                   Blob icon, Set<Category> categories) throws IOException, SQLException {
        this.title = title;
        this.content = content;
        this.author = author;
        this.commentable = commentable;
        this.published = published;
        this.language = language;
        this.publishedDate = publishedDate;
        this.caption = caption;
        this.icon = icon;
        this.categories = categories;
    }

    public Article(String title, String content, String author,
                   boolean commentable, boolean published,
                   String language, Date publishedDate, String caption,
                   Set<Category> categories) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.commentable = commentable;
        this.published = published;
        this.language = language;
        this.publishedDate = publishedDate;
        this.caption = caption;
        this.categories = categories;
        this.icon = icon;
    }

    public Article(String title, String content, String user, boolean commentable, String language, String caption) {
        this.title = title;
        this.content = content;
        this.author = user;
        this.commentable = commentable;
        this.language = language;
        this.caption = caption;
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

    public void setId(Long id) {
        this.id = id;
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

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public byte[] getIcon() throws SQLException, IOException {
        if (icon == null) return null;
        return icon.getBinaryStream().readAllBytes();
    }

    public void setIcon(MultipartFile icon) throws IOException, SQLException {
        this.icon = new SerialBlob(icon.getBytes());
    }
}
