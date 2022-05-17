package com.company.sportHubPortal.Security;

import com.company.sportHubPortal.Database.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    Logger logger = LoggerFactory.getLogger(CustomUserDetails.class);
    private User user;

    public CustomUserDetails(User user) {
        super();
        this.user = user;
    }

    public User getUser() {return user;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(user.isEnabled()){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "user=" + this.getUsername() +
                '}';
    }
}
