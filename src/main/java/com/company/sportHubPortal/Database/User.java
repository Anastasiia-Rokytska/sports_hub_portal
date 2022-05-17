package com.company.sportHubPortal.Database;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Integer id;
  String firstName;
  String lastName;
  String email;
  String password;
  String verificationCode;
  boolean enabled;
  String recoverPassHash;

  @Enumerated(EnumType.STRING)
  UserRole role;

  public User() {
  }

  public User(@NonNull String firstName, @NonNull String lastName, @NonNull String email,
              @NonNull String password) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;

  }

  public User(String firstName, String lastName, String email, String password, UserRole role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public User(String firstName, String lastName, String email, String password, UserRole role,
              boolean enabled) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.role = role;
    this.enabled = enabled;
  }

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public Integer getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getVerificationCode() {
    return verificationCode;
  }

  public void setVerificationCode(String verificationCode) {
    this.verificationCode = verificationCode;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }


  @Override
  public String toString() {
    return "{"
        + "id=" + id
        + ", firstName='" + firstName + '\''
        + ", lastName='" + lastName + '\''
        + ", email='" + email + '\''
        + ", password='" + password + '\''
        + ", role=" + role
        + '}';
  }

  public String getRecoverPassHash() {
    return recoverPassHash;
  }

  public void setRecoverPassHash(String recoverPassHash) {
    this.recoverPassHash = recoverPassHash;
  }
}
