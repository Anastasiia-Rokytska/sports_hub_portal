package com.company.sportHubPortal.Database;

import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {
  private String oauthClientName;
  private OAuth2User oauth2User;

  public CustomOAuth2User(OAuth2User oauth2User, String oauthClientName) {
    this.oauth2User = oauth2User;
    this.oauthClientName = oauthClientName;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return oauth2User.getAttributes();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return oauth2User.getAuthorities();
  }

  @Override
  public String getName() {
    return oauth2User.getAttribute("name");
  }

  public String getEmail() {
    return oauth2User.getAttribute("email");
  }

  public String getOauthClientName() {
    return oauthClientName;
  }
}
