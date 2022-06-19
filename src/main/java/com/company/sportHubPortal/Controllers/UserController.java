package com.company.sportHubPortal.Controllers;

import com.company.sportHubPortal.Database.User;
import com.company.sportHubPortal.Database.UserRole;
import com.company.sportHubPortal.Security.CustomUserDetails;
import com.company.sportHubPortal.Services.EmailSenderService;
import com.company.sportHubPortal.Services.JwtTokenService;
import com.company.sportHubPortal.Services.UserService;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/user")
public class UserController {
  public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


  final UserService userService;
  final JwtTokenService jwtTokenService;
  final EmailSenderService emailSenderService;
  Logger logger = LoggerFactory.getLogger(UserController.class);
  private OAuth2AuthorizedClientService authorizedClientService;
  private Environment environment;


  @Autowired
  public UserController(UserService userService,
                        JwtTokenService jwtTokenService,
                        EmailSenderService emailSenderService,
                        OAuth2AuthorizedClientService authorizedClientService,
                        Environment environment
                        ) {
    this.userService = userService;
    this.jwtTokenService = jwtTokenService;
    this.emailSenderService = emailSenderService;
    this.authorizedClientService = authorizedClientService;
    this.environment = environment;
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
    user.setVerificationCode(RandomString.make(64));
    user.setEnabled(false);
    userService.save(user);

    String message = String.format(
        "Hello, %s! \n"
            + "Welcome to Sporthub. Please, visit next link to verify your "
            + "account: http://localhost:8000/user/verify/%s",
        user.getFirstName(),
        user.getVerificationCode()
    );

    emailSenderService.sendTextMessage(user.getEmail(), "Verification code", message);

    logger.info(new Object() {
    }.getClass().getEnclosingMethod().getName() + "() " + " New user: " + user);
    return ResponseEntity.ok(user);
  }

  @GetMapping("/verify/{code}")
  public String verify(@PathVariable String code) {

    if (userService.verifyUser(code)) {
      logger.info(new Object() {
      }.getClass().getEnclosingMethod().getName() + "() " + "Account is verified");
      return "Account is verified!";
    } else {
      logger.info(new Object() {
      }.getClass().getEnclosingMethod().getName() + "() " + "Account is not verified");
      return "Incorrect verification link";
    }

  }

  @GetMapping("/own_information")
  public ResponseEntity<Object> userByHimself() {
    UserDetails userDetails =
        (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String email = userDetails.getUsername();
    User user = userService.getByEmail(email);
    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return ResponseEntity.ok(new Gson().toJson(user));
  }

  @PostMapping("/check-old-pass")
  public ResponseEntity<Object> checkOldPass(@RequestBody String password) {

    String o = new Object() {
    }.getClass().getEnclosingMethod().getName() + "() ";

    UserDetails userDetails =
        (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = userService.getByEmail(userDetails.getUsername());
    if (user == null) {
      logger.info(o + "User is not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    password = new Gson().fromJson(password, StringRequestParam.class).getParam();

    if (userService.decodePassword(password, user.getPassword())) {
      return ResponseEntity.ok(new Gson().toJson(user));
    } else {
      logger.info(o + password);
      logger.info(o + "Password is not decoded");

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @PatchMapping("/change-password")
  public ResponseEntity<Object> changePassword(@RequestBody String password) {

    String o = new Object() {
    }.getClass().getEnclosingMethod().getName() + "() ";

    password = new Gson().fromJson(password, StringRequestParam.class).getParam();

    UserDetails userDetails =
        (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = userService.getByEmail(userDetails.getUsername());
    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    logger.info(o + "User is found");

    user.setPassword(userService.encodePassword(password));
    userService.save(user);

    logger.info(o + "Password is changed");

    return ResponseEntity.ok(HttpStatus.OK);
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


  @GetMapping("/oauthSuccess")
  public ResponseEntity<Object> getLoginInfo(OAuth2AuthenticationToken authentication) {

    OAuth2AuthorizedClient client = authorizedClientService
        .loadAuthorizedClient(
            authentication.getAuthorizedClientRegistrationId(),
            authentication.getName());

    String userInfoEndpointUri = client.getClientRegistration()
        .getProviderDetails().getUserInfoEndpoint().getUri();

    if (!StringUtils.isEmpty(userInfoEndpointUri)) {
      RestTemplate restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
          .getTokenValue());

      HttpEntity entity = new HttpEntity("", headers);
      ResponseEntity<Map> response = restTemplate
          .exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
      Map<String, String> userAttributes = response.getBody();

      //userService.processOAuthPostLogin(userAttributes);

    }

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
