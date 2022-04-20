package com.company.sportHubPortal.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/{path:[^\\\\.]*}")
    public String Index(){
        return "index.html";
    }
}
