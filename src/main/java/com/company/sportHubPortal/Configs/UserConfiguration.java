package com.company.sportHubPortal.Configs;

import com.company.sportHubPortal.Models.User;
import com.company.sportHubPortal.Models.UserRole;
import com.company.sportHubPortal.Services.TeamService;
import com.company.sportHubPortal.Services.UserService;
import java.util.Arrays;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

  private final UserService userService;
  private final TeamService teamService;


  @Autowired
  public UserConfiguration(UserService userService, TeamService teamService) {
    this.userService = userService;
    this.teamService = teamService;
  }

  @Bean
  public void userConfig() {
    userService.save(new User("admin", "admin", "1@gmail.com", userService.encodePassword("admin"),
        UserRole.ADMIN, true, new HashSet<>()));
    userService.save(new User("firstname1", "lastname1", "email1@gmail.com",
        userService.encodePassword("qwerty"), UserRole.USER, true, new HashSet<>(Arrays.asList(
        teamService.teamByName("Los Angeles Lakers"),
        teamService.teamByName("Los Angeles Lakers10"),
        teamService.teamByName("Memphis Grizzlies10"),
        teamService.teamByName("Utah Jazz1"),
        teamService.teamByName("Utah Jazz")))));
    userService.save(new User("user1firstname", "user1lastname", "user1@gmail.com",
        userService.encodePassword("password"), UserRole.USER, true, new HashSet<>(Arrays.asList(
        teamService.teamByName("Los Angeles Lakers"),
        teamService.teamByName("Houston Rockets"),
        teamService.teamByName("Memphis Grizzlies10"),
        teamService.teamByName("Utah Jazz1"),
        teamService.teamByName("Utah Jazz")))));
    userService.save(new User("user2firstname", "user2lastname", "user2@gmail.com",
        userService.encodePassword("password"), UserRole.USER, true));
    userService.save(new User("user3firstname", "user3lastname", "user3@gmail.com",
        userService.encodePassword("password"), UserRole.USER, true));
    userService.save(new User("yuriy", "gonsor", "gonsor.y2@gmail.com",
        userService.encodePassword("12345678"), UserRole.USER, true));
  }
}
