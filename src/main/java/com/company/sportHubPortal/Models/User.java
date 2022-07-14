package com.company.sportHubPortal.Models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({"subscriptions"})
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

  String photoLink;

  @Enumerated(EnumType.STRING)
  UserRole role;

  @Enumerated(EnumType.STRING)
  private AuthProvider authProvider;

  public User() {
  }

  @ManyToMany
  @JoinTable(name = "subscriptions",
          joinColumns = @JoinColumn(name = "user"),
          inverseJoinColumns = @JoinColumn(name = "team"))
  private Set<Team> subscriptions = new HashSet<>();

  public User(String firstName, String lastName, String email, String password, UserRole role, boolean enabled, Set<Team> subscriptions) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.enabled = enabled;
    this.role = role;
    this.subscriptions = subscriptions;
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

  public void addSubscription(Team team) {
    subscriptions.add(team);
  }

  public void removeSubscription(Team team) {
    subscriptions.remove(team);
  }

  public void addSubscriptions(List<Team> teams){
    subscriptions.addAll(teams);
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

  public Set<Team> getSubscriptions() {
    return subscriptions;
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

  public AuthProvider getAuthProvider() {
    return authProvider;
  }

  public void setAuthProvider(AuthProvider authProvider) {
    this.authProvider = authProvider;
  }

  public String getRecoverPassHash() {
    return recoverPassHash;
  }

  public void setRecoverPassHash(String recoverPassHash) {
    this.recoverPassHash = recoverPassHash;
  }

  public String getPhotoLink() {
    return photoLink;
  }

  public void setPhotoLink(String photoLink) {
    this.photoLink = photoLink;
  }
}
