package com.company.sportHubPortal.POJO;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ArticlePOJO {
    private String title;
    private String content;
    private String author;
    private boolean commentable;
    private String language;
    private String caption;
    private MultipartFile icon;

    public ArticlePOJO(String title, String content, String author, boolean commentable, String language, String caption, MultipartFile icon){
        this.title = title;
        this.content = content;
        this.author = author;
        this.commentable = commentable;
        this.language = language;
        this.caption = caption;
        this.icon = icon;
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

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isCommentable() {
        return commentable;
    }

    public void setCommentable(boolean commentable) {
        this.commentable = commentable;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public MultipartFile getIcon() {
        return icon;
    }

    public void setIcon(MultipartFile icon) {
        this.icon = icon;
    }
}
