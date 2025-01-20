package org.example.modul223backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String username, Set<String> roles) {
        if (username.contains("_")) {
            throw new IllegalArgumentException("Username contains invalid characters for JWT.");
        }
        // Ensure roles are valid strings
        roles.forEach(role -> {
            if (!role.matches("^[a-zA-Z0-9]+$")) {
                throw new IllegalArgumentException("Invalid role for JWT encoding: " + role);
            }
        });

        //only for debugging purposes.
        System.out.println("Username: " + username);
        System.out.println("Roles: " + roles);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles) // Include roles as a claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public String extractRoles(String token) {
        return (String) getClaims(token).get("roles");
    }

    public boolean isTokenValid(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
