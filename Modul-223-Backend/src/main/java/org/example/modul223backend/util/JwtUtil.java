package org.example.modul223backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Utility class for managing JWT token generation and validation.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Generates a JWT token for the given username and roles.
     * @param username the username for which the token is generated.
     * @param roles the roles to be included in the token.
     * @return the generated JWT token.
     * @throws IllegalArgumentException if the username or roles contain invalid characters.
     */
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


    /**
     * Extracts the username from the given JWT token.
     * @param token the JWT token.
     * @return the username embedded in the token.
     */
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Extracts roles from the given JWT token.
     * @param token the JWT token.
     * @return a list of roles embedded in the token.
     */
    public List<String> extractRoles(String token) {
        Claims claims = getClaims(token);
        return claims.get("roles", List.class);
    }

    /**
     * Validates if the token is valid for the given username.
     * @param token the JWT token.
     * @param username the username to validate against the token.
     * @return true if the token is valid and matches the username; false otherwise.
     */
    public boolean isTokenValid(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    /**
     * Checks if the JWT token has expired.
     * @param token the JWT token.
     * @return true if the token has expired; false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    /**
     * Retrieves claims from the given JWT token.
     * @param token the JWT token.
     * @return the claims embedded in the token.
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
