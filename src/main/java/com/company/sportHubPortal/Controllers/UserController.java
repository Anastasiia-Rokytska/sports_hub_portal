package com.company.sportHubPortal.Controllers;

import com.company.sportHubPortal.Database.User;
import com.company.sportHubPortal.Services.JwtTokenService;
import com.company.sportHubPortal.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    final UserService userService;
    final JwtTokenService jwtTokenService;

    @Autowired
    public UserController(UserService userService, JwtTokenService jwtTokenService) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/login" )
    public ResponseEntity<Object> login(
            @RequestBody User user
    ){
        User foundUser = userService.getByEmail(user.getEmail());
        if (foundUser == null) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if (!userService.decodePassword(user.getPassword(), foundUser.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(jwtTokenService.getRefreshAndAccessToken(foundUser.getId()));
    }
}
