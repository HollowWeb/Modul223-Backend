package org.example.modul223backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.modul223backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filter class for handling JWT authentication.
 * Intercepts incoming requests to validate and process JWT tokens.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Filters each incoming request to validate JWT tokens and set authentication in the security context.
     * @param request the incoming HTTP request.
     * @param response the HTTP response.
     * @param chain the filter chain to pass the request/response along.
     * @throws ServletException if an error occurs during request processing.
     * @throws IOException if an I/O error occurs during request processing.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                String username = jwtUtil.extractUsername(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtUtil.isTokenValid(token, username)) {
                        List<String> roles = jwtUtil.extractRoles(token); // Add this method in jwtUtil

                        // Convert roles to GrantedAuthority
                        List<GrantedAuthority> authorities = roles.stream()
                                .map(SimpleGrantedAuthority::new) // Convert each role into a SimpleGrantedAuthority
                                .collect(Collectors.toList());

                        // Create an authentication token with roles
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                username, null, authorities);

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // Set authentication in the security context
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (Exception e) {
                // Handle invalid token exceptions
                System.out.println("Invalid JWT Token: " + e.getMessage());
            }
        }

        // Continue the filter chain
        chain.doFilter(request, response);
    }

}
