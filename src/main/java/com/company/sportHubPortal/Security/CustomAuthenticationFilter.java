package com.company.sportHubPortal.Security;

import com.company.sportHubPortal.Services.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final CustomUserDetailsService userDetailsService;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
                                      JwtTokenService jwtTokenService,
                                      CustomUserDetailsService userDetailsService1) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService1;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("Authentication: "+ request.getRequestURI());
        byte[] body = new byte[0];
        Map<String, String> jsonRequest = null;
        try {
            body = StreamUtils.copyToByteArray(request.getInputStream());
            jsonRequest = new ObjectMapper().readValue(body, Map.class);
        } catch (IOException e) {
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        String email = jsonRequest.get("email");
        String password = jsonRequest.get("password");
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(email);
        logger.info("Email is " + email);
        logger.info("Password is " + password);
        if (!userDetails.getUser().isEnabled()){
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(null, null);
            logger.info("Account is not verified");
            return authenticationManager.authenticate(authenticationToken);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUserDetails user = (CustomUserDetails) authResult.getPrincipal();
        System.out.println(((CustomUserDetails) authResult.getPrincipal()).getUsername());
        jwtTokenService.createRefreshAndAccessToken(user.getUsername());
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
        logger.info("Successful authentication");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        if (failed.getClass() == UsernameNotFoundException.class){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else if (failed.getClass() == BadCredentialsException.class){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        logger.info("Unsuccessful authentication");
    }
}
