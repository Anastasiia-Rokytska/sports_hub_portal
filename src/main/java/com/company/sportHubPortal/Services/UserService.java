package com.company.sportHubPortal.Services;

import com.company.sportHubPortal.Controllers.UserController;
import com.company.sportHubPortal.Database.User;
import com.company.sportHubPortal.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  Logger logger = LoggerFactory.getLogger(UserController.class);

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }


  public String encodePassword(String password) {
    return passwordEncoder.encode(password);
  }

  public Boolean decodePassword(String password, String encodedPassword) {
    return passwordEncoder.matches(password, encodedPassword);
  }


  public void save(User user) {
    userRepository.save(user);
  }

  public User getByEmail(String email) {
    return userRepository.getUserByEmail(email);
  }

  public boolean verifyUser(String code) {
    User user = userRepository.getUserByVerificationCode(code);

    if (user == null) {
      return false;
    }

    user.setVerificationCode(null);
    user.setEnabled(true);
    userRepository.save(user);

    logger.info(new Object() {
    }.getClass().getEnclosingMethod().getName() + "() " + "Account is verified");

    return true;
  }

  public User getById(Integer id) {
    return userRepository.getUserById(id);
  }


  public User getByRecoverPassHash(String uri) {
    return userRepository.getUserByRecoverPassHash(uri);
  }


}
