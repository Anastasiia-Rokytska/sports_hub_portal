package com.company.sportHubPortal.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeMessageController {

    @GetMapping("/welcome")
    public String welcomeMessage() {
        return "Welcome to SportHub Portal";
    }

}
