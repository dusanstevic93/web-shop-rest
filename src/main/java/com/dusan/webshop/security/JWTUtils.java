package com.dusan.webshop.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtils {

    private final SecretKey key;
    private final long expiration;

    public JWTUtils(@Value("${jwt.expiration}") long expiration) {
        this.expiration = expiration;
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public TokenWrapper createToken(long userId, String role) {
        Date expirationTime = new Date(System.currentTimeMillis() + expiration);
        String token = Jwts.builder()
                .claim("userId", userId)
                .claim("userRole", role)
                .setExpiration(expirationTime)
                .signWith(key)
                .compact();

        return new TokenWrapper(token, expirationTime.getTime());
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public long getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }

    public String getUserRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userRole", String.class);
    }
}
