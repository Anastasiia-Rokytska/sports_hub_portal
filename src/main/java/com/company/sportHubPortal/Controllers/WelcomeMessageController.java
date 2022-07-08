package com.company.sportHubPortal.Controllers;

import com.company.sportHubPortal.Models.EmailSender;
import com.company.sportHubPortal.Services.EmailSenderService;
import com.company.sportHubPortal.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class WelcomeMessageController {


  private final EmailSenderService emailSenderService;
  private final UserService userService;
  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  public WelcomeMessageController(EmailSenderService emailSenderService, UserService userService) {
    this.emailSenderService = emailSenderService;
    this.userService = userService;
  }

  @GetMapping("/welcome")
  public RedirectView welcomeMessage() {
    RedirectView view = new RedirectView();
    view.setUrl("https://www.youtube.com/feed/subscriptions");
    return view;
  }
}
