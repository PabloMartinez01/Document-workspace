package com.pablodev.documentworkspace.services.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlyoffice.model.documenteditor.Callback;
import com.pablodev.documentworkspace.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentServerJwtService extends AbstractJwtService {

    public DocumentServerJwtService(JwtProperties jwtProperties) {
        super(jwtProperties);
    }

    public boolean isValid(String token) {
        return !isTokenExpired(token);
    }

    public Optional<Callback> extractCallback(String token) {
        ObjectMapper mapper = new ObjectMapper();
        Claims claims = extractAllClaims(token);
        return Optional.ofNullable(mapper.convertValue(claims.get("payload"), Callback.class));
    }


}
