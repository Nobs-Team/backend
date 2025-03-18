package com.nobs.banksampah.service.implementation;

import com.nobs.banksampah.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtServiceImplementation implements JwtService {

  @Override
  public String generateToken(UserDetails userDetails) {
    String token =
        Jwts.builder()
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
            .signWith(getSigningKey())
            .compact();
    log.info("Access token generated successfully for username: {}", userDetails.getUsername());

    return token;
  }

  @Override
  public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    String refreshToken =
        Jwts.builder()
            .claims(extraClaims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 432000000)) // 5 days
            .signWith(getSigningKey())
            .compact();
    log.info("Refresh token generated successfully for username: {}", userDetails.getUsername());

    return refreshToken;
  }

  @Override
  public String extractUsername(String token) {
    String username = extractClaims(token, Claims::getSubject);
    log.info("Username extracted from token: {}", username);

    return username;
  }

  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    boolean isValid = username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    if (isValid) {
      log.info("Token is valid for username: {}", userDetails.getUsername());
    } else {
      log.warn("Token is invalid or expired for username: {}", userDetails.getUsername());
    }

    return isValid;
  }

  private boolean isTokenExpired(String token) {
    boolean expired = extractClaims(token, Claims::getExpiration).before(new Date());
    if (expired) {
      log.warn("Token has expired");
    } else {
      log.info("Token is still valid");
    }

    return expired;
  }

  private Key getSigningKey() {
    byte[] key =
        Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
    return Keys.hmacShaKeyFor(key);
  }

  private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);

    return claimsResolver.apply(claims);
  }

  @SuppressWarnings("deprecation")
  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .setSigningKey(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }
}
