package com.company.sportHubPortal.Security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.company.sportHubPortal.Services.JwtTokenService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

  final JwtTokenService jwtTokenService;
  final AuthenticationManager authenticationManager;
  final UserDetailsService userDetailsService;

  public CustomAuthorizationFilter(JwtTokenService jwtTokenService,
                                   AuthenticationManager authenticationManager,
                                   UserDetailsService userDetailsService) {
    this.jwtTokenService = jwtTokenService;
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String uri = request.getRequestURI();
    return uri.equals("/user/sign-up")
        || uri.equals("/sign-up")
        || uri.equals("/login")
        || uri.equals("/forgot-password")
        || uri.equals("/user/forgot-password")
        || uri.matches("/oauth2/.*$")
        || uri.equals("/user/oauthSuccess")
        || uri.equals("/welcome")
        || (uri.equals("/team") && !request.getMethod().equals("POST"))
        || uri.equals("/api/category/category")
        || uri.equals("/api/category/subcategory")
        || uri.matches("/api/category/subcategory/.*$")
        || uri.matches("/api/article/team/.*$")
        || (
        uri.matches("/team/.*$")
            && !uri.equals("/team/subscriptions")
            && !request.getMethod().equals("DELETE")
            && !uri.matches("/team/subscribe/.*$"))
        || uri.matches("/reset-password/.*$")
        || uri.matches("/user/reset-password/.*$")
        || uri.matches("/user/verify/.*$")
        || uri.matches(".*(css|jpg|png|gif|js|html|svg|ico)");
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    logger.info("URL : " + request.getRequestURI());
    if (shouldNotFilter(request)) {
      filterChain.doFilter(request, response);
    } else {
      Cookie accessTokenCookie = WebUtils.getCookie(request, "access_token");
      if (accessTokenCookie != null) {
        String accessToken = accessTokenCookie.getValue();
        if (accessToken != null) {
          try {
            Authentication authentication = jwtTokenService.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            logger.info("User: " + userDetails.getUsername());
            filterChain.doFilter(request, response);
          } catch (Exception e) {
            if (e instanceof TokenExpiredException) {
              Cookie refreshTokenCookie = WebUtils.getCookie(request, "refresh_token");
              if (refreshTokenCookie == null) {
                logger.error("Refresh token is null");
                throw new NullPointerException("Refresh token is null");
              } else {
                try {
                  String updatedAccessToken =
                      jwtTokenService.refreshAccessToken(refreshTokenCookie.getValue());
                  accessTokenCookie.setMaxAge(0);
                  response.addCookie(new Cookie("access_token", updatedAccessToken));
                  logger.info("Access token was updated");
                  Authentication authentication =
                      jwtTokenService.getAuthentication(updatedAccessToken);
                  SecurityContextHolder.getContext().setAuthentication(authentication);
                  filterChain.doFilter(request, response);
                } catch (Exception exception) {
                  logger.error(exception);
                }
              }
            } else {
              logger.error(e.getMessage());
            }
          }
        } else {
          logger.error("Access token is null");
          throw new NullPointerException("Access token is null");
        }
      } else {
        logger.warn("access_token cookie is null");
        response.sendRedirect("/login");
      }
    }
  }
}
