package com.company.sportHubPortal.Security;
import com.company.sportHubPortal.Services.JwtTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().equals("/user/sign-up") ||
                request.getRequestURI().equals("/sign-up") ||
                request.getRequestURI().equals("/login") ||
                request.getRequestURI().matches("/user/verify/.*$") ||
                request.getRequestURI().matches(".*(css|jpg|png|gif|js|html|svg|ico)");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info(request.getRequestURI());
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
                        logger.info("Principal: " +
                                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
                        logger.info("Is authenticated: " +
                                SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
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
