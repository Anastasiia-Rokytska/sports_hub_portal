package com.company.sportHubPortal.Services;

import com.company.sportHubPortal.Database.User;
import com.company.sportHubPortal.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

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

  public User getById(int id) {
    return userRepository.getUserById(id);
  }

  public User getByRecoverPassHash(String uri) {
    return userRepository.getUserByRecoverPassHash(uri);
  }


}
