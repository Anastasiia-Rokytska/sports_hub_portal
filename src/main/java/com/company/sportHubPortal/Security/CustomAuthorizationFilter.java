package com.company.sportHubPortal.Security;

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
    return request.getRequestURI().equals("/user/sign-up")
        || request.getRequestURI().equals("/sign-up")
        || request.getRequestURI().equals("/user/sign-up")
        || request.getRequestURI().equals("/login")
        || request.getRequestURI().equals("/forgot-password")
        || request.getRequestURI().equals("/user/forgot-password")
        || request.getRequestURI().matches("/oauth2/.*$")
        || request.getRequestURI().equals("/user/oauthSuccess")
        || request.getRequestURI().equals("/welcome")
        || (request.getRequestURI().equals("/team") && !request.getMethod().equals("POST"))
        || request.getRequestURI().equals("/api/category/category")
        || request.getRequestURI().equals("/api/category/subcategory")
        || request.getRequestURI().matches("/api/category/subcategory/.*$")
        || request.getRequestURI().matches("/api/article/team/.*$")
        || (request.getRequestURI().matches("/team/.*$") && !request.getRequestURI().equals("/team/subscriptions") && !request.getMethod().equals("DELETE")
        && !request.getRequestURI().matches("/team/subscribe/.*$"))
        || request.getRequestURI().matches("/reset-password/.*$")
        || request.getRequestURI().matches("/user/reset-password/.*$")
        || request.getRequestURI().matches("/user/verify/.*$")
        || request.getRequestURI().matches(".*(css|jpg|png|gif|js|html|svg|ico)");
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
            logger.info("Principal: "
                + SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            logger.info("Is authenticated: "
                + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
            filterChain.doFilter(request, response);
          } catch (Exception e) {
            logger.error(e.getMessage());
          }
        } else {
          logger.error("Access token is null");
          throw new NullPointerException();
        }
      } else {
        logger.warn("access_token cookie is null");
        response.sendRedirect("/login");
      }
    }
  }
}
