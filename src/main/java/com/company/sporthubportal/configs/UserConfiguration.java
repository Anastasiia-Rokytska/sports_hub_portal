package com.company.sporthubportal.configs;

import com.company.sporthubportal.database.User;
import com.company.sporthubportal.database.UserRole;
import com.company.sporthubportal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

  final UserService userService;

  @Autowired
  public UserConfiguration(UserService userService) {
    this.userService = userService;
  }

  @Bean
  public void userConfig() {
    User admin = new User("admin", "admin", "1@gmail.com", userService.encodePassword("admin"));
    admin.setRole(UserRole.ADMIN);
    userService.save(admin);
    userService.save(new User("firstname1", "lastname1", "email1@gmail.com",
        userService.encodePassword("qwerty"), UserRole.USER));
    userService.save(new User("user1firstname", "user1lastname", "user1@gmail.com",
        userService.encodePassword("password"), UserRole.USER));
    userService.save(new User("user2firstname", "user2lastname", "user2@gmail.com",
        userService.encodePassword("password"), UserRole.USER));
    userService.save(new User("user3firstname", "user3lastname", "user3@gmail.com",
        userService.encodePassword("password"), UserRole.USER));

  }
}
