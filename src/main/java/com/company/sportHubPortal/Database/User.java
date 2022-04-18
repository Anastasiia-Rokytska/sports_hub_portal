package com.company.sportHubPortal.Database;

import com.sun.istack.NotNull;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @NotNull
    String email;
    @NotNull
    String password;


    @Enumerated(EnumType.STRING)
    UserRole role;

    public User(@NotNull String firstName, @NotNull String lastName, @NotNull String email, @NotNull String password) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;

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
/*public User(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
    }*/

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

    public User() {

    }
}
