package com.company.sportHubPortal.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Service
public class JwtTokenService {

    private final Algorithm algorithm;

    @Autowired
    public JwtTokenService(Algorithm algorithm){
        this.algorithm = algorithm;
    }

    public class Tokens {
        String accessToken;
        String refreshToken;

        public Tokens(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

    public String getRefreshAndAccessToken(Integer idUser) {
        Calendar date = Calendar.getInstance();
        LocalDate expirationTimeRefreshToken = LocalDate.now().plusMonths(1);
        LocalDateTime expirationTimeAccessToken = LocalDateTime.now().plusMinutes(15);
        String refreshToken = JWT.create()
                .withExpiresAt(Date.from(expirationTimeRefreshToken.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withIssuer(idUser.toString())
                .sign(algorithm);
        String accessToken = JWT.create()
                .withExpiresAt(Date.from(expirationTimeAccessToken.atZone(ZoneId.systemDefault()).toInstant()))
                .withIssuer(idUser.toString())
                .sign(algorithm);
        Tokens tokens = new Tokens(accessToken, refreshToken);
        return new Gson().toJson(tokens);
    }
}
