package com.company.sportHubPortal.Controllers;

import com.company.sportHubPortal.Models.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

  @MessageMapping("/message")
  @SendToUser("/team/new-article")
  public ResponseEntity<Article> getMessageUser(Article article) {
    return ResponseEntity.ok(article);
  }
}
