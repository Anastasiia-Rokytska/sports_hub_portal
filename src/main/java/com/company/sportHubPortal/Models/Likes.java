package com.company.sportHubPortal.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "likes")
public class Likes {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "comment")
  private Comment comment;

  @ManyToOne
  @JoinColumn(name = "user")
  private User user;

  private boolean liked;

  public Likes() {
  }

  public Likes(Comment comment, User user, boolean liked) {
    this.comment = comment;
    this.user = user;
    this.liked = liked;
  }

  public Long getId() {
    return id;
  }

  @JsonBackReference(value = "likes")
  public Comment getComment() {
    return comment;
  }

  public void setComment(Comment comment) {
    this.comment = comment;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public boolean isLiked() {
    return liked;
  }

  public void setLiked(boolean liked) {
    this.liked = liked;
  }
}
