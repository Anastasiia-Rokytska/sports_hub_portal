package com.company.sportHubPortal.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class WelcomeMessageController {

  @GetMapping("/welcome")
  public RedirectView welcomeMessage() {
    RedirectView view = new RedirectView();
    view.setUrl("https://www.youtube.com/feed/subscriptions");
    return view;
  }
}
