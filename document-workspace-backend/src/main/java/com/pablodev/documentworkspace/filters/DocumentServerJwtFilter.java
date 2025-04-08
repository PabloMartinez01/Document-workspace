package com.pablodev.documentworkspace.filters;

import com.onlyoffice.model.documenteditor.Callback;
import com.onlyoffice.model.documenteditor.callback.Action;
import com.pablodev.documentworkspace.services.jwt.DocumentServerJwtService;
import com.pablodev.documentworkspace.services.user.DefaultUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class DocumentServerJwtFilter extends OncePerRequestFilter {

    private final DocumentServerJwtService jwtService;
    private final DefaultUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Optional<String> authorization = Optional.ofNullable(request.getHeader("Authorization"))
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                .map(authHeader -> authHeader.replace("Bearer ", ""));

        if (authorization.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.get();
        Optional<Long> optionalUserId = jwtService.extractCallback(token)
                .map(Callback::getActions)
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0))
                .map(Action::getUserid)
                .map(Long::parseLong);


        if (optionalUserId.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userId = optionalUserId.get();

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserById(userId);
            if (jwtService.isValid(token)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);

    }



}
