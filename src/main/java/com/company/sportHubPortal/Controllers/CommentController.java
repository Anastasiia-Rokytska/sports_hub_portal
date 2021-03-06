package com.company.sportHubPortal.Controllers;

import com.company.sportHubPortal.Models.Comment;
import com.company.sportHubPortal.Services.CommentService;
import java.sql.Date;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping("/all")
  public List<Comment> getAllComments() {
    return commentService.getAllComments();
  }

  @GetMapping("/article/{id}")
  public List<Comment> getByArticleId(@PathVariable Long id) {
    return commentService.getByArticleId(id);
  }

  @GetMapping("/replies/{id}")
  public List<Comment> getAllReplies(@PathVariable Long id) {
    return commentService.getAllReplies(id);
  }

  @GetMapping("/{id}")
  public Comment getById(@PathVariable Long id) {
    return commentService.getById(id);
  }

  @PostMapping()
  public void saveComment(@RequestBody Comment comment) {
    comment.setPublishedDate(new Date(System.currentTimeMillis()));
    commentService.saveComment(comment);
  }

  @DeleteMapping("/{id}")
  public void deleteComment(@PathVariable Long id) {
    commentService.deleteComment(id);
  }

  @PutMapping("/{id}")
  public Comment updateComment(@PathVariable Long id, @RequestBody Comment comment) {
    return commentService.updateComment(id, comment);
  }


}
