package com.company.sportHubPortal.Configs;

import com.company.sportHubPortal.Database.User;
import com.company.sportHubPortal.Database.UserRole;
import com.company.sportHubPortal.Services.UserService;
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
    public void userConfig(){
        User admin = new User("admin","admin","1@gmail.com", userService.encodePassword("admin"));
        admin.setRole(UserRole.ADMIN);
        userService.save(admin);
    }
}
