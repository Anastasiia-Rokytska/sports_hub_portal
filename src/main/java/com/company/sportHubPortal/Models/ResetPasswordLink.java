package com.company.sportHubPortal.Models;

import com.company.sportHubPortal.Services.UserService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

public class ResetPasswordLink implements GenerationLinkStrategy {

  final UserService userService;

  @Autowired
  public ResetPasswordLink(UserService userService) {
    this.userService = userService;
  }

  @Override
  public String generateLink() {
    return userService.encodePassword(String.valueOf(new Date().getTime()))
        .replaceAll("[^\\w ]", "");
  }
}
