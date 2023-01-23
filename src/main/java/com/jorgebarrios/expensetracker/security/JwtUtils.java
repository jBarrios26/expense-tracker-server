package com.jorgebarrios.expensetracker.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
    private final String secret = "Secret_KEY";

    public String extractUsername(String token) {
        return extractClaim(
                token,
                Claims::getSubject
                           );
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(
                token,
                Claims::getExpiration
                           );
    }

    public boolean hasClaim(
            String token,
            String claimName
                           ) {
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName) != null;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                   .setSigningKey(secret)
                   .parseClaimsJws(token)
                   .getBody();
    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
                             ) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(
                claims,
                userDetails
                          );
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createRefreshToken(
                claims,
                userDetails
                                 );
    }

    private String createToken(
            Map<String, Object> claims,
            UserDetails userDetails
                              ) {
        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(userDetails.getUsername())
                   .claim(
                           "authorities",
                           userDetails.getAuthorities()
                         )
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(
                           System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                   .signWith(
                           SignatureAlgorithm.HS256,
                           secret
                            )
                   .compact();
    }

    private String createRefreshToken(
            Map<String, Object> claims,
            UserDetails userDetails
                                     ) {
        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(userDetails.getUsername())
                   .claim(
                           "authorities",
                           userDetails.getAuthorities()
                         )
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(
                           System.currentTimeMillis() + 15000 * 60 * 60 * 10))
                   .signWith(
                           SignatureAlgorithm.HS256,
                           secret
                            )
                   .compact();
    }

    public Boolean isTokenValid(
            String token,
            UserDetails userDetails
                               ) {
        final String username = extractUsername(token);
        return (
                username.equals(userDetails.getUsername()) &&
                !isTokenExpired(token)
        );
    }

}
