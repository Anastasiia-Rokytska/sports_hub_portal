package com.company.sportHubPortal.Services;

import com.company.sportHubPortal.Database.User;
import com.company.sportHubPortal.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    public String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

    public Boolean decodePassword(String password, String encodedPassword){
        return passwordEncoder.matches(password, encodedPassword);
    }

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User getByEmail(String email){
        return userRepository.getUserByEmail(email);
    }

    public User getById(Integer id) { return userRepository.getUserById(id); }

}
