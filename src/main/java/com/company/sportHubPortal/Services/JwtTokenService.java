package com.company.sportHubPortal.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.company.sportHubPortal.Security.CustomUserDetailsService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Service
public class JwtTokenService {
    public static class Tokens {
        String accessToken;
        String refreshToken;

        public Tokens(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

    private final Algorithm algorithm;
    private final CustomUserDetailsService userDetailsService;
    private Tokens tokens;
    Logger logger = LoggerFactory.getLogger(JwtTokenService.class);

    @Autowired
    public JwtTokenService(Algorithm algorithm, CustomUserDetailsService userDetailsService){
        this.algorithm = algorithm;
        this.userDetailsService = userDetailsService;
    }

    public String createRefreshToken(String email){
        Calendar date = Calendar.getInstance();
        LocalDate expirationTimeRefreshToken = LocalDate.now().plusMonths(1);
        return JWT.create()
                .withExpiresAt(Date.from(expirationTimeRefreshToken.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withSubject(email)
                .sign(algorithm);
    }

    public String createAccessToken(String email){
        Calendar date = Calendar.getInstance();
        LocalDateTime expirationTimeAccessToken = LocalDateTime.now().plusMinutes(15);
        return JWT.create()
                .withExpiresAt(Date.from(expirationTimeAccessToken.atZone(ZoneId.systemDefault()).toInstant()))
                .withSubject(email)
                .sign(algorithm);
    }

    public void createRefreshAndAccessToken(String email){
        this.tokens = new Tokens(createAccessToken(email), createRefreshToken(email));
    }

    public String verifyToken(String token) throws Exception{
        try{
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (Exception exception){
            throw exception;
        }
    }

    public String refreshAccessToken(String refreshToken) throws Exception{
        try{
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refreshToken);
            String email = decodedJWT.getSubject();
            return createAccessToken(email);
        } catch (Exception exception){
            throw exception;
        }
    }

    public Authentication getAuthentication(String token) throws Exception {
        String email = verifyToken(token);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", null);
    }

    public String getRefreshAndAccessToken(String email) {
        createRefreshAndAccessToken(email);
        return new Gson().toJson(this.tokens);
    }

    public String getAccessToken() throws NullPointerException{
        if (this.tokens.accessToken == null) throw new NullPointerException("Access token is null");
        return this.tokens.accessToken;
    }

    public String getRefreshToken() throws NullPointerException{
        if (this.tokens.refreshToken == null) throw new NullPointerException("Refresh token is null");
        return this.tokens.refreshToken;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }
}
