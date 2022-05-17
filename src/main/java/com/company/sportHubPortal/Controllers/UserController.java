package com.company.sportHubPortal.Controllers;

import com.company.sportHubPortal.Database.User;
import com.company.sportHubPortal.Database.UserRole;
import com.company.sportHubPortal.Security.CustomUserDetails;
import com.company.sportHubPortal.Services.EmailSenderService;
import com.company.sportHubPortal.Services.JwtTokenService;
import com.company.sportHubPortal.Services.UserService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    final EmailSenderService emailSenderService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, JwtTokenService jwtTokenService, EmailSenderService emailSenderService) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
        this.emailSenderService = emailSenderService;
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    @PostMapping("/sign-up")
    public ResponseEntity<User> register(@RequestBody User user) {

        if(user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getPassword() == null) {
            logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + "() " + " Empty fields");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if(userService.getByEmail(user.getEmail()) != null) {
            logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + "() " + " Email already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        if(!validate(user.getEmail())) {
            logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + "() " + " Invalid email");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if(user.getPassword().length() < 8) {
            logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + "() " + " Invalid password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        user.setPassword(userService.encodePassword(user.getPassword()));
        user.setRole(UserRole.ROLE_USER);
        userService.save(user);

        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + "() " + " New user: " + user.toString());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/own_information")
    public ResponseEntity<Object> userByHimself() {
        UserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        User user = userService.getByEmail(email);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok(new Gson().toJson(user));
    }
}
