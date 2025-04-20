package com.pablodev.documentworkspace.services.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlyoffice.model.documenteditor.Callback;
import com.pablodev.documentworkspace.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class DefaultJwtService implements JwtService {

    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;

    @Override
    public boolean isValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && isValid(token);
    }

    @Override
    public boolean isValid(String token) {
        return !extractClaim(token, Claims::getExpiration).before(new Date());
    }

    @Override
    public String createToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails);
    }

    @Override
    public String createToken(Map<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails);
    }

    @Override
    public Optional<Callback> extractCallback(String token) {
        Callback callback = extractClaim(token, claim -> objectMapper.convertValue(claim.get("payload"), Callback.class));
        return Optional.ofNullable(callback);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(getSignInKey())
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

}
