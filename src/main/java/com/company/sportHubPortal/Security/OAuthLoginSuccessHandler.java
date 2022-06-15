package com.company.sportHubPortal.Security;

import com.company.sportHubPortal.Services.JwtTokenService;
import com.company.sportHubPortal.Services.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private UserService userService;
  private final JwtTokenService jwtTokenService;


  @Autowired
  public OAuthLoginSuccessHandler(UserService userService,
                                  JwtTokenService jwtTokenService) {
    this.userService = userService;
    this.jwtTokenService = jwtTokenService;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication)
      throws ServletException, IOException {

    DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
    userService.processOAuthPostLogin(oauthUser.getAttributes(),
        ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId()
            .toUpperCase());
    jwtTokenService.createRefreshAndAccessToken(oauthUser.getEmail());
    String accessToken = jwtTokenService.getAccessToken();
    String refreshToken = jwtTokenService.getRefreshToken();
    Cookie accessTokenCookie = new Cookie("access_token", accessToken);
    Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
    accessTokenCookie.setHttpOnly(true);
    accessTokenCookie.setPath("/");
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setPath("/");
    response.addCookie(accessTokenCookie);
    response.addCookie(refreshTokenCookie);
    response.sendRedirect("/personal_page");
    super.onAuthenticationSuccess(request, response, authentication);
  }



}
