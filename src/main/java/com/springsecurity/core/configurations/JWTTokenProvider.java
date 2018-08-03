package com.springsecurity.core.configurations;

import com.springsecurity.core.entities.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenProvider {

    private final Logger logger = LoggerFactory.getLogger(JWTTokenProvider.class);

    private String jwtSecret;

    private Integer tokenExpireTime;

    public String generateToken(User user) {

        Date now = new Date();
        
        return Jwts.builder()
            .setSubject(user.getUserName())
            .setExpiration(new Date(now.getTime() + tokenExpireTime * 60 * 1000))
            .claim("firstName", user.getFirstName())
            .claim("lastName", user.getLastName())
            .claim("email", user.getEmail())
            .claim("role", user.getRole().getRoleName())
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public Boolean validate(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            logger.error("Authentication Error !");
        }

        return false;
    }

    @Value("${token.expire.time.minutes}")
    public void setTokenExpireTime(Integer tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    @Value("${token.secret}")
    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

}
