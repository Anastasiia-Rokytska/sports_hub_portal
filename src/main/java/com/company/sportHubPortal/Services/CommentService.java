package com.company.sportHubPortal.Services;

import com.company.sportHubPortal.Models.Comment;
import com.company.sportHubPortal.Repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void saveComment(Comment comment) {
        System.out.println(comment);
        commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getById(Long id) {
        return commentRepository.findById(id).get();
    }

    public List<Comment> getByArticleId(Long id) {
        return commentRepository.findAll().stream()
                .filter(comment -> comment.getArticle().getId().equals(id) && comment.getParent() == null)
                .toList();
    }

    public List<Comment> getAllReplies(Long id){
        return getByArticleId(commentRepository.getById(id).getArticle().getId()).stream()
                .filter(comment -> comment.getParent() != null)
                .filter(comment -> comment.getParent().getId().equals(id))
                .toList();
    }

    public Comment updateComment(Long id, Comment comment) {
        Comment temp = getById(id);
        temp.setEdited(true);
        temp.setContent(comment.getContent());
        return commentRepository.save(temp);
    }
}
