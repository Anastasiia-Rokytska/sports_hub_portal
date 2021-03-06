package com.company.sportHubPortal.Repositories;

import com.company.sportHubPortal.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  User getUserByEmail(String email);

  User getUserById(Integer id);

  User getUserByVerificationCode(String verificationCode);

  User getUserByRecoverPassHash(String uri);

  @Query("SELECT u FROM User u LEFT JOIN FETCH u.subscriptions WHERE u.email = :#{#email}")
  User getUserWithSubscriptions(String email);

}
