package com.company.sporthubportal.services;

import com.company.sporthubportal.database.User;
import com.company.sporthubportal.repositories.UserRepository;
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
