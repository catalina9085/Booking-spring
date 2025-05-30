package com.online.booking.service.auth.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {
    //@Value("${jwt_secret}")
    private String secret="mySecret";


    public String generateToken(String email,String role) {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("email" ,email)
                .withClaim("role" ,role)
                .withIssuedAt(new Date())
                .withIssuer("QuickPoll")
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateToken(String token) {
        JWTVerifier verifier=JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("QuickPoll")
                .build();
        DecodedJWT jwt=verifier.verify(token);
        return jwt.getClaim("email").asString();
    }

}