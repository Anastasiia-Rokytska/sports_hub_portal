package com.company.sportHubPortal.Repositories;

import com.company.sportHubPortal.Database.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  User getUserByEmail(String email);

  User getUserById(Integer id);

  User getUserByVerificationCode(String verificationCode);

  User getUserByRecoverPassHash(String uri);

}
