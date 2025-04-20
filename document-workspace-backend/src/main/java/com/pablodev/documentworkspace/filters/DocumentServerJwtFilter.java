package com.pablodev.documentworkspace.filters;

import com.onlyoffice.model.documenteditor.Callback;
import com.pablodev.documentworkspace.services.jwt.JwtService;
import com.pablodev.documentworkspace.services.user.DefaultUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Component
public class DocumentServerJwtFilter extends AbstractJwtFilter {

    private final JwtService jwtService;
    private final DefaultUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Optional<String> authorization = extractAuthorizationHeader(request);

        if (authorization.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization.get();
        Optional<Long> optionalUserId = extractUserId(token);

        if (optionalUserId.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserById(optionalUserId.get());
            if (jwtService.isValid(token)) {
                authenticate(request, userDetails);
            }
        }
        filterChain.doFilter(request, response);
    }

    private Optional<Long> extractUserId(String token) {
        return jwtService.extractCallback(token)
                .map(Callback::getActions)
                .filter(Predicate.not(List::isEmpty))
                .map(list -> Long.parseLong(list.get(0).getUserid()));
    }



}
