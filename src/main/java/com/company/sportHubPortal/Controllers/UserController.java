package com.company.sportHubPortal.Controllers;

import com.company.sportHubPortal.Database.User;
import com.company.sportHubPortal.Database.UserRole;
import com.company.sportHubPortal.Services.JwtTokenService;
import com.company.sportHubPortal.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/user")
public class UserController {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    final UserService userService;
    final JwtTokenService jwtTokenService;

    @Autowired
    public UserController(UserService userService, JwtTokenService jwtTokenService) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    @PostMapping("/sign-up")
    public ResponseEntity<User> register(@RequestBody User user) {

        if(user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if(userService.getByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        if(!validate(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if(user.getPassword().length() < 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        user.setPassword(userService.encodePassword(user.getPassword()));
        user.setRole(UserRole.USER);
        userService.save(user);

        return ResponseEntity.ok(user);
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
