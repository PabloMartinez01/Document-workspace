package com.pablodev.documentworkspace.services.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public abstract class AbstractJwtService {

    @Value("${application.security.jwt.secret-key}")
    protected String secret;

    @Value("${application.security.jwt.expiration}")
    protected Long expiration;


    protected Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    protected <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    protected Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    protected boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    protected SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
