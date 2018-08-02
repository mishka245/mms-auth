package com.springsecurity.core.configurations;

import com.springsecurity.core.dto.UserLoginDTO;
import com.springsecurity.core.entities.User;
import io.jsonwebtoken.Claims;
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

    private Long tokenExpireTime;

    public String generateToken(User user) {

        long now = System.currentTimeMillis();

        Claims claims = Jwts.claims();
        claims.setSubject(user.getUserName());
        claims.setIssuedAt(new Date(now));
        claims.setExpiration(new Date(now + tokenExpireTime));
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole().getRoleName());

        return Jwts.builder()
                .setClaims(claims)
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
    public void setTokenExpireTime(Long tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime * 60 * 1000;
    }

    @Value("${token.secret}")
    public void setTokenExpireTime(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

}
