package com.company.sportHubPortal.Services;

import com.company.sportHubPortal.Models.Article;
import com.company.sportHubPortal.Models.Team;
import com.company.sportHubPortal.Models.User;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
  private final SimpMessagingTemplate simpMessagingTemplate;
  private final TeamService teamService;

  @Autowired
  public NotificationService(SimpMessagingTemplate simpMessagingTemplate,
                             TeamService teamService) {
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.teamService = teamService;
  }

  public void notifySubscribers(Article article) {
    Team team = article.getTeam();
    if (team != null) {
      Set<User> subscribers =
          teamService.getTeamWithSubscribers(article.getTeam()).getSubscribers();
      subscribers.forEach(subscriber -> {
        simpMessagingTemplate
            .convertAndSendToUser(subscriber.getEmail(), "/team/new-article", article);
      });
    }
  }
}
