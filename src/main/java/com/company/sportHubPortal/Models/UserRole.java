package com.company.sportHubPortal.Models;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
  USER,
  ADMIN;

  @Override
  public String getAuthority() {
    return name();
  }
}
