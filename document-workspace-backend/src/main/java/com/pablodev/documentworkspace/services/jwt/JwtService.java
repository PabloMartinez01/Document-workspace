package com.pablodev.documentworkspace.services.jwt;

import com.onlyoffice.model.documenteditor.Callback;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.Optional;

public interface JwtService {
    boolean isValid(String token, UserDetails userDetails);

    boolean isValid(String token);

    String createToken(UserDetails userDetails);

    String createToken(Map<String, Object> claims, UserDetails userDetails);

    Optional<Callback> extractCallback(String token);

    String extractUsername(String token);
}
