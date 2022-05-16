package com.company.sporthubportal.repositories;

import com.company.sporthubportal.database.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  User getUserByEmail(String email);

  User getUserById(int id);

  User getUserByRecoverPassHash(String uri);
}
