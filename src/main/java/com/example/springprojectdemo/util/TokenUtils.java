package com.example.springprojectdemo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private static final long EXPIRE_DATE = 60 * 24 * 7;
    // token secret
    private static final String TOKEN_SECRET = "ZCEQIUBFKSJBFJH2020BQWE";

    public static String create(String username, String password) {
        String token = "";
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_DATE * 60 * 1000);
            //
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("username", username)
                    .withClaim("password", password)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return token;
    }

    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getRefreshToken(String oldToken) {
        return getRefreshToken(
                JWT.require(Algorithm.HMAC256(TOKEN_SECRET))
                        .build()
                        .verify(oldToken)
        );
    }

    private static String getRefreshToken(DecodedJWT jwtToken) {
        Instant now = Instant.now();
        Instant exp = jwtToken.getExpiresAt().toInstant();

        // token 失效则不能重新申请
        if (now.getEpochSecond() - exp.getEpochSecond() > EXPIRE_DATE * 60) {
            return null;
        }
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Instant newExp = exp.plusSeconds(EXPIRE_DATE * 60);
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        String username = jwtToken.getClaim("username").asString();
        String password = jwtToken.getClaim("password").asString();
        String token = JWT.create()
                .withHeader(header)
                .withClaim("username", username)
                .withClaim("password", password)
                .withExpiresAt(Date.from(newExp))
                .sign(algorithm);
        return token;
    }
}
