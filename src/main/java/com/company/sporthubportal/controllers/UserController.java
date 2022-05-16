package com.company.sporthubportal.controllers;

import com.company.sporthubportal.database.User;
import com.company.sporthubportal.database.UserRole;
import com.company.sporthubportal.services.EmailSenderService;
import com.company.sporthubportal.services.JwtTokenService;
import com.company.sporthubportal.services.UserService;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
  public UserController(UserService userService, JwtTokenService jwtTokenService,
                        EmailSenderService emailSenderService) {
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

    if (user.getFirstName() == null
        || user.getLastName() == null
        || user.getEmail() == null
        || user.getPassword() == null) {
      logger.info(new Object() {
      }.getClass().getEnclosingMethod().getName() + "() " + " Empty fields");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    if (userService.getByEmail(user.getEmail()) != null) {
      logger.info(new Object() {
      }.getClass().getEnclosingMethod().getName() + "() " + " Email already exists");
      return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }
    if (!validate(user.getEmail())) {
      logger.info(new Object() {
      }.getClass().getEnclosingMethod().getName() + "() " + " Invalid email");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    if (user.getPassword().length() < 8) {
      logger.info(new Object() {
      }.getClass().getEnclosingMethod().getName() + "() " + " Invalid password");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    user.setPassword(userService.encodePassword(user.getPassword()));
    user.setRole(UserRole.USER);
    userService.save(user);

    logger.info(new Object() {
    }.getClass().getEnclosingMethod().getName() + "() " + " New user: " + user);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/login")
  public ResponseEntity<Object> login(
      @RequestBody User user
  ) {
    User foundUser = userService.getByEmail(user.getEmail());
    if (foundUser == null) {
      logger.info(new Object() {
      }.getClass().getEnclosingMethod().getName() + "() " + " User not found");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    if (!userService.decodePassword(user.getPassword(), foundUser.getPassword())) {
      logger.info(new Object() {
      }.getClass().getEnclosingMethod().getName() + "() " + " Incorrect password");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
    logger.info("Correct user information");
    return ResponseEntity.ok(jwtTokenService.getRefreshAndAccessToken(foundUser.getId()));
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<Object> requestResetLink(@RequestBody String email) {

    email = new Gson().fromJson(email, StringRequestParam.class).getParam();
    User user = userService.getByEmail(email);


    if (user == null) {
      logger.info(new Object() {
      }.getClass().getEnclosingMethod().getName() + "() " + " User not found");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    if (user.getRecoverPassHash() != null) {
      logger.info(new Object() {
      }.getClass().getEnclosingMethod().getName() + "() " + " User has already resetHash");
      return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(null);
    }

    logger.info("User is found");

    String recoverPassHash =
        userService.encodePassword(String.valueOf(new Date().getTime())).replaceAll("[^\\w ]", "");
    user.setRecoverPassHash(recoverPassHash);

    userService.save(user);

    logger.info("new PassHash was saved");

    String emailText = "http://localhost:8000/reset-password/" + user.getRecoverPassHash();
    emailSenderService.sendTextMessage(user.getEmail(), "Reset password", emailText);

    logger.info("Email is sent");

    return ResponseEntity.ok(HttpStatus.OK);
  }

  @PatchMapping("/reset-password/{id}")
  public ResponseEntity<Object> resetPassword(@PathVariable(value = "id") String userId,
                                              @RequestBody String password) {

    String o = new Object() {
    }.getClass().getEnclosingMethod().getName() + "() ";

    password = new Gson().fromJson(password, StringRequestParam.class).getParam();

    User user = userService.getByRecoverPassHash(userId);

    if (user == null) {
      logger.info(o + " User not found");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    logger.info("User is found");

    user.setPassword(userService.encodePassword(password));
    user.setRecoverPassHash(null);
    userService.save(user);

    logger.info("new pass is saved, hashPass==null");

    return ResponseEntity.ok(HttpStatus.OK);
  }


  static class StringRequestParam {
    @SerializedName("param")
    private String param;

    public String getParam() {
      return param;
    }
  }
}
