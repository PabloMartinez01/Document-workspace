package com.pablodev.documentworkspace.services.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    boolean isValid(String token, UserDetails userDetails);
    String createToken(UserDetails userDetails);
    String createToken(Map<String, Object> extraClaims, UserDetails userDetails);
    String extractUsername(String token);
}
